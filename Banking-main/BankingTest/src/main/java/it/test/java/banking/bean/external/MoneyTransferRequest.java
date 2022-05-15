package it.test.java.banking.bean.external;

import it.test.java.banking.bean.Creditor;
import it.test.java.banking.bean.TaxRelief;
import lombok.Data;

@Data
public class MoneyTransferRequest {

	private Creditor creditor;
	private String executionDate;
	private String uri;
	private String description;
	private Double amount;
	private String currency;
	private boolean isUrgent;
	private boolean isInstant;
	private String feeType;
	private String feeAccountId;
	private TaxRelief taxRelief;
	
}