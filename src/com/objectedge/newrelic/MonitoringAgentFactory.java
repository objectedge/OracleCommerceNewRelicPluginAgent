package com.objectedge.newrelic;

import java.util.Map;

import com.newrelic.metrics.publish.Agent;
import com.newrelic.metrics.publish.AgentFactory;
import com.newrelic.metrics.publish.configuration.ConfigurationException;

/**
 * AgentFactory for ATG Health Monitoring. 
 * The name of the Agent and host for ATG Health Monitoring. E.g., 'com.objectedge.HealthMonitor'.
 * @author Object Edge Inc
 */
public class MonitoringAgentFactory extends AgentFactory {

    @Override
    public Agent createConfiguredAgent(Map<String, Object> properties) throws ConfigurationException {
    	String name = (String) properties.get("name");
        String host = (String) properties.get("host");
        String port = (String) properties.get("port");
        
        if (name == null || host == null) {
            throw new ConfigurationException("'name' and 'host' cannot be null. Do you have a 'config/plugin.json' file?");
        }
        if(port == null){
        	return new MonitoringAgent(name, host);
        }else{
        	return new MonitoringAgent(name,host, Integer.parseInt(port));
        }
        	
    }
}
