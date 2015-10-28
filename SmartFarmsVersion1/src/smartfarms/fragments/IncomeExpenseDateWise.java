package smartfarms.fragments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.BasicStroke;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import smartfarms.utils.Activity;
import smartfarms.utils.FarmDBManager;

import layout.layout.R;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class IncomeExpenseDateWise extends Fragment {

	private View mChart, view;
	private String[] mDate, date;
	private float[] income, exp, in, ex;
	private Spinner cropWise;
	private List<String> spinnerList;
	private List<Activity> details;
	String[] d;
	String yr = "2015";
	List<Activity> list;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.cropwisechart, container, false);
		getActivity().getActionBar().setTitle("Reports");
		details = new FarmDBManager().getActivityDetails(getActivity());

		cropWise = (Spinner) view.findViewById(R.id.cropWise);
		spinnerList = new ArrayList<String>();

		for (Activity activity : details) {
			String args[] = activity.getDate().trim().split("-");
			if (!spinnerList.contains(args[2])) {
				spinnerList.add(args[2]);
			}
		}

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				this.getActivity(),
				android.R.layout.simple_spinner_dropdown_item, spinnerList);

		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		cropWise.setAdapter(dataAdapter);

		cropWise.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {

				yr = parent.getItemAtPosition(pos).toString();

				list = new ArrayList<Activity>();

				for (Activity activity : details) {
					String args[] = activity.getDate().trim().split("-");

					if (activity.getType().equals("Sales")
							&& args[2].equals(yr)) {
						list.add(activity);
					}
				}
				setUpChart();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		return view;

	}

	private void setUpChart() {

		XYSeries incomeSeries = new XYSeries("Income");

		XYSeries expenseSeries = new XYSeries("Expense");

		String[] date = new String[list.size()];
		float[] in = new float[list.size()];
		float[] ex = new float[list.size()];

		for (int i = 0; i < list.size(); i++) {

			date[i] = list.get(i).getDate();

			in[i] = list.get(i).getIncome();
			ex[i] = list.get(i).getExpense();
		}

		Map<String, Float> map = new HashMap<String, Float>();
		for (int i = 0; i < date.length; i++) {
			String[] dateArray = date[i].split("-");
			String month = dateArray[1];

			Float sum = map.get(month);
			if (sum == null) {
				map.put(month, in[i]);
			} else {
				sum += in[i];
				map.put(month, sum);
			}
		}

		Map<String, Float> map1 = new HashMap<String, Float>();
		for (int i = 0; i < date.length; i++) {
			String[] dateArray = date[i].split("-");
			String month = dateArray[1];
			String year = dateArray[2];

			String name = date[i];
			Float s = map1.get(month);
			if (s == null) {
				map1.put(month, ex[i]);
			} else {
				s += ex[i];
				map1.put(month, s);
			}
		}

		mDate = new String[map.size()];
		income = new float[map.size()];
		exp = new float[map1.size()];

		int index = 0;
		float max = 0.0f;
		int[] mon = new int[map.size()];
		String[] month = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
				"Aug", "Sep", "Oct", "Nov", "Dec" };

		SortedSet<String> val = new TreeSet<String>(map.keySet());
		int p = 0;
		for (String n : map.keySet()) {
			mon[p++] = Integer.parseInt(n);

		}
		Arrays.sort(mon);

			for (String n : map.keySet()) {
				mDate[index] = month[mon[index] - 1];
				income[index] = map.get(mon[index] + "");

				if (income[index] > max)
					max = income[index];

				index++;
			}

		index = 0;
		for (String n : map1.keySet()) {
			mDate[index] = month[mon[index] - 1];
			exp[index] = map1.get(mon[index] + "");

			if (exp[index] > max)
				max = exp[index];

			index++;

		}

		for (int i = 0; i < mDate.length; i++) {
			incomeSeries.add(i, income[i]);
			expenseSeries.add(i, exp[i]);
		}

		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

		dataset.addSeries(incomeSeries);

		dataset.addSeries(expenseSeries);

		XYSeriesRenderer incomeRenderer = new XYSeriesRenderer();
		incomeRenderer.setColor(Color.BLUE);
		incomeRenderer.setFillPoints(true);
		incomeRenderer.setLineWidth(2f);
		incomeRenderer.setDisplayChartValues(true);

		incomeRenderer.setDisplayChartValuesDistance(10);

		incomeRenderer.setPointStyle(PointStyle.CIRCLE);

		incomeRenderer.setStroke(BasicStroke.SOLID);

		XYSeriesRenderer expenseRenderer = new XYSeriesRenderer();
		expenseRenderer.setColor(Color.RED);
		expenseRenderer.setFillPoints(true);
		expenseRenderer.setLineWidth(2f);
		expenseRenderer.setDisplayChartValues(true);

		expenseRenderer.setPointStyle(PointStyle.SQUARE);

		expenseRenderer.setStroke(BasicStroke.SOLID);

		XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
		multiRenderer.setXLabels(0);
		multiRenderer.setXLabelsColor(Color.BLACK);
		multiRenderer.setChartTitle("Income Expense Chart");
		multiRenderer.setAxesColor(Color.BLACK);
		multiRenderer.setGridColor(Color.BLACK);
		multiRenderer.setMarginsColor(Color.YELLOW);
		multiRenderer.setXTitle("Year " + yr);
		multiRenderer.setYTitle("Income/Expense (Rs)");

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

		multiRenderer.setYAxisMax(max);

		multiRenderer.setXAxisMin(-0.5);

		multiRenderer.setXAxisMax(11);

		multiRenderer.setBarSpacing(0.5);

		multiRenderer.setBackgroundColor(Color.TRANSPARENT);

		multiRenderer.setMarginsColor(getResources().getColor(
				R.color.transparent_background));
		multiRenderer.setApplyBackgroundColor(true);
		multiRenderer.setScale(2f);

		multiRenderer.setPointSize(4f);

		multiRenderer.setMargins(new int[] { 30, 30, 30, 30 });

		for (int i = 0; i < mDate.length; i++) {
			multiRenderer.addXTextLabel(i, mDate[i]);
		}

		multiRenderer.addSeriesRenderer(incomeRenderer);
		multiRenderer.addSeriesRenderer(expenseRenderer);

		LinearLayout chartContainer = (LinearLayout) view
				.findViewById(R.id.chart);

		chartContainer.removeAllViews();

		mChart = ChartFactory.getBarChartView(getActivity(), dataset,
				multiRenderer, Type.DEFAULT);

		chartContainer.addView(mChart);

	}

}
