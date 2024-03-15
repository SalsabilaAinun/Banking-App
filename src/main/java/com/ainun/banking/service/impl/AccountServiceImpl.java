package com.ainun.banking.service.impl;

import com.ainun.banking.dto.AccountDto;
import com.ainun.banking.entity.Account;
import com.ainun.banking.mapper.AccountMapper;
import com.ainun.banking.repository.AccountRepository;
import com.ainun.banking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository repository;

    public AccountServiceImpl(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedAccount = repository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account = repository.findById(id).orElseThrow(() -> new RuntimeException("Account doesn't exist"));
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto deposit(Long id, double amount) {
        Account account = repository.findById(id).orElseThrow(() -> new RuntimeException("Account doesn't exist")); // -> exception kalo account gaada
        double total = account.getBalance() + amount;
        account.setBalance(total); // -> set balance nya yg udah ditambah deposit
        Account savedAccount = repository.save(account); // -> save account dengan balance yg baru
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto withdraw(Long id, double amount) {
        Account account = repository.findById(id).orElseThrow(() -> new RuntimeException("Account doesn't exist")); // -> checking if the account is exist

        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient amount");
        }
        double total = account.getBalance() - amount;
        account.setBalance(total);
        Account savedAccount = repository.save(account);

        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accounts = repository.findAll();
        return accounts.stream()
                .map((account) -> AccountMapper.mapToAccountDto(account))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAccount(Long id) {
        Account account = repository.findById(id).orElseThrow(() -> new RuntimeException("Account doesn't exist"));
        repository.deleteById(id);
    }
}
