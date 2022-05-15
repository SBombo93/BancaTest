package it.test.java.banking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BankingExceptionHandler {

	@ExceptionHandler(value = {BankingGenericException.class})
	public ResponseEntity<Object> bankingGenericException(BankingGenericException exception){
		BankingException bankingException = new BankingException("REQ000", exception.getMessage());
		return new ResponseEntity<>(bankingException, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = {MoneyTransferException.class})
	public ResponseEntity<Object> bankingGenericException(MoneyTransferException exception){
		BankingException bankingException = new BankingException("API000", exception.getMessage());
		return new ResponseEntity<>(bankingException, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}