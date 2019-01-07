package com.purnima.zuulgatwayproxy.dtos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import lombok.Setter;

@Setter
public class Application {
	
	
	private Double amount;
	private Integer tenure;
	private Double emiMin;
	private Double emiMax;
	private String use;
	private String type;
	private String purpose;
	private boolean secured;
	private String securityType;
	private String otherSecurityType;
	private String securityMode;
	private Double ltv;
	private Long securityValue;
	private String loanDescription;
	private ArrayList<HashMap<String, Object>> securityList;
	private String agentCode;
	private LinkedHashMap<String, String> requirement;
	private String product;
	private Boolean consent = Boolean.TRUE;
	private String mobile;
	private String msg;
	private Double netMonthlyIncome;
	private Double totalMonthlyEmi;
	private String motherName;
	private String stageStatus = "S1A1";
	private String leadFlag;
	private String partnerCode = "CT";
	private Integer agentId;
	private Integer fillerAgentId;
	private Integer smId;
	private String channel;
	private String stateRequest;

	private ArrayList<String> excludeLenderList;
	private ArrayList<String> productTypeList;
}
