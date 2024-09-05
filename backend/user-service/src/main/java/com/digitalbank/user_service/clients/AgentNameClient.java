package com.digitalbank.user_service.clients;

import com.digitalbank.user_service.models.AgentName;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "agent-name-generator")
public interface AgentNameClient {
    @GetMapping("/generate")
    AgentName generateAgentName();
}
