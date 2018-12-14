package com.purnima.zuulgatwayproxy.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	    
	    
}
