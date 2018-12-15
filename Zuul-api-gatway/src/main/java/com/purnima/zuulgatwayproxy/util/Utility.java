package com.purnima.zuulgatwayproxy.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public  class Utility {
	public static <T> T extractObject(Object object, Class<T> type) {
		return new ObjectMapper().convertValue(object, type);
	}
}
