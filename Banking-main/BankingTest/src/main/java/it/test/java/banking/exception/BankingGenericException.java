package it.test.java.banking.exception;

@SuppressWarnings("serial")
public class BankingGenericException extends RuntimeException {

	public BankingGenericException(String message) {
		super(message);
	}
 
}