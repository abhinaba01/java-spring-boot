package com.bank;

import java.sql.*;

public class Bank {
    private final String url = "jdbc:postgresql://localhost:5432/bankdb";
    private final String user = "postgres";
    private final String password = "postgres";

    public void initDB() {
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS transactions");
            stmt.execute("DROP TABLE IF EXISTS accounts");
            stmt.execute("DROP TABLE IF EXISTS customers");

            stmt.execute("CREATE TABLE customers (id INT PRIMARY KEY, name VARCHAR(100))");
            stmt.execute("CREATE TABLE accounts (account_number INT PRIMARY KEY, balance DOUBLE PRECISION, customer_id INT REFERENCES customers(id))");
            stmt.execute("CREATE TABLE transactions (id SERIAL PRIMARY KEY, description TEXT, account_number INT, timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initAccounts() {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            for (int i = 1; i <= 10; i++) {
                try (PreparedStatement cStmt = conn.prepareStatement("INSERT INTO customers VALUES (?, ?)")) {
                    cStmt.setInt(1, i);
                    cStmt.setString(2, "Customer" + i);
                    cStmt.executeUpdate();
                }

                try (PreparedStatement aStmt = conn.prepareStatement("INSERT INTO accounts VALUES (?, ?, ?)")) {
                    aStmt.setInt(1, i * 1000);
                    aStmt.setDouble(2, 1000.0);
                    aStmt.setInt(3, i);
                    aStmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double getBalance(int accNo) {
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement("SELECT balance FROM accounts WHERE account_number = ?")) {
            stmt.setInt(1, accNo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            } else {
                throw new RuntimeException("Account not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deposit(int accNo, double amount) {
        if (amount <= 0) throw new RuntimeException("Invalid deposit amount");
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            conn.setAutoCommit(false);

            try (PreparedStatement update = conn.prepareStatement("UPDATE accounts SET balance = balance + ? WHERE account_number = ?")) {
                update.setDouble(1, amount);
                update.setInt(2, accNo);
                if (update.executeUpdate() == 0) throw new RuntimeException("Account not found");
            }

            try (PreparedStatement tx = conn.prepareStatement("INSERT INTO transactions(description, account_number) VALUES (?, ?)")) {
                tx.setString(1, "Deposit: " + amount);
                tx.setInt(2, accNo);
                tx.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void withdraw(int accNo, double amount) {
        if (amount <= 0) throw new RuntimeException("Invalid withdraw amount");

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            conn.setAutoCommit(false);

            try (PreparedStatement check = conn.prepareStatement("SELECT balance FROM accounts WHERE account_number = ?")) {
                check.setInt(1, accNo);
                ResultSet rs = check.executeQuery();
                if (!rs.next() || rs.getDouble(1) < amount)
                    throw new RuntimeException("Insufficient funds or account not found");
            }

            try (PreparedStatement update = conn.prepareStatement("UPDATE accounts SET balance = balance - ? WHERE account_number = ?")) {
                update.setDouble(1, amount);
                update.setInt(2, accNo);
                update.executeUpdate();
            }

            try (PreparedStatement tx = conn.prepareStatement("INSERT INTO transactions(description, account_number) VALUES (?, ?)")) {
                tx.setString(1, "Withdraw: " + amount);
                tx.setInt(2, accNo);
                tx.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void transfer(int fromAcc, int toAcc, double amount) {
        if (amount <= 0) throw new RuntimeException("Invalid transfer amount");

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            conn.setAutoCommit(false);

            try (PreparedStatement check = conn.prepareStatement("SELECT balance FROM accounts WHERE account_number = ?")) {
                check.setInt(1, fromAcc);
                ResultSet rs = check.executeQuery();
                if (!rs.next() || rs.getDouble(1) < amount)
                    throw new RuntimeException("Insufficient funds");
            }

            try (PreparedStatement debit = conn.prepareStatement("UPDATE accounts SET balance = balance - ? WHERE account_number = ?")) {
                debit.setDouble(1, amount);
                debit.setInt(2, fromAcc);
                debit.executeUpdate();
            }

            try (PreparedStatement credit = conn.prepareStatement("UPDATE accounts SET balance = balance + ? WHERE account_number = ?")) {
                credit.setDouble(1, amount);
                credit.setInt(2, toAcc);
                credit.executeUpdate();
            }

            try (PreparedStatement tx = conn.prepareStatement("INSERT INTO transactions(description, account_number) VALUES (?, ?)")) {
                tx.setString(1, "Transfer to " + toAcc + ": " + amount);
                tx.setInt(2, fromAcc);
                tx.executeUpdate();

                tx.setString(1, "Transfer from " + fromAcc + ": " + amount);
                tx.setInt(2, toAcc);
                tx.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void printLast10Transactions(int accNo) {
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement("SELECT description FROM transactions WHERE account_number = ? ORDER BY timestamp DESC LIMIT 10")) {
            stmt.setInt(1, accNo);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}