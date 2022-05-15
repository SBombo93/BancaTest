package it.test.java.banking.bean;

import lombok.Data;

@Data
public class Creditor {

	private String name;
	private Account account;
	private Address address;
	
}