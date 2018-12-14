package com.purnima.zuulgatwayproxy.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.purnima.zuulgatwayproxy.model.Configurations;

@Repository
public interface ConfigRepo extends JpaRepository<Configurations, Integer>{
	
	List<Configurations> findByApiName(String apiname);
	
	@Query("SELECT c FROM Configurations c WHERE c.partnerName = :partnerName and c.apiName= :apiName")
    public Configurations findByPArtnerAndApiName(@Param("partnerName") String partnerName,@Param("apiName") String apiName);

}
