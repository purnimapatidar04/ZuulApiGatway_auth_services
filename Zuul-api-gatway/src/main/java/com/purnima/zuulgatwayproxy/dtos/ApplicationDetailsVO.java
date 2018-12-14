package com.purnima.zuulgatwayproxy.dtos;

import java.util.List;

import lombok.Data;

@Data
public class ApplicationDetailsVO implements CommonVO {
	
	String type;
	String purpose;
	String use;
	String secured;
	String securityType;
	String otherSecurityType;
	String amount;
	String ltv;
	String tenure;
	String totalMonthlyEmi;
	String productTypeList;
	String partnerCode;
	String excludeLenderList;
	String businessLoan;
	List<String> requiredFields;
	
	@Override
	public List<String> getAllRequiredFields() {
		return this.requiredFields;
	}

}
