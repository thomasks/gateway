package cn.freeexchange.gateway.service;

import java.util.Map;

import cn.freeexchange.gateway.domain.interact.Interact;

public interface InteractService {
	
	Interact interact(Map<String, String> header, String body, String ip);

    Interact doCreate(Map<String, String> headerMap, String body, String ip);

}
