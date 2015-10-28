package smartfarms.fragments;

import java.util.Calendar;
import java.util.List;

import smartfarms.utils.Activity;
import smartfarms.utils.FarmDBManager;
import layout.layout.R;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class ShowEditHarvest extends Fragment {
	EditText hehtdate, heqty_harvest, heincome_harvest, heno_labourers_harvest;
	Button heSaveHarvest, heDeleteHarvest;
	Float expenses;
	String landname, type, category;
	int id;

	private int year;
	private int month;
	private int day;
	static final int DATE_PICKER_ID = 1111;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.show_or_edit_harvest, container,
				false);
		final String cropname = getArguments().getString("message");

		hehtdate = (EditText) view.findViewById(R.id.hehtdate);

		hehtdate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {

				showDatePicker();
			}
		});

		heqty_harvest = (EditText) view.findViewById(R.id.heqty_harvest);
		heincome_harvest = (EditText) view.findViewById(R.id.heincome_harvest);
		heno_labourers_harvest = (EditText) view
				.findViewById(R.id.heno_labourers_harvest);

		List<Activity> details = new FarmDBManager()
				.getActivityDetails(getActivity().getApplicationContext());

		for (Activity activity : details) {

			if (cropname.equals(activity.getCrop())
					&& (activity.getType()).equals("Harvest")) {

				hehtdate.setText(activity.getDate());
				heqty_harvest.setText(Integer.toString(activity.getQuantity()));
				heno_labourers_harvest.setText(Integer.toString(activity
						.getLabourers()));

				heincome_harvest.setText(Float.toString(activity.getExpense()));
				expenses = activity.getIncome();
				landname = activity.getLand();
				type = activity.getType();
				category = activity.getCategory();
				id = activity.getId();
				break;
			}
		}

		heSaveHarvest = (Button) view.findViewById(R.id.heSaveHarvest);
		heSaveHarvest.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Activity activity = new Activity();
				activity.setDate(hehtdate.getText().toString());
				activity.setExpense(Float.parseFloat(heincome_harvest.getText()
						.toString()));
				activity.setLabourers(Integer.parseInt(heno_labourers_harvest
						.getText().toString()));
				activity.setCrop(cropname);
				activity.setCategory(category);
				activity.setIncome(expenses);
				activity.setLand(landname);
				activity.setType(type);
				activity.setQuantity(Integer.parseInt(heqty_harvest.getText()
						.toString()));

				new FarmDBManager().editActivity(getActivity(), activity);

				hehtdate.setText("");
				heqty_harvest.setText("");
				heincome_harvest.setText("");
				heno_labourers_harvest.setText("");

				Fragment newFragment = new HarvestListView();
				FragmentManager frgManager = getFragmentManager();
				frgManager.beginTransaction()
						.replace(R.id.content_frame, newFragment).commit();
			}
		});

		heDeleteHarvest = (Button) view.findViewById(R.id.heDeleteHarvest);
		heDeleteHarvest.setOnClickListener(new View.OnClickListener() {
			Activity activity = new Activity();

			@Override
			public void onClick(View view) {

				activity.setCrop(cropname);
				activity.setId(id);

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						getActivity());
				alertDialogBuilder.setMessage(R.string.decision1);
				alertDialogBuilder.setPositiveButton(R.string.positive_button1,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								new FarmDBManager().deleteActivity(
										getActivity(), activity);
								Fragment newFragment = new HarvestListView();
								FragmentManager frgManager = getFragmentManager();
								frgManager
										.beginTransaction()
										.replace(R.id.content_frame,
												newFragment).commit();
							}
						});
				alertDialogBuilder.setNegativeButton(R.string.negative_button1,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Fragment newFragment = new ShowEditHarvest();
								FragmentManager frgManager = getFragmentManager();
								frgManager
										.beginTransaction()
										.replace(R.id.content_frame,
												newFragment).commit();
							}
						});

				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();

			}
		});
		return view;
	}

	private void showDatePicker() {
		DatePickerFragment date = new DatePickerFragment();

		Calendar calender = Calendar.getInstance();
		Bundle args = new Bundle();
		args.putInt("year", calender.get(Calendar.YEAR));
		args.putInt("month", calender.get(Calendar.MONTH));
		args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
		date.setArguments(args);

		date.setCallBack(ondate);
		date.show(getFragmentManager(), "Date Picker");
	}

	OnDateSetListener ondate = new OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {

			hehtdate.setText(String.valueOf(dayOfMonth) + "-"
					+ String.valueOf(monthOfYear + 1) + "-"
					+ String.valueOf(year));
		}

	};

}
