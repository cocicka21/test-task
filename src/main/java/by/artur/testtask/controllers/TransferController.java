package by.artur.testtask.controllers;

import by.artur.testtask.dtos.TransferRequest;
import by.artur.testtask.entities.TransferLog;
import by.artur.testtask.services.TransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/transfer")
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @PostMapping
    public ResponseEntity<Void> transfer(@RequestBody @Valid TransferRequest request) throws IOException {
        transferService.transfer(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/history")
    public ResponseEntity<List<TransferLog>> getUserHistory() throws IOException {
        return ResponseEntity.ok(transferService.getUserHistory());
    }
}
