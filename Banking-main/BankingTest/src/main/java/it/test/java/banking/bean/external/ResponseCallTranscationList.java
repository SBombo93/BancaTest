package it.test.java.banking.bean.external;

import java.util.List;

import lombok.Data;

@Data
public class ResponseCallTranscationList {
	
	private String status;
	private List<Error> errors;
    private PayloadTransactionList payload; 

}