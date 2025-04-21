package com.example.bank.service;

import com.example.bank.model.*;
import com.example.bank.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BankService {

    @Autowired private CustomerRepository customerRepo;
    @Autowired private AccountRepository accountRepo;
    @Autowired private TransactionRepository transactionRepo;

    public double getBalance(int accountNumber) {
        Account acc = accountRepo.findById(accountNumber).orElseThrow(() -> new IllegalArgumentException("Account not found"));
        return acc.getBalance();
    }

    public void deposit(int accountNumber, double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Invalid deposit amount");
        Account acc = accountRepo.findById(accountNumber).orElseThrow(() -> new IllegalArgumentException("Account not found"));
        acc.setBalance(acc.getBalance() + amount);
        accountRepo.save(acc);
        transactionRepo.save(new Transaction("Deposit: " + amount, acc));
    }

    public void withdraw(int accountNumber, double amount) {
        Account acc = accountRepo.findById(accountNumber).orElseThrow(() -> new IllegalArgumentException("Account not found"));
        if (amount <= 0 || acc.getBalance() < amount)
            throw new IllegalArgumentException("Insufficient funds");
        acc.setBalance(acc.getBalance() - amount);
        accountRepo.save(acc);
        transactionRepo.save(new Transaction("Withdraw: " + amount, acc));
    }

    public void transfer(int fromAcc, int toAcc, double amount) {
        Account from = accountRepo.findById(fromAcc).orElseThrow(() -> new IllegalArgumentException("From Account not found"));
        Account to = accountRepo.findById(toAcc).orElseThrow(() -> new IllegalArgumentException("To Account not found"));
        if (amount <= 0 || from.getBalance() < amount)
            throw new IllegalArgumentException("Insufficient funds");
        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);
        accountRepo.save(from);
        accountRepo.save(to);
        transactionRepo.save(new Transaction("Transfer to: " + toAcc + " amount: " + amount, from));
        transactionRepo.save(new Transaction("Transfer from: " + fromAcc + " amount: " + amount, to));
    }

    public List<Transaction> getLast10Transactions(int accountNumber) {
        return transactionRepo.findTop10ByAccountAccountNumberOrderByTimestampDesc(accountNumber);
    }
}