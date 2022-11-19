package edu.lambton.model;

import java.util.List;

public class Client {
    private String username;
    private String password;

    private List<AccountAbstract> accounts;

    public Client() {
    }

    public Client(String username, List<AccountAbstract> accounts) {
        this.username = username;
        this.accounts = accounts;
    }

    public Client(String username, String password, List<AccountAbstract> accounts) {
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

    public List<AccountAbstract> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountAbstract> accounts) {
        this.accounts = accounts;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
