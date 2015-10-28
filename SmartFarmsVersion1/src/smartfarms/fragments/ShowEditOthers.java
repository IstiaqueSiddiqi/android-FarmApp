package smartfarms.fragments;

import java.util.ArrayList;
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
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class ShowEditOthers extends Fragment {
	Spinner SpinnereditOthers;
	EditText Other, OtherDate, OtherExpenses;
	int quant, lab;
	String category, landname, type;
	Float income;
	Button oeSaveOthers, oeDeleteOthers;
	int id;
	String categoryName;

	private int year;
	private int month;
	private int day;
	static final int DATE_PICKER_ID = 1111;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.show_or_edit_others, container,
				false);
		final String cropname = getArguments().getString("message");
		SpinnereditOthers = (Spinner) view.findViewById(R.id.SpinnereditOthers);

		Other = (EditText) view.findViewById(R.id.Other);
		Other.setEnabled(true);
		Other.setInputType(InputType.TYPE_NULL);
		Other.setFocusable(true);
		List<String> list = new ArrayList<String>();
		list.add("Manure");
		list.add("Fertilizer");
		list.add("Pesticides");
		list.add("Seeds");
		list.add("Others");

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				this.getActivity(), android.R.layout.simple_spinner_item, list);

		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		SpinnereditOthers.setAdapter(dataAdapter);

		OnItemSelectedListener selectedItemListener = new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {

				categoryName = parent.getItemAtPosition(pos).toString();
				if (categoryName.equals("Others")) {
					Toast.makeText(getActivity(), categoryName,
							Toast.LENGTH_LONG).show();
					Other.setFocusable(true);
					Other.setInputType(InputType.TYPE_CLASS_TEXT);
					Other.setEnabled(true);
				} else {
					Other.setEnabled(false);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		};

		SpinnereditOthers.setOnItemSelectedListener(selectedItemListener);

		OtherDate = (EditText) view.findViewById(R.id.OtherDate);
		OtherExpenses = (EditText) view.findViewById(R.id.OtherExpenses);

		OtherDate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {

				showDatePicker();
			}
		});

		List<Activity> details = new FarmDBManager()
				.getActivityDetails(getActivity().getApplicationContext());

		for (Activity activity : details) {

			if (cropname.equals(activity.getCrop())
					&& (activity.getType()).equals("others")) {

				OtherDate.setText(activity.getDate());
				Other.setText(activity.getCategory());
				OtherExpenses.setText(Float.toString(activity.getExpense()));
				quant = activity.getQuantity();
				lab = activity.getLabourers();
				income = activity.getIncome();
				landname = activity.getLand();
				category = activity.getCategory();
				type = activity.getType();
				id = activity.getId();
				break;
			}
		}

		oeSaveOthers = (Button) view.findViewById(R.id.oeSaveOthers);
		oeSaveOthers.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Activity activity = new Activity();

				activity.setDate(OtherDate.getText().toString());
				activity.setExpense(Float.parseFloat(OtherExpenses.getText()
						.toString()));
				activity.setLabourers(lab);
				activity.setCrop(cropname);
				activity.setCategory(category);
				activity.setIncome(income);
				activity.setLand(landname);
				activity.setType(type);
				activity.setQuantity(quant);

				new FarmDBManager().editActivity(getActivity(), activity);

				OtherDate.setText("");
				Other.setText("");
				OtherExpenses.setText("");

				Fragment newFragment = new OthersListView();
				FragmentManager frgManager = getFragmentManager();
				frgManager.beginTransaction()
						.replace(R.id.content_frame, newFragment).commit();
			}
		});

		oeDeleteOthers = (Button) view.findViewById(R.id.oeDeleteOthers);
		oeDeleteOthers.setOnClickListener(new View.OnClickListener() {
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
								Fragment newFragment = new OthersListView();
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
								Fragment newFragment = new ShowEditOthers();
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

			OtherDate.setText(String.valueOf(dayOfMonth) + "-"
					+ String.valueOf(monthOfYear + 1) + "-"
					+ String.valueOf(year));
		}
	};
}
