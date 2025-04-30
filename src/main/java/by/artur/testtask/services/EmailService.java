package by.artur.testtask.services;

import by.artur.testtask.entities.EmailData;
import by.artur.testtask.entities.EmailLog;
import by.artur.testtask.exceptions.EmailException;
import by.artur.testtask.repositories.EmailDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailDataRepository emailDataRepository;
    private final ElasticsearchService elasticsearchService;

    public EmailData saveEmail(EmailData emailData) throws IOException {
        if (!existsByEmail(emailData.getEmail())) {
            emailDataRepository.save(emailData);
            elasticsearchService.addEmailLog(new EmailLog(emailData.getUser().getId().toString(), emailData.getEmail(), LocalDate.now()));
            return emailData;
        }
        throw new EmailException("Email already exists");
    }

    public void deleteEmail(EmailData emailData) {
        emailDataRepository.delete(emailData);
    }

    public boolean existsByEmail(String email) {
        return emailDataRepository.existsByEmail(email);
    }
}
