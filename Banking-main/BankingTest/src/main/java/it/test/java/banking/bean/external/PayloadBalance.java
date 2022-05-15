package it.test.java.banking.bean.external;

import lombok.Data;

@Data
public class PayloadBalance {

	private String date;
    private double balance;
    private double availableBalance;
    private String currency;
	
}