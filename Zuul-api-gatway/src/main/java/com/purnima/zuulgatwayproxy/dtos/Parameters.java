package com.purnima.zuulgatwayproxy.dtos;

import java.util.List;

import lombok.Data;

@Data
public class Parameters {
	private String businessType;
	private List<String> mandatParams;
	private List<String> dependentParams;

}
