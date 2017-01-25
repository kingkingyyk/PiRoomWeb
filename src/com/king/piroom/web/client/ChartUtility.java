package com.king.piroom.web.client;

import org.moxieapps.gwt.highcharts.client.Axis;
import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.ChartTitle;
import org.moxieapps.gwt.highcharts.client.Color;
import org.moxieapps.gwt.highcharts.client.Credits;
import org.moxieapps.gwt.highcharts.client.Exporting;
import org.moxieapps.gwt.highcharts.client.Legend;
import org.moxieapps.gwt.highcharts.client.Pane;
import org.moxieapps.gwt.highcharts.client.PaneBackground;
import org.moxieapps.gwt.highcharts.client.Series;
import org.moxieapps.gwt.highcharts.client.Style;
import org.moxieapps.gwt.highcharts.client.ToolTip;
import org.moxieapps.gwt.highcharts.client.labels.YAxisLabels;
import org.moxieapps.gwt.highcharts.client.plotOptions.SolidGaugePlotOptions;

public class ChartUtility {

	public static Chart createSolidGauge(String unit, Number min, Number max) {
		Chart c=new Chart();
		c.setType(Series.Type.GAUGE)
					.setLegend(new Legend().setEnabled(false))
					.setCredits(new Credits().setEnabled(false))
					.setAlignTicks(false)
					.setBorderWidth(0)
					.setPlotShadow(false)
					.setExporting(new Exporting().setEnabled(false))
					.setChartTitle(new ChartTitle()
									.setText("")
									.setStyle(new Style()
												.setOption("color","white")
												.setFontFamily("'Gloria Hallelujah', cursive")))
					.setPane(new Pane()
							.setStartAngle(-90)
							.setEndAngle(90)
							.setSize(150)
							.setCenter("50%","80%")
							.setBackground(new PaneBackground()
									.setInnerRadius("60%")
									.setOuterRadius("100%")
									.setShape(PaneBackground.Shape.ARC)
									.setBackgroundColor("rgba(0,0,0,0)")
									)
							)
					.setSolidGaugePlotOptions(new SolidGaugePlotOptions().setAnimation(true));
		c.getYAxis().setMin(min).setMax(max);
		c.getYAxis()
        .setTickPosition(Axis.TickPosition.INSIDE)
        .setMinorTickPosition(Axis.TickPosition.INSIDE)
        .setLineColor("white")
        .setTickColor("white")
        .setGridLineColor("white")
        .setMinorTickColor("white")
        .setLabels(new YAxisLabels().setColor("white"))
        .setLineWidth(2)
        .setEndOnTick(true);
		
		c.setBackgroundColor(new Color()
				   .setLinearGradient(0.0, 0.0, 1.0, 1.0)
				   .addColorStop(0, 0, 0, 0, 1)
				   .addColorStop(0, 0, 0, 0, 0)
				 ).setColors("green");
		
		Series series=c.createSeries();
		series.setName("Value");
		series.addPoint(0.0);
		series.setToolTip(new ToolTip().setValueSuffix(unit).setEnabled(true));
		series.setOption("/dataLabels/format", "<div style=\"text-align:center;border-width:0\"><span style=\"font-size:25px;color:'white')><br/><br/>{y} "+unit+"</div>");
		series.setOption("/dial/backgroundColor", "white");
		c.addSeries(series);
		return c;
	}
}
