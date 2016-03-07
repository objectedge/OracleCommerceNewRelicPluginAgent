package com.objectedge.newrelic;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newrelic.metrics.publish.Agent;
import com.newrelic.metrics.publish.configuration.ConfigurationException;
import com.newrelic.metrics.publish.util.Logger;

/**
 * An agent for ATG Health Monitoring.
 * This agent will log ATG Health statistics
 * @author Object Edge Inc.
 */
public class MonitoringAgent extends Agent {

    private static final String GUID = "com.objectedge.oracle.atg";
    private static final String VERSION = "1.0.0";

    private static final String HTTP = "http";
    private static final String ATG_METRICS_URI = "/oe-diagnostics/rest/api/metrics/get";

    private String name;
    private URL url;
    
    private static Logger LOGGER = Logger.getLogger(MonitoringAgent.class);

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
            this.url = new URL(HTTP,host,port, ATG_METRICS_URI);
        } catch (MalformedURLException e) {
            throw new ConfigurationException("ATG Statistics Engine metric URL could not be parsed", e);
        }
    }
    
	public MonitoringAgent(String name, String host) throws ConfigurationException {
    	super(GUID, VERSION);
        try {
            this.name = name;
            this.url = new URL(HTTP,host, ATG_METRICS_URI);
        } catch (MalformedURLException e) {
            throw new ConfigurationException("ATG Statistics Engine metric URL could not be parsed", e);
        }
    }

	@Override
    public String getAgentName() {
        return name;
    }

	@Override
    public void pollCycle() {
    	 try {
    		 String jsonString = getResponse();
    		 LOGGER.debug("JSON Response: " + jsonString);
        	 ObjectMapper mapper = new ObjectMapper();
        	 MetricsResponseJSON responseJSON = mapper.readValue(jsonString, MetricsResponseJSON.class);
        	 
        	 if(responseJSON.getComponents() != null && !responseJSON.getComponents().isEmpty()) {
        		 for (ComponentJSON component : responseJSON.getComponents()) {
        			 pushMetric(component);
				}
        	 }
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    private void pushMetric(ComponentJSON component) {
    	if(component.getMetrics() != null && !component.getMetrics().isEmpty()) {
    		for (MetricJSON metric : component.getMetrics()) {
    			reportMetric(String.format("%s-%s", component.getName(), metric.getName()), metric.getUnit(), metric.getValue());
			}
    	}
	}

	/**
     * Get JSON Response from ATG
     * @return JSONObject the JSONObject response
     */
    private String getResponse() {
    	CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet(getURLString(url));
            System.out.println("Executing request " + httpget.getRequestLine());

            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

                @Override
                public String handleResponse(
                        final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }
            };
            
            return httpclient.execute(httpget, responseHandler);
            
        } catch (ClientProtocolException e) {
			e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
            try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    	return null;
    }
    
    private static String getURLString(URL url) {
		StringBuffer urlStr = new StringBuffer();
		urlStr.append(url.getProtocol()).append("://").
			append(url.getHost()).append(url.getPort() != -1 ? ":" + url.getPort() : "").append("/")
			.append(url.getFile());
		
		return urlStr.toString();
	}
    
}