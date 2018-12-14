package cn.freeexchange.gateway.domain.rpt;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.freeexchange.gateway.domain.interact.Interact;

public interface InteractRpt extends JpaRepository<Interact, Long>,JpaSpecificationExecutor<Interact> {
	
	
	@Query("from Interact where partner.id=?1 and serviceCode=?2 and traceNo=?3")
	List<Interact> findInteract(Long partner, String serviceCode, String traceNO);
	
	@Query("from Interact where partner.id=?1 and serviceCode=?2 and traceNo=?3 and oprType=?4")
	List<Interact> findInteract(Long partner, String serviceCode, String traceNO,String oprType);

}
