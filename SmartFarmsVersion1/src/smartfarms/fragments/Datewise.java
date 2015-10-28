package smartfarms.fragments;

import android.app.Fragment;

import java.util.Random;


import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.BasicStroke;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import layout.layout.R;

import android.graphics.Color;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class Datewise extends Fragment {
	
	private View mChart;
	private View view;
	private String[] mDate = new String[] {
			"10/11/14",
			"10/11/14" , 
			"10/11/14",
			"10/11/14"};
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.datewisechart, container, false);

		setUpChart();
		return view;

	}
	
	private void setUpChart() {
		int[] x = {0,1,2,3};

		// Creating an XYSeries for Potato
		XYSeries quantitySeries = new XYSeries("Quantity");
		
		// Adding data to Quantity Series
		Random rand = new Random();
		for(int i=0;i<x.length;i++){
			quantitySeries.add(i,rand.nextInt(3000));
			
		}

		// Creating a dataset to hold each series
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		// Adding Quantity Series to the dataset
		dataset.addSeries(quantitySeries);

		// Creating XYSeriesRenderer to customize potatoSeries
		XYSeriesRenderer quantityRenderer = new XYSeriesRenderer();
		//color of the graph set to blue
		quantityRenderer.setColor(Color.BLUE); 
		quantityRenderer.setFillPoints(true);
		quantityRenderer.setLineWidth(2f);
		quantityRenderer.setDisplayChartValues(true);
		//setting chart value distance
		quantityRenderer.setDisplayChartValuesDistance(10);
		//setting line graph point style to circle
		quantityRenderer.setPointStyle(PointStyle.CIRCLE);
		
		//setting stroke of the line chart to solid
		quantityRenderer.setStroke(BasicStroke.SOLID);  

		// Creating a XYMultipleSeriesRenderer to customize the whole chart
		XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
		multiRenderer.setXLabels(0);
		multiRenderer.setChartTitle("Quantity");
		multiRenderer.setAxesColor(Color.BLACK);
		multiRenderer.setGridColor(Color.BLACK);
		multiRenderer.setXTitle("Year 2014");
		multiRenderer.setYTitle("Quantity in Kgs");

		/*** Customizing graphs ***/
		//setting text size of the title
		multiRenderer.setChartTitleTextSize(28);
		//setting text size of the axis title
		multiRenderer.setAxisTitleTextSize(24);
		//setting text size of the graph lable
		multiRenderer.setLabelsTextSize(24);
		//setting zoom buttons visiblity
		multiRenderer.setZoomButtonsVisible(false);
		//setting pan enablity which uses graph to move on both axis
		multiRenderer.setPanEnabled(false, false);
		//setting click false on graph
		multiRenderer.setClickEnabled(false);
		//setting zoom to false on both axis
		multiRenderer.setZoomEnabled(false, false);
		//setting lines to display on y axis
		multiRenderer.setShowGridY(true);
		//setting lines to display on x axis
		multiRenderer.setShowGridX(true);
		//setting legend to fit the screen size
		multiRenderer.setFitLegend(true);
		//setting displaying line on grid
		multiRenderer.setShowGrid(true);
		//setting zoom to false
		multiRenderer.setZoomEnabled(false);
		//setting external zoom functions to false
		multiRenderer.setExternalZoomEnabled(false);
		//setting displaying lines on graph to be formatted(like using graphics)
		multiRenderer.setAntialiasing(true);
		//setting to in scroll to false
		multiRenderer.setInScroll(false);
		//setting to set legend height of the graph
		multiRenderer.setLegendHeight(30);
		//setting x axis label align
		multiRenderer.setXLabelsAlign(Align.CENTER);
		//setting y axis label to align
		multiRenderer.setYLabelsAlign(Align.LEFT);
		//setting text style
		multiRenderer.setTextTypeface("sans_serif", Typeface.NORMAL);
		//setting no of values to display in y axis
		multiRenderer.setYLabels(10);
		// setting y axis max value, Since i'm using static values inside the graph so i'm setting y max value to 4000.
		// if you use dynamic values then get the max y value and set here
		multiRenderer.setYAxisMax(3800);
		//setting used to move the graph on xaxis to .5 to the right
		multiRenderer.setXAxisMin(-0.5);
		//setting used to move the graph on xaxis to .5 to the right
		multiRenderer.setXAxisMax(3);
		//setting line size or space between two line
		//multiRenderer.setBarSpacing(0.5);
		//Setting background color of the graph to transparent
		multiRenderer.setBackgroundColor(Color.TRANSPARENT);
		//Setting margin color of the graph to transparent
		multiRenderer.setMarginsColor(getResources().getColor(R.color.transparent_background));
		multiRenderer.setApplyBackgroundColor(true);
		multiRenderer.setScale(2f);
		//setting x axis point size
		multiRenderer.setPointSize(4f);
		//setting the margin size for the graph in the order top, left, bottom, right
		multiRenderer.setMargins(new int[]{30, 30, 30, 30});

		for(int i=0; i< x.length;i++) {
			multiRenderer.addXTextLabel(i, mDate[i]);
		}

		// Adding quantityRenderer to multipleRenderer
		// Note: The order of adding dataseries to dataset and renderers to multipleRenderer
		// should be same
		multiRenderer.addSeriesRenderer(quantityRenderer);

		//this part is used to display graph on the xml
		LinearLayout chartContainer = (LinearLayout)view. findViewById(R.id.chart);
		//remove any views before u paint the chart
		chartContainer.removeAllViews();
		//drawing line chart
		mChart = ChartFactory.getLineChartView(getActivity(), dataset, multiRenderer);
		//adding the view to the linearlayout
		chartContainer.addView(mChart);
	}
}