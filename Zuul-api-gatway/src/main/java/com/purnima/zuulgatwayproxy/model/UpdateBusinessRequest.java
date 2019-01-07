package com.purnima.zuulgatwayproxy.model;

import java.util.HashMap;

import lombok.Data;

@Data
public class UpdateBusinessRequest {
	private String key;
	private HashMap<String,Object> data;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public HashMap<String, Object> getData() {
		return data;
	}
	public void setData(HashMap<String, Object> data) {
		this.data = data;
	}
	
}
