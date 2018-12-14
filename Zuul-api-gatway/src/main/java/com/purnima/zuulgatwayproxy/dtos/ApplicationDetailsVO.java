package com.purnima.zuulgatwayproxy.dtos;

public class ApplicationDetailsVO extends ApplicationMainVO {
	
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getUse() {
		return use;
	}

	public void setUse(String use) {
		this.use = use;
	}

	public String getSecured() {
		return secured;
	}

	public void setSecured(String secured) {
		this.secured = secured;
	}

	public String getSecurityType() {
		return securityType;
	}

	public void setSecurityType(String securityType) {
		this.securityType = securityType;
	}

	public String getOtherSecurityType() {
		return otherSecurityType;
	}

	public void setOtherSecurityType(String otherSecurityType) {
		this.otherSecurityType = otherSecurityType;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getLtv() {
		return ltv;
	}

	public void setLtv(String ltv) {
		this.ltv = ltv;
	}

	public String getTenure() {
		return tenure;
	}

	public void setTenure(String tenure) {
		this.tenure = tenure;
	}

	public String getTotalMonthlyEmi() {
		return totalMonthlyEmi;
	}

	public void setTotalMonthlyEmi(String totalMonthlyEmi) {
		this.totalMonthlyEmi = totalMonthlyEmi;
	}

	public String getProductTypeList() {
		return productTypeList;
	}

	public void setProductTypeList(String productTypeList) {
		this.productTypeList = productTypeList;
	}

	public String getPartnerCode() {
		return partnerCode;
	}

	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}

	public String getExcludeLenderList() {
		return excludeLenderList;
	}

	public void setExcludeLenderList(String excludeLenderList) {
		this.excludeLenderList = excludeLenderList;
	}

	public String getBusinessLoan() {
		return businessLoan;
	}

	public void setBusinessLoan(String businessLoan) {
		this.businessLoan = businessLoan;
	}

}
