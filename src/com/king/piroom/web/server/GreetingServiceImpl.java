package com.king.piroom.web.server;

import com.king.piroom.web.client.GreetingService;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements GreetingService {

	private static final String IP_ADDRESS="192.168.0.10";
	private static final int PORT=14521;

	private static Object communicate (String opcode, Object operand) {
		Object o=null;
		try {
			Socket sc=new Socket(IP_ADDRESS,PORT);
			ObjectOutputStream oos=new ObjectOutputStream(sc.getOutputStream());
			ArrayList<Object> list=new ArrayList<>();
			list.add(opcode);
			if (operand!=null) list.add(operand);
			else list.add("");
			oos.writeObject(list);
			ObjectInputStream ois=new ObjectInputStream(sc.getInputStream());
	
			o=ois.readObject();
			sc.close();
		} catch (Exception e) {}
		return o;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Double [] getPeriodicReadings() throws IllegalArgumentException {
		ArrayList<Object> list=(ArrayList<Object>)communicate("GetPeriodicReadings",null);
		Double [] value=new Double[list.size()];
		for (int i=0;i<list.size();i++) {
			if (list.get(i) instanceof Double) value[i]=(Double)list.get(i);
			else value[i]=((int)list.get(i))+0.0;
		}
		
		return value;
	}
	
	@Override
	public double getPiTemperatureReading() throws IllegalArgumentException {
		return (double)communicate("GetPiTemperatureReading",null);
	}
	
	@Override
	public double getPiClockSpeedReading() throws IllegalArgumentException {
		return (double)communicate("GetPiClockSpeedReading",null);
	}
	
	@Override
	public double getPiMemoryUsageReading() throws IllegalArgumentException {
		return (double)communicate("GetPiMemoryUsageReading",null);
	}
	
	@Override
	public double getMotionReading() throws IllegalArgumentException {
		return (double)communicate("GetMotionReading",null);
	}

	@Override
	public double getLightReading() throws IllegalArgumentException {
		return (double)communicate("GetLightReading",null);
	}

	@Override
	public double getTemperatureReading() throws IllegalArgumentException {
		return (double)communicate("GetTemperatureReading",null);
	}

	@Override
	public void setAutomationStatus(boolean on) throws IllegalArgumentException {
		communicate("SetAutomationStatus",on);
	}

	@Override
	public boolean getAutomationStatus() throws IllegalArgumentException {
		return (boolean)communicate("GetAutomationStatus" ,null);
	}

	@Override
	public void setFanStatus(boolean on) throws IllegalArgumentException {
		communicate("SetFanStatus",on);
	}

	@Override
	public boolean getFanStatus() throws IllegalArgumentException {
		return (boolean)communicate("GetFanStatus" ,null);
	}

	@Override
	public void setLightStatus(boolean on) throws IllegalArgumentException {
		communicate("SetLightStatus",on);
	}

	@Override
	public boolean getLightStatus() throws IllegalArgumentException {
		return (boolean)communicate("GetLightStatus" ,null);
	}

	@Override
	public void setFanAutomationMotionThreshold(double value) throws IllegalArgumentException {
		communicate("SetFanControlMotionThreshold",value);
	}

	@Override
	public double getFanAutomationMotionThreshold() throws IllegalArgumentException {
		return (double)communicate("GetFanControlMotionThreshold" ,null);
	}

	@Override
	public void setFanAutomationTemperatureThreshold(double value) throws IllegalArgumentException {
		communicate("SetFanControlTemperatureThreshold",value);
	}

	@Override
	public double getFanAutomationTemperatureThreshold() throws IllegalArgumentException {
		return (double)communicate("GetFanControlTemperatureThreshold" ,null);
	}

	@Override
	public void setLightAutomationMotionThreshold(double value) throws IllegalArgumentException {
		communicate("SetLightControlMotionThreshold",value);
	}

	@Override
	public double getLightAutomationMotionThreshold() throws IllegalArgumentException {
		return (double)communicate("GetLightControlMotionThreshold" ,null);
	}

	@Override
	public void setLightAutomationLightThreshold(double value) throws IllegalArgumentException {
		communicate("SetLightControlLightThreshold",value);
	}

	@Override
	public double getLightAutomationLightThreshold() throws IllegalArgumentException {
		return (double)communicate("GetLightControlLightThreshold" ,null);
	}

	@Override
	public String greetServer(String name) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}
}
