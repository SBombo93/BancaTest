package it.test.java.banking.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import it.test.java.banking.bean.Creditor;
import it.test.java.banking.bean.Transaction;
import it.test.java.banking.bean.TransactionsListModel;
import it.test.java.banking.bean.external.MoneyTransferRequest;
import it.test.java.banking.bean.external.PayloadBalance;
import it.test.java.banking.bean.external.PayloadTransactionList;
import it.test.java.banking.bean.external.ResponseCallBalance;
import it.test.java.banking.bean.external.ResponseCallTranscationList;
import it.test.java.banking.bean.request.MoneyTransferRequestDTO;
import it.test.java.banking.exception.MoneyTransferException;
import it.test.java.banking.exception.BankingGenericException;
import it.test.java.banking.exception.ErrorMessage;
import it.test.java.banking.service.BankingService;

@Service
public class BankingServiceImpl implements BankingService {

	@Value("${api.key}")
	private String apiKey;
	
	@Value("${api.baseUrl}")
	private String apiBaseUrl;
	
	@Value("${api.authSchema}")
	private String apiAuthSchema;
	
	@Value("${api.accountID}")
	private String accountID;
	
	private Logger logger = LoggerFactory.getLogger(BankingServiceImpl.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public Double getBalance() throws BankingGenericException {
		
		logger.info("BankingServiceImpl getBalance");
		
		try {
			HttpHeaders headers = createHeaders();
			
			HttpEntity<Object> request = new HttpEntity<>(headers);
			ResponseEntity<ResponseCallBalance> response = restTemplate.exchange(apiBaseUrl + "/api/gbs/banking/v4.0/accounts/" + accountID + "/balance",
					HttpMethod.GET,
					request,
					ResponseCallBalance.class);
			
			ResponseCallBalance responseCallBalance = response.getBody();
			PayloadBalance payloadBalance = responseCallBalance.getPayload();
			
			logger.info("Result: BankingServiceImpl - getBalance: {}", payloadBalance.getAvailableBalance());
			return payloadBalance.getAvailableBalance();
			
		} catch(HttpClientErrorException httpClientErrorException) {
			logger.error("- ERROR - BankingServiceImpl getBalance: "+httpClientErrorException);
			throw new BankingGenericException(ErrorMessage.GENERIC_ERROR);
		}
		
	}

	@Override
	public TransactionsListModel getTransactionList(String fromAccountingDate, String toAccountingDate) throws BankingGenericException {
		logger.info("BankingServiceImpl - getTransactionList fromDate {} and toDate {}", fromAccountingDate, toAccountingDate);
		
		try {
			HttpHeaders headers = createHeaders();
			
			HttpEntity<Object> request = new HttpEntity<>(headers);
			ResponseEntity<ResponseCallTranscationList> response = restTemplate.exchange(apiBaseUrl + "/api/gbs/banking/v4.0/accounts/" + accountID + "/transactions?fromAccountingDate=" + fromAccountingDate + "&toAccountingDate=" + toAccountingDate,
					HttpMethod.GET,
					request,
					ResponseCallTranscationList.class);
			
			ResponseCallTranscationList responseCallTranscationList = response.getBody();
			PayloadTransactionList payloadTransactionList = responseCallTranscationList.getPayload();
			List<Transaction> transactionList = payloadTransactionList.getList();
			
			TransactionsListModel transactionListModel = new TransactionsListModel();
			transactionListModel.setTransactionList(transactionList);
			
			logger.info("Result: BankingServiceImpl - getTransactionList: {}", transactionListModel);
			return transactionListModel;
			
		} catch(HttpClientErrorException httpClientErrorException) {
			logger.error("- ERROR - BankingServiceImpl getTransactionList: " + httpClientErrorException);
			throw new BankingGenericException(ErrorMessage.GENERIC_ERROR);
		}
		
	}
	
	@Override
	public String createMoneyTransfer(MoneyTransferRequestDTO moneyTransferRequestDTO) throws MoneyTransferException {
		logger.info("BankingServiceImpl createMoneyTransfer - moneyTransferRequestDTO: {}", moneyTransferRequestDTO);
		
		try {
			HttpEntity<Object> moneyTransferRequest = setMoneyTransferRequest(moneyTransferRequestDTO);
			
			ResponseEntity<String> response = restTemplate.exchange(apiBaseUrl + "/api/gbs/banking/v4.0/accounts/" + accountID + "/payments/money-transfers",
					HttpMethod.POST,
					moneyTransferRequest,
					String.class);
			
			return response.getBody();
		} catch(Exception exception) {
			logger.error("- ERROR - BankingServiceImpl createMoneyTransfer: "+exception);
			throw new MoneyTransferException(ErrorMessage.ERROR_MONEY_TRANSFER);
		}
		
	}
	
	private HttpHeaders createHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Api-Key", apiKey);
		headers.set("Auth-Schema", apiAuthSchema);
		headers.set("X-Time-Zone", "Europe/Rome");
		return headers;
	}

	private HttpEntity<Object> setMoneyTransferRequest(MoneyTransferRequestDTO moneyTransferRequestDTO){
		HttpHeaders headers = createHeaders();
		MoneyTransferRequest moneyTransferRequest = new MoneyTransferRequest();
		
		Creditor creditor = new Creditor();
		creditor.setName(moneyTransferRequestDTO.getReceiverName());
		
		moneyTransferRequest.setCreditor(creditor);
		moneyTransferRequest.setExecutionDate(moneyTransferRequestDTO.getExecutionDate());
		moneyTransferRequest.setAmount(moneyTransferRequestDTO.getAmount());
		moneyTransferRequest.setDescription(moneyTransferRequestDTO.getDescription());
		moneyTransferRequest.setCurrency(moneyTransferRequestDTO.getCurrency());
		moneyTransferRequest.setFeeAccountId(accountID);
		
		HttpEntity<Object> request = new HttpEntity<>(moneyTransferRequest.toString(), headers);	
		
		return request;
	}
	
	
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
	
}