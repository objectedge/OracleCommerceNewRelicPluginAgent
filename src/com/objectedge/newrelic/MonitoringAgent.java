package com.objectedge.newrelic;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.newrelic.metrics.publish.Agent;
import com.newrelic.metrics.publish.configuration.ConfigurationException;
import com.newrelic.metrics.publish.processors.EpochCounter;
import com.newrelic.metrics.publish.processors.Processor;

/**
 * An agent for ATG Health Monitoring.
 * This agent will log ATG Health statistics
 * @author Object Edge Inc.
 */
public class MonitoringAgent extends Agent {

    private static final String GUID = "com.objectedge.oracle.atg";
    private static final String VERSION = "1.0.0";

    private static final String HTTP = "http";
    private static final String ATG_INSTANCE_URL = "/AtgStatisticsEngine/index.jsp";

    private String name;
    private URL url;
    private Processor repositoryCreationRate;

    /**
     * Constructor for ATG Health Monitoring Agent.
     * Uses name for Component Human Label and host for building ATG Health Monitoring Metric service.
     * @param name
     * @param host
     * @throws ConfigurationException if URL for ATG Statistics Engine's metric service could not be built correctly from provided host
     */
    public MonitoringAgent(String name, String host, int port) throws ConfigurationException {
        super(GUID, VERSION);
        try {
            this.name = name;
            this.url = new URL(HTTP,host,port, ATG_INSTANCE_URL);
            this.repositoryCreationRate = new EpochCounter();
        } catch (MalformedURLException e) {
            throw new ConfigurationException("ATG Statistics Engine metric URL could not be parsed", e);
        }
    }
    
    public MonitoringAgent(String name, String host) throws ConfigurationException {
    	super(GUID, VERSION);
        try {
            this.name = name;
            this.url = new URL(HTTP,host, ATG_INSTANCE_URL);
            this.repositoryCreationRate = new EpochCounter();
        } catch (MalformedURLException e) {
            throw new ConfigurationException("ATG Statistics Engine metric URL could not be parsed", e);
        }
    }


	@Override
    public String getComponentHumanLabel() {
        return name;
    }

    @Override
    public void pollCycle() {
    	 JSONObject jsonObj = getJSONResponse();
    	 if (jsonObj != null) {
    		 JSONObject statistics = (JSONObject) jsonObj.get("metrics");
    		 HashMap<String,JSONObject> repositories = (HashMap<String,JSONObject>) statistics.get("repositories");
    		 Iterator<String> repositoryIterator = repositories.keySet().iterator();
    		 while(repositoryIterator.hasNext()){
    			 String repositoryName = repositoryIterator.next();
    			 HashMap<String,JSONObject> itemDescriptors = (HashMap<String,JSONObject>) repositories.get(repositoryName);
    			 Iterator<String> itemDescriptorIterator = itemDescriptors.keySet().iterator();
    			 while(itemDescriptorIterator.hasNext()){
    				 String itemDescriptorName = itemDescriptorIterator.next();
    				 HashMap<String,JSONObject> cacheStatistics = (HashMap<String,JSONObject>)itemDescriptors.get(itemDescriptorName);
    		         	Iterator<String> cacheStatisticsIter = cacheStatistics.keySet().iterator();
    		         	while(cacheStatisticsIter.hasNext()){
    		         		String cacheStatistic = cacheStatisticsIter.next();
    		         		HashMap<String,Number> units = cacheStatistics.get(cacheStatistic);
    		         		Iterator<String> unitIter   = units.keySet().iterator();
    		         		while(unitIter.hasNext()){
    		         			String unit = unitIter.next();
    		         			reportMetric(repositoryName+":"+itemDescriptorName+":"+cacheStatistic, unit, units.get(unit));
    		         		}
    		         	}
    			 }
    		 }
         } 
    }


    /**
     * Get JSON Response from ATG
     * @return JSONObject the JSONObject response
     */
    private JSONObject getJSONResponse() {
        Object response = null;
        InputStream inputStream = null;
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty("Accept", "application/json");
            inputStream = connection.getInputStream();
            response = JSONValue.parse(new InputStreamReader(inputStream));
        } catch (IOException e) {
            System.out.println("Error: Unable to access to agent at "+url.getHost()+":"+url.getPort());
        } 	finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {}
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return (JSONObject) response;
    }

}
