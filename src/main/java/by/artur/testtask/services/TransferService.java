package by.artur.testtask.services;

import by.artur.testtask.dtos.TransferRequest;
import by.artur.testtask.entities.Account;
import by.artur.testtask.entities.TransferLog;
import by.artur.testtask.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final AccountRepository accountRepository;
    private final ElasticsearchService elasticsearchService;
    private final UserService userService;

    public void transfer(TransferRequest request) throws IOException {
        Long userId = userService.getUser();
        Account fromAccount = accountRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Sender account not found"));

        Account toAccount = accountRepository.findByUserId(request.getToUserId())
                .orElseThrow(() -> new IllegalArgumentException("Receiver account not found"));

        if (fromAccount.getBalance().compareTo(request.getAmount()) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(request.getAmount()));
        toAccount.setBalance(toAccount.getBalance().add(request.getAmount()));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
        TransferLog transferLog = new TransferLog(userId.toString(), request.getToUserId().toString(), request.getAmount().toString());
        elasticsearchService.addTransferLog(transferLog);
    }

    public List<TransferLog> getUserHistory() throws IOException {
        Long userId = userService.getUser();
        return elasticsearchService.getUserTransfersHistory(userId);
    }
}
