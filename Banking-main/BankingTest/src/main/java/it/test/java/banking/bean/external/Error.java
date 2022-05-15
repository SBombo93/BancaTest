package it.test.java.banking.bean.external;

import lombok.Data;

@Data
public class Error {

	private String code;
    private String description;
    private String params;
	
}