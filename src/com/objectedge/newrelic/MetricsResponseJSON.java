package com.objectedge.newrelic;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a POJO which holds metric response.
 * 
 * @author Object Edge Inc
 *
 */
public class MetricsResponseJSON {
	private boolean success;
	private String message;
	private int status;
	private List<ComponentJSON> components;
	
	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}
	/**
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	/**
	 * @return the components
	 */
	public List<ComponentJSON> getComponents() {
		return components;
	}
	/**
	 * @param components the components to set
	 */
	public void setComponents(List<ComponentJSON> components) {
		this.components = components;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getClass().getName()).append(": {").
			append("success: ").append(this.success)
			.append(", status: ").append(this.status)
			.append(", message: ").append(this.message)
			.append(", components: ").append(this.components)
			.append("}");
		return sb.toString();
	}
	
	public static void main(String[] args) {
		MetricsResponseJSON model = new MetricsResponseJSON();
		List<ComponentJSON> components = new ArrayList<ComponentJSON>();
		List<MetricJSON> metrics = new ArrayList<MetricJSON>();
		
		for (int i = 0; i < 3; i++) {
			ComponentJSON comp = new ComponentJSON();

			for (int j = 0; j < 2; j++) {
				MetricJSON metric = new MetricJSON();
				metrics.add(metric);
			}
			comp.setMetrics(metrics);
			components.add(comp);
		}
		model.setComponents(components);
		
		System.out.println(model);
	}
}

class ComponentJSON {
	private String name;
	private String description;
	private List<MetricJSON> metrics;
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the metrics
	 */
	public List<MetricJSON> getMetrics() {
		return metrics;
	}
	/**
	 * @param metrics the metrics to set
	 */
	public void setMetrics(List<MetricJSON> metrics) {
		this.metrics = metrics;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getClass().getName()).append(" :{").
			append("name: ").append(this.name)
			.append(", description: ").append(this.description)
			.append(", metrics: ").append(this.metrics)
			.append("}");
		return sb.toString();
	}
}

class MetricJSON {
	private String name;
	private String unit;
	private double value;
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the unit
	 */
	public String getUnit() {
		return unit;
	}
	/**
	 * @param unit the unit to set
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}
	/**
	 * @return the value
	 */
	public double getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(double value) {
		this.value = value;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getClass().getName()).append(":{")
			.append("name: ").append(this.name)
			.append(", unit: ").append(this.unit)
			.append(", value: ").append(this.value)
			.append("}");
		return sb.toString();
	}
}	