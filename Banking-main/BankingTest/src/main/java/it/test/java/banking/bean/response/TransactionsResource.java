package it.test.java.banking.bean.response;

import java.util.List;

import it.test.java.banking.bean.Transaction;
import lombok.Data;

@Data
public class TransactionsResource {

	private List<Transaction> listTransaction;
	
}