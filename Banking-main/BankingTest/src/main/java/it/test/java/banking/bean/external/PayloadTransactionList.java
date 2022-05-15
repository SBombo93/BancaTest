package it.test.java.banking.bean.external;

import java.util.List;

import it.test.java.banking.bean.Transaction;
import lombok.Data;

@Data
public class PayloadTransactionList {

	private List<Transaction> list;
	
}