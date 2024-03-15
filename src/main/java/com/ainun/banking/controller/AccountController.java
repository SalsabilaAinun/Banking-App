package com.ainun.banking.controller;

import com.ainun.banking.dto.AccountDto;
import com.ainun.banking.entity.Account;
import com.ainun.banking.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    // Add Account REST API
    @PostMapping
    public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto accountDto) {
        return new ResponseEntity<>(service.createAccount(accountDto), HttpStatus.CREATED);
    }

    // Get Account REST API
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id) {
        AccountDto accountDto = service.getAccountById(id); // -> manggil service karena method "getAccountById" ada di service
        return ResponseEntity.ok(accountDto); // -> response entity mengembalikan variable yang ada di dalam dto
    }

    // Deposit addition to balance REST API
    @PutMapping("/{id}/deposit")
    public ResponseEntity<AccountDto> deposit(@PathVariable Long id, @RequestBody Map<String, Double> request) { //-> mapping key dan value
        Double amount = request.get("amount"); // -> getting string "amount" dari request body
        AccountDto accountDto = service.deposit(id, amount); // -> manggil service karena method "deposit" di define di service
        return ResponseEntity.ok(accountDto); // -> response entity mengembalikan variable yang ada di dalam dto
    }

    // Withdraw substraction to balance REST API
    @PutMapping("/{id}/withdraw")
    public ResponseEntity<AccountDto> withdraw(@PathVariable Long id, @RequestBody Map<String, Double> request) {
        Double amount = request.get("amount");
        AccountDto accountDto = service.withdraw(id, amount);
        return ResponseEntity.ok(accountDto);
    }

    // Get All Accounts REST API
    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        List<AccountDto> accounts = service.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    // Delete Account REST API
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
        service.deleteAccount(id);
        return ResponseEntity.ok("Account is deleted successfully");
    }
}
