package it.test.java.banking.bean;

import java.util.List;

import lombok.Data;


@Data
public class TransactionsListModel {
 
	private List<Transaction> TransactionList;
}