package com.purnima.zuulgatwayproxy.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.purnima.zuulgatwayproxy.model.Configurations;
import com.purnima.zuulgatwayproxy.model.FieldsMaster;

@Repository
public interface ConfigRepo extends JpaRepository<Configurations, Integer>{
	
	List<Configurations> findByApiName(String apiname);
	
	@Query("SELECT c FROM Configurations c WHERE c.partnerName = :partnerName and c.apiName= :apiName")
    public Configurations findByPArtnerAndApiName(@Param("partnerName") String partnerName,@Param("apiName") String apiName);

	
//	@Query(value="select fm.id from fields_master fm inner join pm_fm pf on pf.fm_id=fm.id inner join am_pm_fm apf on apf.pm_fm_id= pf.id inner join api_master am on am.id=apf.am_id inner join partner_master pm on pm.id=pf.pm_id where fm.business_type=:businessType and am.name=:apiName and  pm.name=:partnerName",nativeQuery=true)
//	public List<Object> findFieldsByApiNameAndPartner(@Param("partnerName") String partnerName,@Param("apiName") String apiName,@Param("businessType") String businessType);

	@Query(value="select fm.id,fm.name,fm.business_type businessType,fm.dependent_field dependentFId,am.name apiName,pm.name partnerName from fields_master fm inner join pm_fm pf on pf.fm_id=fm.id inner join am_pm_fm apf on apf.pm_fm_id= pf.id inner join api_master am on am.id=apf.am_id inner join partner_master pm on pm.id=pf.pm_id where (fm.business_type=:businessType or fm.business_type='ALL') and am.name=:apiName and  pm.name=:partnerName",nativeQuery=true)
	public Object[][] findFieldsByApiNameAndPartner(@Param("partnerName") String partnerName,@Param("apiName") String apiName,@Param("businessType") String businessType);

	@Query(value="select d.id,d.name,d.values from defualt_values d where d.api_master_id=(select id from api_master where name=:apiName)",nativeQuery=true)
    public Object[][] getDefaultValuesByApiName(@Param("apiName") String apiName);
}



