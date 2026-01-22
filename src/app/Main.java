package app;

import service.BankService;
import service.BankServiceImpl;
import java.util.Scanner;

public class Main {
	public static void main(String args[]) {
		Scanner sc = new Scanner(System.in);
		
		BankService bankService = new BankServiceImpl();
		boolean running = true;
		while (running) {
			System.out.println("Welcome to Console Bank");
			System.out.println("""
					1) Open Account
					2) Deposit
					3) Withdraw
					4) Transfer
					5) Account Statement
					6) List Account
					7) Search Account by Customer Name
					8) Exit
					""");
			System.out.print("CHOICE => ");
			String choice = sc.nextLine().trim();
			System.out.println("CHOICE : " + choice);

			switch (choice) {
		    case "1":
		        openAccount(sc, bankService);
		        break;
		    case "2":
		        deposit(sc,bankService);
		        break;
		    case "3":
		        withdraw(sc, bankService);
		        break;
		    case "4":
		        transfer(sc);
		        break;
		    case "5":
		        statement(sc);
		        break;
		    case "6":
		        listAccount(sc, bankService);
		        break;
		    case "7":
		        searchAccounts(sc);
		        break;
		    case "8":
		        running = false;
		        break;
		    default:
		        System.out.println("Invalid choice, please try again.");
		}


		}

	}

	private static void openAccount(Scanner sc, BankService bankService) {
		System.out.println("Cutomer Name: ");
		String name = sc.nextLine().trim();
		System.out.println("Cutomer Email: ");
		String email = sc.nextLine().trim();
		System.out.println("Cutomer Type(SAVING/CURRENT) : ");
		String accountType = sc.nextLine().trim();
		System.out.println("Initial deposit (optional, blank for 0) ");
		String amount = sc.nextLine().trim();
		Double initial = amount.isEmpty() ? 0.0 : Double.valueOf(amount);

		String accountNumber = bankService.openAccount(name, email, accountType, initial);
		if(initial>0)
			bankService.deposit(accountNumber,initial,"Initial Deposit");
		System.out.println("Account opened: "+ accountNumber);
	}

	private static void deposit(Scanner sc, BankService bankService) {
		System.out.println("Account number: ");
		String accountNumber = sc.nextLine().trim();
		System.out.println("Amount: ");
		Double amount = Double.valueOf(sc.nextLine().trim());
		bankService.deposit(accountNumber, amount, "Deposit");
		System.out.println("Deposited");

	}

	private static void withdraw(Scanner sc, BankService bankService) {
		System.out.println("Account number: ");
		String accountNumber = sc.nextLine().trim();
		System.out.println("Amount: ");
		Double amount = Double.valueOf(sc.nextLine().trim());
		bankService.withdrawn(accountNumber, amount, "Withdrawn");
		System.out.println("Withdrawn");

	}

	private static void transfer(Scanner sc) {
		// TODO Auto-generated method stub

	}

	private static void statement(Scanner sc) {
		// TODO Auto-generated method stub

	}

	private static void listAccount(Scanner sc, BankService bankService) {
		
		bankService.listAccount().forEach(a -> {
			System.out.println(a.getAccountNumber() + "|"+a.getAccountType()+"|"+a.getBalance());
		});

	}

	private static void searchAccounts(Scanner sc) {
		// TODO Auto-generated method stub

	}

}
