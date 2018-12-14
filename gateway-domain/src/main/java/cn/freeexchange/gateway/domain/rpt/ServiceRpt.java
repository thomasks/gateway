package cn.freeexchange.gateway.domain.rpt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.freeexchange.gateway.domain.service.Service;

public interface ServiceRpt extends JpaRepository<Service, Long>,JpaSpecificationExecutor<Service>{

}
