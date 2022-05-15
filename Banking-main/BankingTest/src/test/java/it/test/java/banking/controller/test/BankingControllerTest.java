package it.test.java.banking.controller.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;

import it.test.java.banking.bean.Transaction;
import it.test.java.banking.bean.TransactionsListModel;
import it.test.java.banking.bean.request.MoneyTransferRequestDTO;
import it.test.java.banking.controllers.BankingController;
import it.test.java.banking.exception.MoneyTransferException;
import it.test.java.banking.exception.BankingGenericException;
import it.test.java.banking.service.BankingService;

@WebMvcTest
public class BankingControllerTest {

	@InjectMocks
	private BankingController bankingController;
	
	@MockBean
	BankingService bankingService;
	
	@Test
	public void test_getBalance() throws BankingGenericException {
				
		ReflectionTestUtils.setField(bankingController, "bankingService", bankingService);
		
		Double balance = 123D;
		
		given(bankingService.getBalance()).willReturn(balance);
		assertNotNull(bankingController.getBalance());
		assertEquals(123D, balance);
	}
	
	@Test
	public void test_getBalance_KO() throws BankingGenericException {
				
		ReflectionTestUtils.setField(bankingController, "bankingService", bankingService);
				
		given(bankingService.getBalance()).willThrow(BankingGenericException.class);
		
		Assertions.assertThrows(BankingGenericException.class, new Executable() {
			
			@Override
			public void execute() throws Throwable {
				bankingController.getBalance();
			}
			
		});

	}
	
	@Test
	public void test_getTransactionList()  throws BankingGenericException {
				
		ReflectionTestUtils.setField(bankingController, "bankingService", bankingService);
		
		TransactionsListModel transactionsListModel = new TransactionsListModel();
		
		Transaction transaction = new Transaction();
		transaction.setAmount(120D);
		
		List<Transaction> transactionList = new ArrayList<Transaction>();
		transactionList.add(transaction);
		
		transactionsListModel.setTransactionList(transactionList);
		
		given(bankingService.getTransactionList(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).willReturn(transactionsListModel);
		assertNotNull(bankingController.getTransactionList(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()));
		assertEquals(120D, transactionList.get(0).getAmount());
	}
	
	
	@Test
	public void test_getTransactionList_KO()  throws BankingGenericException {
				
		ReflectionTestUtils.setField(bankingController, "bankingService", bankingService);
		
		TransactionsListModel transactionsListModel = new TransactionsListModel();
		
		Transaction transaction = new Transaction();
		transaction.setAmount(120D);
		
		List<Transaction> transactionList = new ArrayList<Transaction>();
		transactionList.add(transaction);
		
		transactionsListModel.setTransactionList(transactionList);
		
		given(bankingService.getTransactionList(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).willThrow(BankingGenericException.class);
		
		Assertions.assertThrows(BankingGenericException.class, new Executable() {
			
			@Override
			public void execute() throws Throwable {
				bankingController.getTransactionList(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
			}
			
		});
	}
	
	
	@Test
	public void test_createMoneyTransfer() {
		
		ReflectionTestUtils.setField(bankingController, "bankingService", bankingService);
		MoneyTransferRequestDTO moneyTransferRequestDTO = new MoneyTransferRequestDTO();

		String response = "Ok";
		given(bankingService.createMoneyTransfer(moneyTransferRequestDTO)).willReturn(response);
		
		assertNotNull(bankingController.createMoneyTransfer(moneyTransferRequestDTO));
		assertEquals("Ok", response);	
	}
	
	
	@Test
	public void test_createMoneyTransfer_KO() {
		
		ReflectionTestUtils.setField(bankingController, "bankingService", bankingService);
		MoneyTransferRequestDTO moneyTransferRequestDTO = new MoneyTransferRequestDTO();
		
		given(bankingService.createMoneyTransfer(moneyTransferRequestDTO)).willThrow(MoneyTransferException.class);
		
		Assertions.assertThrows(MoneyTransferException.class, new Executable() {
			
			@Override
			public void execute() throws Throwable {
				bankingController.createMoneyTransfer(moneyTransferRequestDTO);
			}
			
		});					
	}

	
}