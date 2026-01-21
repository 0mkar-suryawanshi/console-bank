package service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import domain.Account;
import repository.AccountRepository;

public class BankServiceImpl implements BankService {

	private final AccountRepository accountRepository = new AccountRepository();

	@Override
	public String openAccount(String name, String email, String accountType,Double deposit) {
		String customerId = UUID.randomUUID().toString();
		// String accountNumber = UUID.randomUUID().toString();

		// CHANGE LATER--> 10 + 1 = AC11

		String accountNumber = getAccountNumber();
		Account a = new Account(accountNumber, accountType, (double) 0, customerId);
		accountRepository.save(a);
		return accountNumber;
	}
	
	private String getAccountNumber() {
		int size= accountRepository.findAll().size() +1;
		String accountNumber = String.format("AC%06d", size);
		return accountNumber;
	}

	@Override
	public List<Account> listAccount() {

		return accountRepository.findAll().stream().sorted
				(Comparator.comparing(Account::getAccountNumber)).collect(Collectors.toList());
	}

}
