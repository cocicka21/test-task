package by.artur.testtask.services;

import by.artur.testtask.entities.Account;
import by.artur.testtask.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BalanceScheduler {

    private final AccountRepository accountRepository;

    @Scheduled(fixedRate = 30000)
    public void increaseBalances() {
        List<Account> accounts = accountRepository.findAll();
        for (Account account : accounts) {
            BigDecimal initial = account.getInitialBalance();
            BigDecimal max = initial.multiply(BigDecimal.valueOf(2.07));
            BigDecimal increased = account.getBalance().multiply(BigDecimal.valueOf(1.10));
            if (increased.compareTo(max) > 0) {
                increased = max;
            }
            account.setBalance(increased);
        }
        accountRepository.saveAll(accounts);
        log.info("Balances have been increased");
    }
}
