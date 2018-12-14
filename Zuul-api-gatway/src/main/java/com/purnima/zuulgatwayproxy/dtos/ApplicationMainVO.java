package com.purnima.zuulgatwayproxy.dtos;

import java.util.List;

public class ApplicationMainVO<T extends CommonVO> {

	private T viewObject;

	public ApplicationMainVO(T viewObject) {
		this.viewObject = viewObject;
	}

	public T getViewObject() {
		return this.viewObject;
	}
	
	public List<String> getRequiredFields(){
		return this.viewObject.getAllRequiredFields();
	}

}
