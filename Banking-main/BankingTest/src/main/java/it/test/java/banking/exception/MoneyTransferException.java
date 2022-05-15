package it.test.java.banking.exception;

@SuppressWarnings("serial")
public class MoneyTransferException extends RuntimeException{
	
	public MoneyTransferException(String message) {
		super(message);
	}

}