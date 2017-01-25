package com.king.piroom.web.client;

import java.util.ArrayList;

import org.moxieapps.gwt.highcharts.client.Chart;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.TextAlign;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PiRoomWeb implements EntryPoint {

	private static final String LoadingIconImg="images/loading.gif";
	private static final String ManualIconImg="images/human.png";
	private static final String AutoIconImg="images/machine.png";
	private static final String FanOnIconImg="images/fan-move.png";
	private static final String FanOffIconImg="images/fan-static.png";
	private static final String LightOnIconImg="images/light-on.png";
	private static final String LightOffIconImg="images/light-off.png";
	
	private static final int LoadingDelay=1500;
	private final GreetingServiceAsync serverService = GWT.create(GreetingService.class);
	
	private Label lblControlMode;
	private Image lblControlModeIcon;
	private boolean controlModeAuto;
	
	private Chart ChartPiTemperature;
	private Chart ChartMotion;
	private Chart ChartLight;
	private Chart ChartTemperature;
	private Label lblPiClockSpeed;
	private Label lblPiMemoryUsage;
	
	private Image lblFanControlIcon;
	private boolean fanStatus;
	
	private Image lblLightControlIcon;
	private boolean lightStatus;
	
	private Slider fanAutoMotionSlider;
	private Label lblfanAutoMotionValue;
	
	private Slider fanAutoTemperatureSlider;
	private Label lblfanAutoTemperatureValue;
	
	private Slider lightAutoMotionSlider;
	private Label lbllightAutoMotionValue;
	
	private Slider lightAutoLightSlider;
	private Label lbllightAutoLightValue;
	
	public void onModuleLoad() {
		HorizontalPanel titlePanel=new HorizontalPanel();
		titlePanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		titlePanel.setStyleName("title");
			Label title=new Label("My Room Control");
			title.setStyleName("titleText");
			titlePanel.add(title);
		RootPanel.get().add(titlePanel);
		
		HorizontalPanel topPanel=new HorizontalPanel();
			topPanel.getElement().getStyle().setMarginLeft(20,Unit.PX);
			HorizontalPanel ctrlPanel=new HorizontalPanel();
			ctrlPanel.setStyleName("tilePanel");
				VerticalPanel ctrlPanelInternal=new VerticalPanel();
						lblControlMode=new Label("Control Mode");
						lblControlMode.setStyleName("tilePanelText");
					
						lblControlModeIcon=new Image(LoadingIconImg);
						lblControlModeIcon.setStyleName("tilePanelIcon");
						
					ctrlPanelInternal.add(lblControlMode);
					ctrlPanelInternal.add(lblControlModeIcon);
				ctrlPanel.add(ctrlPanelInternal);
			topPanel.add(ctrlPanel);
				
			HorizontalPanel ctrlPanelPiTemp=new HorizontalPanel();
			ctrlPanelPiTemp.setStyleName("tilePanel");
			ctrlPanelPiTemp.getElement().getStyle().setBackgroundColor("#f1c40f");
				VerticalPanel ctrlPanelPiTempInternal=new VerticalPanel();
					Label lblPiTemp=new Label("Pi's Temperature: ");
						lblPiTemp.setStyleName("tilePanelText");
					ChartPiTemperature=ChartUtility.createSolidGauge("°C", 0, 100);
						ChartPiTemperature.setStyleName("chart");
						
					ctrlPanelPiTempInternal.add(lblPiTemp);
					ctrlPanelPiTempInternal.add(ChartPiTemperature);
				ctrlPanelPiTemp.add(ctrlPanelPiTempInternal);
			topPanel.add(ctrlPanelPiTemp);

			
			HorizontalPanel ctrlPanelPiStatus=new HorizontalPanel();
			ctrlPanelPiStatus.setStyleName("tilePanel");
			ctrlPanelPiStatus.getElement().getStyle().setBackgroundColor("#9b59b6");
			ctrlPanelPiStatus.setWidth("200px");
				VerticalPanel ctrlPanelPiStatusInternal=new VerticalPanel();
					Label lblPiStatusTitle=new Label("Pi's Status: ");
						lblPiStatusTitle.setStyleName("tilePanelText");
						
					Grid g=new Grid(2,2);
						g.getElement().getStyle().setMarginTop(30,Unit.PX);
						g.getElement().getStyle().setMarginLeft(25,Unit.PX);
						
						Label lblPiStatusClockSpeed=new Label("Clock Speed :");
							lblPiStatusClockSpeed.setStyleName("tilePanelSubtext");
							lblPiStatusClockSpeed.getElement().getStyle().setTextAlign(TextAlign.RIGHT);
						g.setWidget(0,0,lblPiStatusClockSpeed);
						
							lblPiClockSpeed=new Label("0 MHz");
							lblPiClockSpeed.setStyleName("tilePanelSubtext");
						g.setWidget(0,1,lblPiClockSpeed);
						
						Label lblPiStatusMemoryUsage=new Label("Memory Usage :");
							lblPiStatusMemoryUsage.setStyleName("tilePanelSubtext");
							lblPiStatusClockSpeed.getElement().getStyle().setTextAlign(TextAlign.RIGHT);
						g.setWidget(1,0,lblPiStatusMemoryUsage);
					
							lblPiMemoryUsage=new Label("0.00 %");
							lblPiMemoryUsage.setStyleName("tilePanelSubtext");
						g.setWidget(1,1,lblPiMemoryUsage);
						
					ctrlPanelPiStatusInternal.add(lblPiStatusTitle);
					ctrlPanelPiStatusInternal.add(g);
				ctrlPanelPiStatus.add(ctrlPanelPiStatusInternal);
			topPanel.add(ctrlPanelPiStatus);
		RootPanel.get().add(topPanel);
		
		DecoratorPanel statusPanel=new DecoratorPanel();
		statusPanel.setStyleName("chartPanel");
		statusPanel.getElement().getStyle().setMarginLeft(20,Unit.PX);
			FlowPanel statusPanelInternal=new FlowPanel();
				VerticalPanel motionTile=new VerticalPanel();
				motionTile.setStyleName("chart");
					Label motionTileLabel=new Label("Motion");
						motionTileLabel.setStyleName("chartPanelTitle");
					ChartMotion=ChartUtility.createSolidGauge("move/s",0.0,10.0);
						ChartMotion.setStyleName("chart");
					motionTile.add(motionTileLabel);
					motionTile.add(ChartMotion);
				statusPanelInternal.add(motionTile);
				
				VerticalPanel lightTile=new VerticalPanel();
				lightTile.setStyleName("chart");
					Label lightTileLabel=new Label("Light");
						lightTileLabel.setStyleName("chartPanelTitle");
					ChartLight=ChartUtility.createSolidGauge("%",0.0,100.0);
						ChartLight.setStyleName("chart");
					lightTile.add(lightTileLabel);
					lightTile.add(ChartLight);
				statusPanelInternal.add(lightTile);
				
				VerticalPanel temperatureTile=new VerticalPanel();
				temperatureTile.setStyleName("chart");
					Label temperatureTileLabel=new Label("Temperature");
						temperatureTileLabel.setStyleName("chartPanelTitle");
					ChartTemperature=ChartUtility.createSolidGauge("°C",0.0,100.0);
						ChartTemperature.setStyleName("chart");
					temperatureTile.add(temperatureTileLabel);
					temperatureTile.add(ChartTemperature);
				statusPanelInternal.add(temperatureTile);
				
			statusPanel.add(statusPanelInternal);
		RootPanel.get().add(statusPanel);
		
		HorizontalPanel fanPanel=new HorizontalPanel();
		fanPanel.setStyleName("tilePanelLarge");
		fanPanel.getElement().getStyle().setBackgroundColor("#1abc9c");
		fanPanel.getElement().getStyle().setMarginLeft(20,Unit.PX);
			VerticalPanel fanPanelInternal=new VerticalPanel();
				Label lblFanPanel=new Label("Fan Control");
					lblFanPanel.setStyleName("tilePanelText");
				lblFanControlIcon=new Image(LoadingIconImg);
					lblFanControlIcon.setVisible(true);
					lblFanControlIcon.setStyleName("tilePanelIcon");
				fanPanelInternal.add(lblFanPanel);
				fanPanelInternal.add(lblFanControlIcon);
			fanPanel.add(fanPanelInternal);
			
			VerticalPanel fanPanelControl=new VerticalPanel();
				HorizontalPanel fanPanelControlMotionPanel=new HorizontalPanel();
				fanPanelControlMotionPanel.getElement().getStyle().setMarginTop(10,Unit.PX);
					Label lblfanPanelControlMotion=new Label("Motion :");
						lblfanPanelControlMotion.setStyleName("tilePanelSliderTitle");
					
					fanAutoMotionSlider=new Slider("FanMotion",0,300,new int [1]);
						fanAutoMotionSlider.setStyleName("tilePanelSlider");
						fanPanelControl.add(fanAutoMotionSlider);
						
					lblfanAutoMotionValue=new Label("0.00 move/s");
						lblfanAutoMotionValue.setStyleName("tilePanelSliderValue");
						
					fanPanelControlMotionPanel.add(lblfanPanelControlMotion);
					fanPanelControlMotionPanel.add(fanAutoMotionSlider);
					fanPanelControlMotionPanel.add(lblfanAutoMotionValue);
				fanPanelControl.add(fanPanelControlMotionPanel);
				
				HorizontalPanel fanPanelControlTemperaturePanel=new HorizontalPanel();
					Label lblfanPanelControlTemperature=new Label("Temperature :");
						lblfanPanelControlTemperature.setStyleName("tilePanelSliderTitle");
					
					fanAutoTemperatureSlider=new Slider("FanTemperature",0,10000,new int [1]);
						fanAutoTemperatureSlider.setStyleName("tilePanelSlider");
						fanPanelControl.add(fanAutoTemperatureSlider);
					
					lblfanAutoTemperatureValue=new Label("0.00 °C");
						lblfanAutoTemperatureValue.setStyleName("tilePanelSliderValue");
						
					fanPanelControlTemperaturePanel.add(lblfanPanelControlTemperature);
					fanPanelControlTemperaturePanel.add(fanAutoTemperatureSlider);
					fanPanelControlTemperaturePanel.add(lblfanAutoTemperatureValue);
				fanPanelControl.add(fanPanelControlTemperaturePanel);
			fanPanel.add(fanPanelControl);
		RootPanel.get().add(fanPanel);
		
		HorizontalPanel lightPanel=new HorizontalPanel();
		lightPanel.setStyleName("tilePanelLarge");
		lightPanel.getElement().getStyle().setBackgroundColor("#34495e");
		lightPanel.getElement().getStyle().setMarginLeft(20,Unit.PX);
			VerticalPanel lightPanelInternal=new VerticalPanel();
				Label lblLightPanel=new Label("Light Control");
					lblLightPanel.setStyleName("tilePanelText");
				lblLightControlIcon=new Image(LoadingIconImg);
					lblLightControlIcon.setVisible(true);
					lblLightControlIcon.setStyleName("tilePanelIcon");
				lightPanelInternal.add(lblLightPanel);
				lightPanelInternal.add(lblLightControlIcon);
			lightPanel.add(lightPanelInternal);
			
			VerticalPanel lightPanelControl=new VerticalPanel();
				HorizontalPanel lightPanelControlMotionPanel=new HorizontalPanel();
				lightPanelControlMotionPanel.getElement().getStyle().setMarginTop(10,Unit.PX);
					Label lbllightPanelControlMotion=new Label("Motion :");
						lbllightPanelControlMotion.setStyleName("tilePanelSliderTitle");
					
					lightAutoMotionSlider=new Slider("LightMotion",0,300,new int [1]);
						lightAutoMotionSlider.setStyleName("tilePanelSlider");
						lightPanelControl.add(lightAutoMotionSlider);
						
					lbllightAutoMotionValue=new Label("0.00 move/s");
						lbllightAutoMotionValue.setStyleName("tilePanelSliderValue");
						
					lightPanelControlMotionPanel.add(lbllightPanelControlMotion);
					lightPanelControlMotionPanel.add(lightAutoMotionSlider);
					lightPanelControlMotionPanel.add(lbllightAutoMotionValue);
				lightPanelControl.add(lightPanelControlMotionPanel);
				
				HorizontalPanel lightPanelControlLightPanel=new HorizontalPanel();
					Label lbllightPanelControlLight=new Label("Light :");
						lbllightPanelControlLight.setStyleName("tilePanelSliderTitle");
					
					lightAutoLightSlider=new Slider("LightLight",0,10000,new int [1]);
						lightAutoLightSlider.setStyleName("tilePanelSlider");
						lightPanelControl.add(lightAutoLightSlider);
					
					lbllightAutoLightValue=new Label("0.00 %");
						lbllightAutoLightValue.setStyleName("tilePanelSliderValue");
						
					lightPanelControlLightPanel.add(lbllightPanelControlLight);
					lightPanelControlLightPanel.add(lightAutoLightSlider);
					lightPanelControlLightPanel.add(lbllightAutoLightValue);
				lightPanelControl.add(lightPanelControlLightPanel);
			lightPanel.add(lightPanelControl);
		RootPanel.get().add(lightPanel);
		
		initData();
		retrieveStatusPeriodic();
	}
	
	public void initData() {
		//======================== Control Mode
		serverService.getAutomationStatus(new AsyncCallback<Boolean>() {
			@Override public void onFailure(Throwable caught) { }
			@Override
			public void onSuccess(Boolean result) {
				controlModeAuto=result;
				if (controlModeAuto) lblControlModeIcon.setUrl(AutoIconImg);
				else lblControlModeIcon.setUrl(ManualIconImg);
			}
		});
		lblControlModeIcon.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (!lblControlModeIcon.getUrl().equals(LoadingIconImg)) {
					controlModeAuto=!controlModeAuto;
					lblControlModeIcon.setUrl(LoadingIconImg);
					setControlMode();
				}
			}
		});
		//========================= Fan
		serverService.getFanStatus(new AsyncCallback<Boolean>() {
			@Override
			public void onFailure(Throwable caught) {}
			@Override
			public void onSuccess(Boolean result) {
				fanStatus=result;
				if (fanStatus) lblFanControlIcon.setUrl(FanOnIconImg);
				else lblFanControlIcon.setUrl(FanOffIconImg);
			}
		});
		lblFanControlIcon.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (!lblFanControlIcon.getUrl().equals(LoadingIconImg)) {
					fanStatus=!fanStatus;
					lblFanControlIcon.setUrl(LoadingIconImg);
					controlFan();
				}
			}
		});
		serverService.getFanAutomationMotionThreshold(new AsyncCallback<Double>() {
			@Override public void onFailure(Throwable caught) { }
			@Override
			public void onSuccess(Double result) {
				fanAutoMotionSlider.setValue((int)(result*100));
			}
		});
		fanAutoMotionSlider.addListener(new SliderListener() {
			@Override public void onStart(SliderEvent e) {}

			@Override
			public boolean onSlide(SliderEvent e) {
				lblfanAutoMotionValue.setText(NumberFormat.getFormat("0.00").format(fanAutoMotionSlider.getValue()/100.0)+" move/s");
				return true;
			}

			@Override
			public void onChange(SliderEvent e) {
				lblfanAutoMotionValue.setText(NumberFormat.getFormat("0.00").format(fanAutoMotionSlider.getValue()/100.0)+" move/s");
			}

			@Override
			public void onStop(SliderEvent e) {
				serverService.setFanAutomationMotionThreshold(fanAutoMotionSlider.getValue()/100.0, new AsyncCallback<Void>() {
					@Override public void onFailure(Throwable caught) {}
					@Override public void onSuccess(Void result) {}
				});
			}
		});
		serverService.getFanAutomationTemperatureThreshold(new AsyncCallback<Double>() {
			@Override public void onFailure(Throwable caught) { }
			@Override
			public void onSuccess(Double result) {
				fanAutoTemperatureSlider.setValue((int)(result*100));
			}
		});
		fanAutoTemperatureSlider.addListener(new SliderListener() {
			@Override public void onStart(SliderEvent e) {}

			@Override
			public boolean onSlide(SliderEvent e) {
				lblfanAutoTemperatureValue.setText(NumberFormat.getFormat("0.00").format(fanAutoTemperatureSlider.getValue()/100.0)+" °C");
				return true;
			}

			@Override
			public void onChange(SliderEvent e) {
				lblfanAutoTemperatureValue.setText(NumberFormat.getFormat("0.00").format(fanAutoTemperatureSlider.getValue()/100.0)+" °C");
			}

			@Override
			public void onStop(SliderEvent e) {
				serverService.setFanAutomationTemperatureThreshold(fanAutoTemperatureSlider.getValue()/100.0, new AsyncCallback<Void>() {
					@Override public void onFailure(Throwable caught) {}
					@Override public void onSuccess(Void result) {}
				});
			}
		});
		//========================== Light
		serverService.getLightStatus(new AsyncCallback<Boolean>() {
			@Override
			public void onFailure(Throwable caught) {
			}
			@Override
			public void onSuccess(Boolean result) {
				lightStatus=result;
				if (lightStatus) lblLightControlIcon.setUrl(LightOnIconImg);
				else lblLightControlIcon.setUrl(LightOffIconImg);
			}
		});
		lblLightControlIcon.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (!lblLightControlIcon.getUrl().equals(LoadingIconImg)) {
					lightStatus=!lightStatus;
					lblLightControlIcon.setUrl(LoadingIconImg);
					controlLight();
				}
			}
		});
		serverService.getLightAutomationMotionThreshold(new AsyncCallback<Double>() {
			@Override public void onFailure(Throwable caught) { }
			@Override
			public void onSuccess(Double result) {
				lightAutoMotionSlider.setValue((int)(result*100));
			}
		});
		lightAutoMotionSlider.addListener(new SliderListener() {
			@Override public void onStart(SliderEvent e) {}

			@Override
			public boolean onSlide(SliderEvent e) {
				lbllightAutoMotionValue.setText(NumberFormat.getFormat("0.00").format(lightAutoMotionSlider.getValue()/100.0)+" move/s");
				return true;
			}

			@Override
			public void onChange(SliderEvent e) {
				lbllightAutoMotionValue.setText(NumberFormat.getFormat("0.00").format(lightAutoMotionSlider.getValue()/100.0)+" move/s");
			}

			@Override
			public void onStop(SliderEvent e) {
				serverService.setLightAutomationMotionThreshold(lightAutoMotionSlider.getValue()/100.0, new AsyncCallback<Void>() {
					@Override public void onFailure(Throwable caught) {}
					@Override public void onSuccess(Void result) {}
				});
			}
		});
		serverService.getLightAutomationLightThreshold(new AsyncCallback<Double>() {
			@Override public void onFailure(Throwable caught) { }
			@Override
			public void onSuccess(Double result) {
				lightAutoLightSlider.setValue((int)(result*100));
			}
		});
		lightAutoLightSlider.addListener(new SliderListener() {
			@Override public void onStart(SliderEvent e) {}

			@Override
			public boolean onSlide(SliderEvent e) {
				lbllightAutoLightValue.setText(NumberFormat.getFormat("0.00").format(lightAutoLightSlider.getValue()/100.0)+" %");
				return true;
			}

			@Override
			public void onChange(SliderEvent e) {
				lbllightAutoLightValue.setText(NumberFormat.getFormat("0.00").format(lightAutoLightSlider.getValue()/100.0)+" %");
			}

			@Override
			public void onStop(SliderEvent e) {
				serverService.setLightAutomationLightThreshold(lightAutoLightSlider.getValue()/100.0, new AsyncCallback<Void>() {
					@Override public void onFailure(Throwable caught) {}
					@Override public void onSuccess(Void result) {}
				});
			}
		});
	}
	
	public void setControlMode() {
		if (controlModeAuto) {
			//lblLightControlIcon.setEnabled(false);
			//btnLightControl.setEnabled(false);
		} else {
			//btnFanControl.setEnabled(true);
			//btnLightControl.setEnabled(true);
		}
		serverService.setAutomationStatus(controlModeAuto, new AsyncCallback<Void>() {
			private void restore() {
				Timer t=new Timer() {
					public void run () {
						if (controlModeAuto) lblControlModeIcon.setUrl(AutoIconImg);
						else lblControlModeIcon.setUrl(ManualIconImg);
					}
				};
				t.schedule(LoadingDelay);
			}
			@Override
			public void onFailure(Throwable caught) {
				restore();
			}
			@Override
			public void onSuccess(Void result) {
				restore();
			}
		});
	}
	
	public void retrieveStatusPeriodic () {
		Timer t=new Timer() {

			@Override
			public void run() {
				serverService.getPeriodicReadings(new AsyncCallback<Double []>() {

					@Override public void onFailure(Throwable caught) {}

					@Override
					public void onSuccess(Double [] result) {
						ChartPiTemperature.getSeries()[0].getPoints()[0].update(Utility.round(result[0]));
						lblPiClockSpeed.setText(result[1]+" MHz");
						lblPiMemoryUsage.setText(NumberFormat.getFormat("0.00").format(result[2])+" %");
						ChartMotion.getSeries()[0].getPoints()[0].update(Utility.round(result[3]));
						ChartLight.getSeries()[0].getPoints()[0].update(Utility.round(result[4]));
						ChartTemperature.getSeries()[0].getPoints()[0].update(Utility.round(result[5]));
					}
					
				});
			}
			
		};
		t.scheduleRepeating(1000);
	}
	
	public void controlFan() {
		serverService.setFanStatus(fanStatus, new AsyncCallback<Void>() {
			private void restore() {
				Timer t=new Timer() {
					public void run () {
						if (fanStatus) lblFanControlIcon.setUrl(FanOnIconImg);
						else lblFanControlIcon.setUrl(FanOffIconImg);
					}
				};
				t.schedule(LoadingDelay);
			}
			@Override
			public void onFailure(Throwable caught) {
				restore();
			}
			@Override
			public void onSuccess(Void result) {
				restore();
			}
		});
	}
	
	public void controlLight() {
		serverService.setLightStatus(lightStatus, new AsyncCallback<Void>() {
			private void restore() {
				Timer t=new Timer() {
					public void run () {
						if (lightStatus) lblLightControlIcon.setUrl(LightOnIconImg);
						else lblLightControlIcon.setUrl(LightOffIconImg);
					}
				};
				t.schedule(LoadingDelay);
			}
			@Override
			public void onFailure(Throwable caught) {
				restore();
			}
			@Override
			public void onSuccess(Void result) {
				restore();
			}
		});
	}
}
