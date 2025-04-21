package com.bank;

public class Bank {
    private Customer[] customers = new Customer[10];
    private Account[] accounts = new Account[10];
    private Transaction[] transactions = new Transaction[100];
    private int transactionCount = 0;

    public void initAccounts() {
        for (int i = 0; i < 10; i++) {
            customers[i] = new Customer(i + 1, "Customer" + (i + 1));
            accounts[i] = new Account((i + 1) * 1000, 1000.0, customers[i]);
        }
    }

    private Account findAccount(int accNo) {
        for (Account acc : accounts) {
            if (acc.getAccountNumber() == accNo) return acc;
        }
        return null;
    }

    public double getBalance(int accNo) {
        Account acc = findAccount(accNo);
        if (acc == null) throw new RuntimeException("Invalid account");
        return acc.getBalance();
    }

    public void deposit(int accNo, double amount) {
        Account acc = findAccount(accNo);
        if (acc == null || amount <= 0) throw new RuntimeException("Invalid deposit");
        acc.setBalance(acc.getBalance() + amount);
        logTransaction(new Transaction("Deposit: " + amount, acc));
    }

    public void withdraw(int accNo, double amount) {
        Account acc = findAccount(accNo);
        if (acc == null || amount <= 0 || acc.getBalance() < amount)
            throw new RuntimeException("Invalid withdraw");
        acc.setBalance(acc.getBalance() - amount);
        logTransaction(new Transaction("Withdraw: " + amount, acc));
    }

    public void transfer(int fromAcc, int toAcc, double amount) {
        Account from = findAccount(fromAcc);
        Account to = findAccount(toAcc);
        if (from == null || to == null || amount <= 0 || from.getBalance() < amount)
            throw new RuntimeException("Transfer failed");
        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);
        logTransaction(new Transaction("Transfer to " + toAcc + ": " + amount, from));
        logTransaction(new Transaction("Transfer from " + fromAcc + ": " + amount, to));
    }

    private void logTransaction(Transaction tx) {
        if (transactionCount < transactions.length) {
            transactions[transactionCount++] = tx;
        }
    }

    public void printLast10Transactions(int accNo) {
        int count = 0;
        for (int i = transactionCount - 1; i >= 0 && count < 10; i--) {
            if (transactions[i].getAccount().getAccountNumber() == accNo) {
                System.out.println(transactions[i]);
                count++;
            }
        }
    }
}