package domain;

public class Account {
    private String accountNumber;
    private String accountType;
    private Double balance;
    private String customerId;

    public Account(String accountNumber, String accountType, Double balance, String customerId) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
        this.customerId = customerId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public Double getBalance() {
        return balance;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
