package com.king.piroom.web.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void greetServer(String input, AsyncCallback<String> callback) throws IllegalArgumentException;
	void getPeriodicReadings (AsyncCallback<java.lang.Double []> callback) throws IllegalArgumentException;
	void getPiTemperatureReading(AsyncCallback<Double> callback) throws IllegalArgumentException;
	void getPiClockSpeedReading(AsyncCallback<Double> callback) throws IllegalArgumentException;
	void getPiMemoryUsageReading(AsyncCallback<Double> callback) throws IllegalArgumentException;
	void getMotionReading(AsyncCallback<Double> callback) throws IllegalArgumentException;
	void getLightReading(AsyncCallback<Double> callback) throws  IllegalArgumentException;
	void getTemperatureReading(AsyncCallback<Double> callback) throws  IllegalArgumentException;
	void setAutomationStatus(boolean on, AsyncCallback<Void> callback) throws  IllegalArgumentException;
	void getAutomationStatus(AsyncCallback<Boolean> callback) throws  IllegalArgumentException;
	void setFanStatus(boolean on, AsyncCallback<Void> callback) throws  IllegalArgumentException;
	void getFanStatus(AsyncCallback<Boolean> callback) throws  IllegalArgumentException;
	void setLightStatus(boolean on, AsyncCallback<Void> callback) throws  IllegalArgumentException;
	void getLightStatus(AsyncCallback<Boolean> callback) throws  IllegalArgumentException;
	void setFanAutomationMotionThreshold(double value, AsyncCallback<Void> callback) throws  IllegalArgumentException;
	void getFanAutomationMotionThreshold(AsyncCallback<Double> callback) throws  IllegalArgumentException;
	void setFanAutomationTemperatureThreshold(double value, AsyncCallback<Void> callback) throws  IllegalArgumentException;
	void getFanAutomationTemperatureThreshold(AsyncCallback<Double> callback) throws  IllegalArgumentException;
	void setLightAutomationMotionThreshold(double value, AsyncCallback<Void> callback) throws  IllegalArgumentException;
	void getLightAutomationMotionThreshold(AsyncCallback<Double> callback) throws  IllegalArgumentException;
	void setLightAutomationLightThreshold(double value, AsyncCallback<Void> callback) throws  IllegalArgumentException;
	void getLightAutomationLightThreshold(AsyncCallback<Double> callback) throws  IllegalArgumentException;
	
}
