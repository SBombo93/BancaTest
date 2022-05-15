package it.test.java.banking.bean.external;

import java.util.List;

import lombok.Data;

@Data
public class ResponseCallBalance {

	private String status;
	private List<Error> errors;
    private PayloadBalance payload; 

}