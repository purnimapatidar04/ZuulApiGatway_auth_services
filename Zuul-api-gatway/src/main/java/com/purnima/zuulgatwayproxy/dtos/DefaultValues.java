package com.purnima.zuulgatwayproxy.dtos;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.Getter;


@Getter
@Component
public class DefaultValues {
	@Value("${default.values.use}")
	String use;
	

}
