package edu.lambton.model;

import java.util.List;

public class User {
    private String username;
    private String password;

    private List<Account> accounts;

    public User() {
    }

    public User(String username, List<Account> accounts) {
        this.username = username;
        this.accounts = accounts;
    }

    public User(String username, String password, List<Account> accounts) {
        this.username = username;
        this.password = password;
        this.accounts = accounts;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
