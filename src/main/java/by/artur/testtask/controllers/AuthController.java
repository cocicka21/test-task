package by.artur.testtask.controllers;

import by.artur.testtask.dtos.EmailRequest;
import by.artur.testtask.dtos.JwtResponse;
import by.artur.testtask.dtos.PhoneRequest;
import by.artur.testtask.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Аутентификация по email и паролю.
     */
    @PostMapping("/login/email")
    public ResponseEntity<JwtResponse> loginByEmail(@RequestBody EmailRequest emailRequest) {
        return ResponseEntity.ok(authService.authenticateByEmail(emailRequest));
    }

    /**
     * Аутентификация по phone и паролю.
     */
    @PostMapping("/login/phone")
    public ResponseEntity<JwtResponse> loginByPhone(@RequestBody PhoneRequest phoneRequest) {
        return ResponseEntity.ok(authService.authenticateByPhone(phoneRequest));
    }
}
