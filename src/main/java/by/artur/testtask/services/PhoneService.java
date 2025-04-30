package by.artur.testtask.services;

import by.artur.testtask.entities.PhoneData;
import by.artur.testtask.entities.PhoneLog;
import by.artur.testtask.exceptions.PhoneException;
import by.artur.testtask.repositories.PhoneDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PhoneService {

    private final PhoneDataRepository phoneDataRepository;
    private final ElasticsearchService elasticsearchService;

    public PhoneData savePhone(PhoneData phoneData) throws IOException {
        if (!existsByPhone(phoneData.getPhone())) {
            phoneDataRepository.save(phoneData);
            elasticsearchService.addPhoneLog(new PhoneLog(phoneData.getUser().getId().toString(), phoneData.getPhone(), LocalDate.now()));
            return phoneData;
        }
        throw new PhoneException("Phone already exists");
    }

    public void deletePhone(PhoneData phoneData) {
        phoneDataRepository.delete(phoneData);
    }

    public boolean existsByPhone(String phone) {
        return phoneDataRepository.existsByPhone(phone);
    }
}
