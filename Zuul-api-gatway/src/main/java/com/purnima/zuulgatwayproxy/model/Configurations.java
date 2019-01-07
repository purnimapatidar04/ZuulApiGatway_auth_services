package com.purnima.zuulgatwayproxy.model;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "configurations")
@Data
public class Configurations  implements Serializable{

	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    @Column(name = "config_id")
	    private int configId;
	    
	    
	    @Column(name="partner_name")
	    private String partnerName;
	    
	    @Column(name="api_name")
	    private String apiName;
	    
	    @Column(name="mandate_parm")
	    private String mandateParm;
	    
	    @Lob
	    @Column(name="mandate_parm1")
	    private String mandateParm1;
	    
	    
	    public List<String> fieldsForValidation(){
	    	return Arrays.asList(this.mandateParm.split(","));
	    }
	    
	    
}
