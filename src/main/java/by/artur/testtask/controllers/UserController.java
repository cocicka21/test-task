package by.artur.testtask.controllers;

import by.artur.testtask.dtos.EmailDataDto;
import by.artur.testtask.dtos.PhoneDataDto;
import by.artur.testtask.dtos.UserDTO;
import by.artur.testtask.entities.EmailLog;
import by.artur.testtask.entities.PhoneLog;
import by.artur.testtask.entities.User;
import by.artur.testtask.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/email")
    public ResponseEntity<EmailDataDto> addUserEmail(@RequestParam("email") String email) throws IOException {
        return ResponseEntity.ok(userService.addEmail(email));
    }

    @DeleteMapping("/email")
    public ResponseEntity<EmailDataDto> deleteUserEmail(@RequestParam("email") String email) {
        userService.deleteEmail(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/phone")
    public ResponseEntity<PhoneDataDto> createUserPhone(@RequestParam("phone") String phone
    ) throws IOException {
        return ResponseEntity.ok(userService.addPhones(phone));
    }

    @DeleteMapping("/phone")
    public ResponseEntity<PhoneDataDto> deleteUserPhone(@RequestParam("phone") String phone
    ) {
        userService.deletePhone(phone);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<UserDTO>>> searchUsers(@RequestParam(value = "name", required = false) String name,
                                                                        @RequestParam(value = "email", required = false) String email,
                                                                        @RequestParam(value = "phone", required = false) String phone,
                                                                        @RequestParam(value = "dateOfBirth", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfBirth,
                                                                        @RequestParam(value = "page", defaultValue = "0") int page,
                                                                        @RequestParam(value = "size", defaultValue = "20") int size,
                                                                        @RequestParam(defaultValue = "id") String sort,
                                                                        @RequestParam(defaultValue = "asc") Sort.Direction order) {
        Pageable pageRequest = PageRequest.of(page, size, Sort.by(order, sort));
        return ResponseEntity.ok(userService.searchUsers(name, email, phone, dateOfBirth, pageRequest));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @GetMapping("/me")
    public ResponseEntity<User> getMe(){
        return ResponseEntity.ok(userService.findUserById(userService.getUser()));
    }

    @GetMapping("/phones/history")
    public ResponseEntity<List<PhoneLog>> getUserPhoneHistory() throws IOException {
        return ResponseEntity.ok(userService.getUserPhoneHistory());
    }

    @GetMapping("/emails/history")
    public ResponseEntity<List<EmailLog>> getUserEmailHistory() throws IOException {
        return ResponseEntity.ok(userService.getUserEmailHistory());
    }

}
