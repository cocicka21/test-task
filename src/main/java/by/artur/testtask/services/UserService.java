package by.artur.testtask.services;

import by.artur.testtask.configuration.UserSpecification;
import by.artur.testtask.dtos.EmailDataDto;
import by.artur.testtask.dtos.PhoneDataDto;
import by.artur.testtask.dtos.UserDTO;
import by.artur.testtask.entities.*;
import by.artur.testtask.exceptions.EmailException;
import by.artur.testtask.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PhoneService phoneService;
    private final ElasticsearchService elasticsearchService;
    private final ModelMapper modelMapper;
    private final PagedResourcesAssembler<UserDTO> pagedResourcesAssembler;

    public EmailDataDto addEmail(String email) throws IOException {
        Long userId = getUser();
        User user = findUserById(userId);
        Set<EmailData> listEmailsData = user.getEmails();
        EmailData emailData = new EmailData();
        emailData.setEmail(email);
        emailData.setUser(user);
        emailService.saveEmail(emailData);
        listEmailsData.add(emailData);
        user.setEmails(listEmailsData);
        userRepository.save(user);
        return new EmailDataDto(email);
    }

    public void deleteEmail(String email) {
        Long userId = getUser();
        User user = findUserById(userId);
        Set<EmailData> emailsData = user.getEmails();
        EmailData emailData = emailsData.stream().filter(e -> e.getEmail().equals(email))
                .findFirst().orElseThrow(() -> new EmailException("email not found"));
        emailsData.remove(emailData);
        emailService.deleteEmail(emailData);
        userRepository.save(user);
    }

    public PhoneDataDto addPhones(String phone) throws IOException {
        Long userId = getUser();
        User user = findUserById(userId);
        Set<PhoneData> listPhoneData = user.getPhones();
        PhoneData newPhoneData = new PhoneData();
        newPhoneData.setPhone(phone);
        newPhoneData.setUser(user);
        phoneService.savePhone(newPhoneData);
        listPhoneData.add(newPhoneData);
        user.setPhones(listPhoneData);
        userRepository.save(user);
        return new PhoneDataDto(phone);
    }

    public void deletePhone(String phone) {
        Long userId = getUser();
        User user = findUserById(userId);
        Set<PhoneData> emailsData = user.getPhones();
        PhoneData phoneData = emailsData.stream().filter(e -> e.getPhone().equals(phone))
                .findFirst().orElseThrow(() -> new EmailException("email not found"));
        emailsData.remove(phoneData);
        phoneService.deletePhone(phoneData);
        userRepository.save(user);
    }

    public UserDTO getUserById(Long userId) {
        return modelMapper.map(findUserById(userId), UserDTO.class);
    }

    public User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public Long getUser() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findById(Long.valueOf(userName)).orElseThrow(() -> new UsernameNotFoundException("User not found")).getId();
    }

    @Transactional(readOnly = true)
    public PagedModel<EntityModel<UserDTO>> searchUsers(String name, String email, String phone, LocalDate dateOfBirth, Pageable pageable) {
//        Page<User> users = userRepository.searchUsers(name, email, phone, dateOfBirth, pageable);

        Specification<User> spec = UserSpecification.withFilters(name, phone, email, dateOfBirth);
        Page<User> users = userRepository.findAll(spec, pageable);

        Page<UserDTO> usersDto = users.map(user -> new UserDTO(
                user.getId(),
                user.getName(),
                user.getDateOfBirth(),
                user.getEmails().stream().map(e -> modelMapper.map(e, EmailDataDto.class)).collect(Collectors.toSet()),
                user.getPhones().stream().map(p -> modelMapper.map(p, PhoneDataDto.class)).collect(Collectors.toSet())
        ));
        return pagedResourcesAssembler.toModel(usersDto);
    }

    public List<PhoneLog> getUserPhoneHistory() throws IOException {
        Long userId = getUser();
        return elasticsearchService.getUserPhonesHistory(userId);
    }

    public List<EmailLog> getUserEmailHistory() throws IOException {
        Long userId = getUser();
        return elasticsearchService.getUserEmailsHistory(userId);
    }
}
