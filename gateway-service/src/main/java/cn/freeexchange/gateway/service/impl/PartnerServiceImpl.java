package cn.freeexchange.gateway.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import cn.freeexchange.gateway.domain.partner.Partner;
import cn.freeexchange.gateway.domain.rpt.PartnerRpt;
import cn.freeexchange.gateway.service.PartnerService;

@Service("partnerService")
public class PartnerServiceImpl implements PartnerService {
	
	@Resource
    private PartnerRpt partnerRpt;
	
	@SuppressWarnings("rawtypes")
	@Autowired
    private RedisTemplate redisTemplate;
	
	private static final String REDIS_PREFIX = "GW_PAR_";
	
	@PostConstruct
	public void initialization() {
		List<Partner> pars = partnerRpt.findAll();
		for (Partner par : pars) {
			Map<String, String> config = par.getConfig();
			redisTemplate.opsForHash().putAll(REDIS_PREFIX+par.getId(), config);
		}
	}
	
	
	@Override
	public String calcSgin(Long partnerId, String requestBody) {
		Partner partner = partnerRpt.getOne(partnerId);
		return partner.calcSgin(requestBody);
	}
	
	@Override
	public Map<String,String> getPartnerConfig(Long partner) {
		Map<String,String> config = redisTemplate.opsForHash().entries(REDIS_PREFIX+partner);
		return config;
	}
	
	
	@Scheduled(cron = "* */5 * * * ?")
	public void syncPartner() throws Exception {
		List<Partner> pars = partnerRpt.findAll();
		for (Partner par : pars) {
			Map<String, String> config = par.getConfig();
			redisTemplate.opsForHash().putAll(REDIS_PREFIX+par.getId(), config);
		}
	}
	
	
	
	

}
