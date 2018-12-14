package cn.freeexchange.gateway.domain.rpt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.freeexchange.gateway.domain.partner.Partner;

public interface PartnerRpt extends JpaRepository<Partner, Long>,JpaSpecificationExecutor<Partner> {

}
