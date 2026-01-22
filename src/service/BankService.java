package service;
import java.util.List;
import domain.Account;
public interface BankService {
	String openAccount(String name, String email, String accountType, Double initial);
	List<Account> listAccount();

	void deposit(String accountNumber, Double amount, String note);
	void withdrawn(String accountNumber, Double amount, String string);
}
