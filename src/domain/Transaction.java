package domain;

import java.time.LocalDateTime;
public class Transaction {
	private String id;
	private Type type;
	private String accountNumber;
	private Double amount;
	private LocalDateTime timeStamp;
	
	public Transaction(String id, Type type, String accountNumber, Double amount, LocalDateTime timeStamp) {
		super();
		this.id = id;
		this.type = type;
		this.accountNumber = accountNumber;
		this.amount = amount;
		this.timeStamp = timeStamp;
	}
	
	

}
