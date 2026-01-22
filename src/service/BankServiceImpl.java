package service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import domain.Account;
import domain.Transaction;
import domain.Type;
import repository.AccountRepository;
import repository.TransactionRepository;

public class BankServiceImpl implements BankService {

		private final AccountRepository accountRepository = new AccountRepository();
		private final TransactionRepository transactionRepository = new TransactionRepository();

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

	@Override
	public void deposit(String accountNumber, Double amount, String note) {
		
		Account account = accountRepository.findByNumber(accountNumber).orElseThrow
				(() -> new RuntimeException("Account not found: "+accountNumber));
		account.setBalance(account.getBalance() + amount);
		Transaction transaction = new Transaction(account.getAccountNumber(), amount, UUID.randomUUID().toString(),
				note,LocalDateTime.now(), Type.DEPOSIT);
		transactionRepository.add(transaction);
		
	}

	@Override
	public void withdrawn(String accountNumber, Double amount, String withrawn) {
		Account account = accountRepository.findByNumber(accountNumber).orElseThrow
				(() -> new RuntimeException("Account not found: "+accountNumber));
		if(account.getBalance().compareTo(amount)<0)
			throw new RuntimeException (" Insufficient Balance");
		account.setBalance(account.getBalance() + amount);
		Transaction transaction = new Transaction(account.getAccountNumber(), amount, UUID.randomUUID().toString(),
				note,LocalDateTime.now(), Type.DEPOSIT);
		transactionRepository.add(transaction);
	}

}
