package it.test.java.banking.exception;

import lombok.Data;

@Data
public class BankingException {

	private final String code;
	private final String description;
	
}