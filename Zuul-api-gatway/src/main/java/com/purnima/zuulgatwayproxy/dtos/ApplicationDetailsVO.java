package com.purnima.zuulgatwayproxy.dtos;

import java.util.List;

import lombok.Data;

@Data
public class ApplicationDetailsVO extends ApplicationMainVO {
	
	String type;
	String purpose;
	String use;
	String secured;
	String securityType;
	String otherSecurityType;
	String securityValue;
	String amount;
	String ltv;
	String tenure;
	String totalMonthlyEmi;
	List<String> productTypeList;
	String partnerCode;
	List<String> excludeLenderList;
	String businessLoan;


	
}
