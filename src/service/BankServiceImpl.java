package service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import domain.Account;
import domain.Customer;
import domain.Transaction;
import domain.Type;
import repository.AccountRepository;
import repository.CustomerRepository;
import repository.TransactionRepository;

public class BankServiceImpl implements BankService {

		private final AccountRepository accountRepository = new AccountRepository();
		private final TransactionRepository transactionRepository = new TransactionRepository();
		private final CustomerRepository customerRepository = new CustomerRepository();


	@Override
	public String openAccount(String name, String email, String accountType,Double deposit) {
		String customerId = UUID.randomUUID().toString();
		// String accountNumber = UUID.randomUUID().toString();

		// CHANGE LATER--> 10 + 1 = AC11
		
		Customer c = new Customer(customerId, name, email);
		customerRepository.save(c);
		

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
	public void withdrawn(String accountNumber, Double amount, String note) {
		Account account = accountRepository.findByNumber(accountNumber).orElseThrow
				(() -> new RuntimeException("Account not found: "+accountNumber));
		if(account.getBalance().compareTo(amount)<0)
			throw new RuntimeException (" Insufficient Balance");
		account.setBalance(account.getBalance() - amount);
		Transaction transaction = new Transaction(account.getAccountNumber(), amount, UUID.randomUUID().toString(),
				note,LocalDateTime.now(), Type.WITHDRAW);
		transactionRepository.add(transaction);
	}

	@Override
	public void transfer(String fromAcc, String toAcc, Double amount, String note) {
		if(fromAcc.equals(toAcc))
		{
			throw new RuntimeException("Cannot Transfer to your own account");
		}
		Account from = accountRepository.findByNumber(fromAcc).orElseThrow
				(() -> new RuntimeException("Account not found: "+fromAcc));
		Account to = accountRepository.findByNumber(toAcc).orElseThrow
				(() -> new RuntimeException("Account not found: "+toAcc));
		
		if(from.getBalance().compareTo(amount)<0)
			throw new RuntimeException (" Insufficient Balance");
		
		from.setBalance(from.getBalance() - amount);
		to.setBalance(to.getBalance() + amount);
		
		
		transactionRepository.add(new Transaction(from.getAccountNumber(), amount, UUID.randomUUID().toString(),
				note,LocalDateTime.now(), Type.TRANSFER_IN));
		
		transactionRepository.add(new Transaction(to.getAccountNumber(), amount, UUID.randomUUID().toString(),
				note,LocalDateTime.now(), Type.TRANSFER_OUT));

		
		
	}

	@Override
	public List<Transaction> getStatement(String account) {
		return transactionRepository.findByAccount(account).stream().
				sorted(Comparator.comparing(Transaction::getTimeStamp))
				.collect(Collectors.toList());
	}

	@Override
	public List<Account> searchAccountsByCustomerName(String q) {
		String query = (q==null)? "" : q.toLowerCase();
		List<Account> result = new ArrayList<>();
		for(Customer c: customerRepository.findAll())
		{
			if(c.getName().toLowerCase().contains(query))
			{
				result.addAll(accountRepository.findByCustomerId(c.getId()));
			}
		}
		result.sort(Comparator.comparing(Account::getAccountNumber));
		return result;
	}

}
