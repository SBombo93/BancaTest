package it.test.java.banking.service;

import it.test.java.banking.bean.TransactionsListModel;
import it.test.java.banking.bean.request.MoneyTransferRequestDTO;
import it.test.java.banking.exception.MoneyTransferException;
import it.test.java.banking.exception.BankingGenericException;

public interface BankingService {
	
	public Double getBalance() throws BankingGenericException;
	
	public TransactionsListModel getTransactionList(String fromDate, String toDate) throws BankingGenericException;
	
	public String createMoneyTransfer(MoneyTransferRequestDTO moneyTransferRequestDTO) throws MoneyTransferException;

}