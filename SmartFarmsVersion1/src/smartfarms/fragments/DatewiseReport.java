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

public class DatewiseReport extends Fragment {

	private View mChart;
	private View view;
	private String[] mDate = new String[] { "10/11/14", "10/11/14", "10/11/14",
			"10/11/14" };

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.datewisechart, container, false);

		setUpChart();
		return view;

	}

	private void setUpChart() {
		int[] x = { 0, 1, 2, 3 };

		XYSeries quantitySeries = new XYSeries("Quantity");

		Random rand = new Random();
		for (int i = 0; i < x.length; i++) {
			quantitySeries.add(i, rand.nextInt(3000));

		}

		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

		dataset.addSeries(quantitySeries);

		XYSeriesRenderer quantityRenderer = new XYSeriesRenderer();

		quantityRenderer.setColor(Color.BLUE);
		quantityRenderer.setFillPoints(true);
		quantityRenderer.setLineWidth(2f);
		quantityRenderer.setDisplayChartValues(true);

		quantityRenderer.setDisplayChartValuesDistance(10);

		quantityRenderer.setPointStyle(PointStyle.CIRCLE);

		quantityRenderer.setStroke(BasicStroke.SOLID);

		XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
		multiRenderer.setXLabels(0);
		multiRenderer.setChartTitle("Quantity");
		multiRenderer.setAxesColor(Color.BLACK);
		multiRenderer.setGridColor(Color.BLACK);
		multiRenderer.setXTitle("Year 2014");
		multiRenderer.setYTitle("Quantity in Kgs");

		multiRenderer.setChartTitleTextSize(28);

		multiRenderer.setAxisTitleTextSize(24);

		multiRenderer.setLabelsTextSize(24);

		multiRenderer.setZoomButtonsVisible(false);

		multiRenderer.setPanEnabled(false, false);

		multiRenderer.setClickEnabled(false);

		multiRenderer.setZoomEnabled(false, false);

		multiRenderer.setShowGridY(true);

		multiRenderer.setShowGridX(true);

		multiRenderer.setFitLegend(true);

		multiRenderer.setShowGrid(true);

		multiRenderer.setZoomEnabled(false);

		multiRenderer.setExternalZoomEnabled(false);

		multiRenderer.setAntialiasing(true);

		multiRenderer.setInScroll(false);

		multiRenderer.setLegendHeight(30);

		multiRenderer.setXLabelsAlign(Align.CENTER);

		multiRenderer.setYLabelsAlign(Align.LEFT);

		multiRenderer.setTextTypeface("sans_serif", Typeface.NORMAL);

		multiRenderer.setYLabels(10);

		multiRenderer.setYAxisMax(3800);

		multiRenderer.setXAxisMin(-0.5);

		multiRenderer.setXAxisMax(3);

		multiRenderer.setBackgroundColor(Color.TRANSPARENT);

		multiRenderer.setMarginsColor(getResources().getColor(
				R.color.transparent_background));
		multiRenderer.setApplyBackgroundColor(true);
		multiRenderer.setScale(2f);

		multiRenderer.setPointSize(4f);

		multiRenderer.setMargins(new int[] { 30, 30, 30, 30 });

		for (int i = 0; i < x.length; i++) {
			multiRenderer.addXTextLabel(i, mDate[i]);
		}

		multiRenderer.addSeriesRenderer(quantityRenderer);

		LinearLayout chartContainer = (LinearLayout) view
				.findViewById(R.id.chart);

		chartContainer.removeAllViews();

		mChart = ChartFactory.getLineChartView(getActivity(), dataset,
				multiRenderer);

		chartContainer.addView(mChart);
	}
}