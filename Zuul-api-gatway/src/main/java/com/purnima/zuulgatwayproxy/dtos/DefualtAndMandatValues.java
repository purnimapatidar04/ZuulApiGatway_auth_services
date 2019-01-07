package com.purnima.zuulgatwayproxy.dtos;

import java.util.List;

import lombok.Data;

@Data
public class DefualtAndMandatValues {

	private List<Parameters> params;
	private List<DefaultValue> defaultValues;
}
