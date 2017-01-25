package com.king.piroom.web.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	String greetServer(String name) throws IllegalArgumentException;
	java.lang.Double [] getPeriodicReadings() throws IllegalArgumentException;
	double getPiTemperatureReading() throws  IllegalArgumentException;
	double getPiClockSpeedReading() throws  IllegalArgumentException;
	double getPiMemoryUsageReading() throws  IllegalArgumentException;
	double getMotionReading() throws IllegalArgumentException;
	double getLightReading() throws  IllegalArgumentException;
	double getTemperatureReading() throws  IllegalArgumentException;
	void setAutomationStatus(boolean on) throws  IllegalArgumentException;
	boolean getAutomationStatus() throws  IllegalArgumentException;
	void setFanStatus(boolean on) throws  IllegalArgumentException;
	boolean getFanStatus() throws  IllegalArgumentException;
	void setLightStatus(boolean on) throws  IllegalArgumentException;
	boolean getLightStatus() throws  IllegalArgumentException;
	void setFanAutomationMotionThreshold(double value) throws  IllegalArgumentException;
	double getFanAutomationMotionThreshold() throws  IllegalArgumentException;
	void setFanAutomationTemperatureThreshold(double value) throws  IllegalArgumentException;
	double getFanAutomationTemperatureThreshold() throws  IllegalArgumentException;
	void setLightAutomationMotionThreshold(double value) throws  IllegalArgumentException;
	double getLightAutomationMotionThreshold() throws  IllegalArgumentException;
	void setLightAutomationLightThreshold(double value) throws  IllegalArgumentException;
	double getLightAutomationLightThreshold() throws  IllegalArgumentException;
}
