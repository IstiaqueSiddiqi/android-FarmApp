package smartfarms.fragments;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.HashMap;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import smartfarms.utils.Activity;
import smartfarms.utils.FarmDBManager;

import layout.layout.R;

import android.graphics.Color;
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
import android.widget.Toast;

public class IncomeExpenseCropwise extends Fragment {

	private View mChart;
	private View view;
	private String[] mCrop;
	private float[] income, exp;
	Spinner cropWise;
	List<String> list;
	String chartType = "Expense";
	LinearLayout chartContainer;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.cropwisechart, container, false);
		getActivity().getActionBar().setTitle("Reports");
		setUpChartValues();

		cropWise = (Spinner) view.findViewById(R.id.cropWise);
		list = new ArrayList<String>();
		list.add("Income");
		list.add("Expense");

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				this.getActivity(),
				android.R.layout.simple_spinner_dropdown_item, list);

		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		cropWise.setAdapter(dataAdapter);

		cropWise.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {

				chartType = parent.getItemAtPosition(pos).toString();

				if (chartType.equals("Income")) {

					CategorySeries distributionSeries = new CategorySeries(
							"Cropwise Income");
					for (int i = 0; i < income.length; i++) {

						distributionSeries.add(mCrop[i], income[i]);

					}

					DefaultRenderer defaultRenderer = new DefaultRenderer();
					Random rand = new Random();
					defaultRenderer.setChartTitle("Cropwise Income");
					for (int i = 0; i < income.length; i++) {
						SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
						seriesRenderer.setColor(Color.rgb(rand.nextInt(255),
								rand.nextInt(255), rand.nextInt(255)));
						seriesRenderer.setDisplayChartValues(true);

						defaultRenderer.addSeriesRenderer(seriesRenderer);
					}

					defaultRenderer.setChartTitleTextSize(20);
					defaultRenderer.setZoomButtonsVisible(true);
					defaultRenderer.setDisplayValues(true);
					defaultRenderer.setLabelsColor(Color.BLACK);
					defaultRenderer.setPanEnabled(false);

					chartContainer = (LinearLayout) getActivity().findViewById(
							R.id.chart);

					chartContainer.removeAllViews();
					mChart = ChartFactory.getPieChartView(getActivity(),
							distributionSeries, defaultRenderer);

					chartContainer.addView(mChart);

				} else if (chartType.equals("Expense")) {

					CategorySeries distributionSeries = new CategorySeries(
							"Cropwise Expenses");
					for (int i = 0; i < exp.length; i++) {

						distributionSeries.add(mCrop[i], exp[i]);
					}

					DefaultRenderer defaultRenderer = new DefaultRenderer();
					Random rand = new Random();
					defaultRenderer.setChartTitle("Cropwise Expenses");
					for (int i = 0; i < exp.length; i++) {
						SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
						seriesRenderer.setColor(Color.rgb(rand.nextInt(255),
								rand.nextInt(255), rand.nextInt(255)));
						seriesRenderer.setDisplayChartValues(true);

						defaultRenderer.addSeriesRenderer(seriesRenderer);
					}

					defaultRenderer.setChartTitleTextSize(20);
					defaultRenderer.setZoomButtonsVisible(true);
					defaultRenderer.setDisplayValues(true);
					defaultRenderer.setLabelsColor(Color.BLACK);
					defaultRenderer.setPanEnabled(false);

					LinearLayout chartContainer = (LinearLayout) getActivity()
							.findViewById(R.id.chart);

					chartContainer.removeAllViews();
					mChart = ChartFactory.getPieChartView(getActivity(),
							distributionSeries, defaultRenderer);

					chartContainer.addView(mChart);
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		return view;

	}

	private void setUpChartValues() {

		List<Activity> details = new FarmDBManager()
				.getActivityDetails(getActivity());
		List<Activity> list = new ArrayList<Activity>();

		for (Activity activity : details) {

			if (activity.getType().equals("Sales")) {
				list.add(activity);
			}
		}

		String[] Crop = new String[list.size()];
		float[] in = new float[list.size()];
		float[] ex = new float[list.size()];

		for (int i = 0; i < list.size(); i++) {
			Crop[i] = list.get(i).getCrop();
			in[i] = list.get(i).getIncome();
			ex[i] = list.get(i).getExpense();
		}

		Map<String, Float> map = new HashMap<String, Float>();
		for (int i = 0; i < Crop.length; i++) {
			String name = Crop[i];
			Float sum = map.get(name);
			if (sum == null) {
				map.put(name, in[i]);
			} else {
				sum += in[i];
				map.put(name, sum);
			}
		}

		Map<String, Float> map1 = new HashMap<String, Float>();
		for (int i = 0; i < Crop.length; i++) {
			String name = Crop[i];
			Float s = map1.get(name);
			if (s == null) {
				map1.put(name, ex[i]);
			} else {
				s += ex[i];
				map1.put(name, s);
			}
		}

		mCrop = new String[map.size()];
		income = new float[map.size()];
		exp = new float[map1.size()];
		int[] x = new int[map.size()];
		int[] x1 = new int[map1.size()];
		int index = 0;
		float max = 0.0f;
		for (String n : map.keySet()) {
			mCrop[index] = n;
			income[index] = map.get(n);
			x[index] = index;
			if (income[index] > max)
				max = income[index];
			if (exp[index] > max)
				max = exp[index];
			index++;

		}

		index = 0;
		for (String n : map1.keySet()) {
			mCrop[index] = n;
			exp[index] = map1.get(n);
			x1[index] = index;
			if (exp[index] > max)
				max = exp[index];

			index++;

		}

	}

}
