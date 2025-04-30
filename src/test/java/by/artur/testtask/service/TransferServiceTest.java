package by.artur.testtask.service;

import by.artur.testtask.dtos.TransferRequest;
import by.artur.testtask.entities.Account;
import by.artur.testtask.entities.TransferLog;
import by.artur.testtask.entities.User;
import by.artur.testtask.repositories.AccountRepository;
import by.artur.testtask.services.ElasticsearchService;
import by.artur.testtask.services.TransferService;
import by.artur.testtask.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
public class TransferServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private ElasticsearchService elasticsearchService;

    @Mock
    private UserService userService;

    @InjectMocks
    private TransferService transferService;

    @Test
    void transfer_shouldTransferAmountCorrectly() throws IOException {
        User fromUser = new User();
        fromUser.setId(1L);
        User toUser = new User();
        toUser.setId(2L);
        BigDecimal amount = new BigDecimal("100.00");

        Account fromAccount = new Account();
        fromAccount.setUser(fromUser);
        fromAccount.setBalance(new BigDecimal("200.00"));

        Account toAccount = new Account();
        toAccount.setUser(toUser);
        toAccount.setBalance(new BigDecimal("50.00"));

        TransferRequest request = new TransferRequest(toUser.getId(), amount);

        Mockito.when(userService.getUser()).thenReturn(fromUser.getId());
        Mockito.when(accountRepository.findByUserId(fromUser.getId())).thenReturn(Optional.of(fromAccount));
        Mockito.when(accountRepository.findByUserId(toUser.getId())).thenReturn(Optional.of(toAccount));

        transferService.transfer(request);

        Assertions.assertEquals(new BigDecimal("100.00"), fromAccount.getBalance());
        Assertions.assertEquals(new BigDecimal("150.00"), toAccount.getBalance());

        Mockito.verify(elasticsearchService).addTransferLog(any(TransferLog.class));
    }
}
