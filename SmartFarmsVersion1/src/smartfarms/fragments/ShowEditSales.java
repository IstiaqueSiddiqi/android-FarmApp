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

public class ShowEditSales extends Fragment {

	EditText sesdate, SalesQuantity, SalesIncome, SalesExpenses;
	Button SaveSales, DeleteSales;
	String type, category, landname;
	int labours, id;

	private int year;
	private int month;
	private int day;
	static final int DATE_PICKER_ID = 1111;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.show_or_edit_sales, container,
				false);
		final String cropname = getArguments().getString("message");

		sesdate = (EditText) view.findViewById(R.id.sesdate);

		sesdate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {

				showDatePicker();
			}
		});

		SalesQuantity = (EditText) view.findViewById(R.id.SalesQuantity);
		SalesIncome = (EditText) view.findViewById(R.id.SalesIncome);
		SalesExpenses = (EditText) view.findViewById(R.id.SalesExpenses);

		List<Activity> details = new FarmDBManager()
				.getActivityDetails(getActivity().getApplicationContext());

		for (Activity activity : details) {

			if (cropname.equals(activity.getCrop())
					&& (activity.getType()).equals("Sales")) {

				sesdate.setText(activity.getDate());
				SalesExpenses.setText(Float.toString(activity.getExpense()));
				SalesIncome.setText(Float.toString(activity.getIncome()));
				SalesQuantity.setText(Integer.toString(activity.getQuantity()));

				labours = activity.getLabourers();
				landname = activity.getLand();
				type = activity.getType();
				category = activity.getCategory();
				id = activity.getId();
				break;
			}

		}

		SaveSales = (Button) view.findViewById(R.id.SaveSales);
		SaveSales.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Activity activity = new Activity();
				activity.setDate(sesdate.getText().toString());
				activity.setExpense(Float.parseFloat(SalesExpenses.getText()
						.toString()));
				activity.setLabourers(labours);
				activity.setCrop(cropname);
				activity.setCategory(category);
				activity.setIncome(Float.parseFloat(SalesIncome.getText()
						.toString()));
				activity.setLand(landname);
				activity.setType(type);
				activity.setQuantity(Integer.parseInt(SalesQuantity.getText()
						.toString()));

				new FarmDBManager().editActivity(getActivity(), activity);
				sesdate.setText("");
				SalesExpenses.setText("");
				SalesIncome.setText("");
				SalesQuantity.setText("");

				Fragment newFragment = new SalesListView();
				FragmentManager frgManager = getFragmentManager();
				frgManager.beginTransaction()
						.replace(R.id.content_frame, newFragment).commit();
			}
		});

		DeleteSales = (Button) view.findViewById(R.id.DeleteSales);
		DeleteSales.setOnClickListener(new View.OnClickListener() {
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
								Fragment newFragment = new SalesListView();
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
								Fragment newFragment = new ShowEditSales();
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

			sesdate.setText(String.valueOf(dayOfMonth) + "-"
					+ String.valueOf(monthOfYear + 1) + "-"
					+ String.valueOf(year));
		}

	};

}
