package com.objectedge.newrelic;

import com.newrelic.metrics.publish.Runner;
import com.newrelic.metrics.publish.configuration.ConfigurationException;

public class ATGHealthMonitor {

	public static void main(String[] args) {
		        try {
		            Runner runner = new Runner();
		            runner.add(new MonitoringAgentFactory());
		            runner.setupAndRun(); // Never returns
		        } catch (ConfigurationException e) {
		            System.err.println("ERROR: " + e.getMessage());
		            System.exit(-1);
		        }
		    }

}
