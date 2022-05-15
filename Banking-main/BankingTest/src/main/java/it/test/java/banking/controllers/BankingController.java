package it.test.java.banking.controllers;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.test.java.banking.bean.TransactionsListModel;
import it.test.java.banking.bean.request.MoneyTransferRequestDTO;
import it.test.java.banking.bean.response.BalanceResource;
import it.test.java.banking.bean.response.TransactionsResource;
import it.test.java.banking.exception.MoneyTransferException;
import it.test.java.banking.exception.BankingException;
import it.test.java.banking.exception.BankingGenericException;
import it.test.java.banking.service.BankingService;


@RestController
@RequestMapping("/demoBanking")
public class BankingController {
	
	private Logger logger = LoggerFactory.getLogger(BankingController.class);
		
	@Autowired
	public BankingService bankingService;

	@GetMapping("/getBalance")
	@ApiOperation(value = "Find Balance", notes = "Retrieving balance")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "OK"),
	        @ApiResponse(code = 401, message = "Unauthorized"),
	        @ApiResponse(code = 500, message = "An unexpected error has occurred.", response = BankingException.class) })
	public ResponseEntity<BalanceResource> getBalance() throws BankingGenericException {
	    
		logger.info("BankingController getBalance");
		
		try {
			BalanceResource balanceResource = new BalanceResource(); 
			Double balance = bankingService.getBalance();
			
			balanceResource.setBalance(balance);
			
			logger.info("Result: BankingController - getBalance: {}", balance);
			return ResponseEntity.ok(balanceResource);
			
		} catch(BankingGenericException bankingGenericException) {
			logger.error("- ERROR - BankingController getBalance: "+bankingGenericException);
			throw bankingGenericException;
		}
	}
	
	@GetMapping("/getTransactionList")
	@ApiOperation(value = "Find Transactions List", notes = "Retrieving transactions list")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "OK"),
	        @ApiResponse(code = 400, message = "Bad Request"),
	        @ApiResponse(code = 500, message = "An unexpected error has occurred.", response = BankingException.class) })
	@ApiImplicitParams({
		@ApiImplicitParam(name = "fromAccountingDate", dataType = "String", paramType = "query", defaultValue = "YYYY-MM-DD"),
		@ApiImplicitParam(name = "toAccountingDate", dataType = "String", paramType = "query", defaultValue = "YYYY-MM-DD")
	})
	public ResponseEntity<TransactionsResource> getTransactionList(@PathParam(value = "from Accounting Date") String fromAccountingDate, @PathParam(value = "to Accounting Date") String toAccountingDate) throws BankingGenericException {
		
		logger.info("BankingController getTransactionList\nfromDate:{} - toDate {}", fromAccountingDate, toAccountingDate);
		
		try { 	
			TransactionsResource transactionsResource = new TransactionsResource();
			TransactionsListModel transactionList = bankingService.getTransactionList(fromAccountingDate, toAccountingDate);
			
			transactionsResource.setListTransaction(transactionList.getTransactionList());
			
			logger.info("Result: getTranscationList: {}", transactionsResource);
			return ResponseEntity.ok(transactionsResource);
			
		} catch(BankingGenericException bankingGenericException) {
			logger.error("- ERROR - BankingController getTransactionList: "+bankingGenericException);
			throw bankingGenericException;
		}
		
	}
	
	@PostMapping("/createMoneyTransfer")
	@ApiOperation(value = "Create money Transfer", notes = "Create Money Transfer")
	@ApiResponses(value = {
	        @ApiResponse(code = 201, message = "Created"),
	        @ApiResponse(code = 400, message = "Bad Request"),
	        @ApiResponse(code = 403, message = "Forbidden"),
	        @ApiResponse(code = 500, message = "An unexpected error has occurred.") })
	public ResponseEntity<String> createMoneyTransfer(MoneyTransferRequestDTO moneyTransferRequestDTO) throws MoneyTransferException {
		logger.info("BankingController createMoneyTransfer - moneyTransferRequestDTO: {}", moneyTransferRequestDTO);
		
		try {
			String result = bankingService.createMoneyTransfer(moneyTransferRequestDTO);
			
			logger.info("Result: createMoneyTransfer: {}", result);
			return ResponseEntity.ok(result);
		} catch(MoneyTransferException creditTransferException) {
			logger.error("- ERROR - BankingController createMoneyTransfer: "+creditTransferException);
			throw creditTransferException;
		}
		
	}
	
}