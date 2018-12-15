package com.purnima.zuulgatwayproxy.model;


import java.util.HashMap;

import lombok.Data;

@Data
public class ClientRequest<T> {

	private Token token;
	private T data;

	
}



@Data
class Token {
	private String accessToken;
	private Integer type;
	
	private Object roleId;
	private Long timeToLive;
	private Long creationTime;
	private HashMap<String,String> metaData;
	
	
}

