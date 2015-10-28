package smartfarms.fragments;

import java.util.Calendar;
import java.util.List;

import smartfarms.utils.Activity;
import smartfarms.utils.FarmDBManager;
import smartfarms.utils.Land;
import layout.layout.R;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class ShowEditPlant extends Fragment {
	EditText pepdate, peamt_plant, peno_labourers_plant;
	Button peSavePlant, peDeletePlant;
	String cropname, landname, type, category, date1;
	float income;
	int quantity, id;

	private int year;
	private int month;
	private int day;
	static final int DATE_PICKER_ID = 1111;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.show_or_edit_plant, container,
				false);

		cropname = getArguments().getString("message");

		pepdate = (EditText) view.findViewById(R.id.pepdate);

		pepdate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {

				showDatePicker();
			}
		});

		peamt_plant = (EditText) view.findViewById(R.id.peamt_plant);
		peno_labourers_plant = (EditText) view
				.findViewById(R.id.peno_labourers_plant);

		List<Activity> details = new FarmDBManager()
				.getActivityDetails(getActivity().getApplicationContext());

		for (Activity activity : details) {

			if (cropname.equals(activity.getCrop())
					&& (activity.getType()).equals("Plantation")) {

				pepdate.setText(activity.getDate());
				peamt_plant.setText(Float.toString(activity.getExpense()));
				peno_labourers_plant.setText(Integer.toString(activity
						.getLabourers()));
				income = activity.getIncome();
				landname = activity.getLand();
				type = activity.getType();
				category = activity.getCategory();
				quantity = activity.getQuantity();
				id = activity.getId();
				break;
			}

		}

		peSavePlant = (Button) view.findViewById(R.id.peSavePlant);
		peSavePlant.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Activity activity = new Activity();
				activity.setDate(pepdate.getText().toString());
				activity.setExpense(Float.parseFloat(peamt_plant.getText()
						.toString()));
				activity.setLabourers(Integer.parseInt(peno_labourers_plant
						.getText().toString()));
				activity.setCrop(cropname);
				activity.setCategory(category);
				activity.setIncome(income);
				activity.setLand(landname);
				activity.setType(type);
				activity.setQuantity(quantity);

				new FarmDBManager().editActivity(getActivity(), activity);

				pepdate.setText("");
				peamt_plant.setText("");
				peno_labourers_plant.setText("");

				Fragment newFragment = new PlantListView();
				FragmentManager frgManager = getFragmentManager();
				frgManager.beginTransaction()
						.replace(R.id.content_frame, newFragment).commit();
			}
		});

		peDeletePlant = (Button) view.findViewById(R.id.peDeletePlant);
		peDeletePlant.setOnClickListener(new View.OnClickListener() {
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
								Fragment newFragment = new PlantListView();
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
								Fragment newFragment = new ShowEditPlant();
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

			pepdate.setText(String.valueOf(dayOfMonth) + "-"
					+ String.valueOf(monthOfYear + 1) + "-"
					+ String.valueOf(year));
		}

	};

}
