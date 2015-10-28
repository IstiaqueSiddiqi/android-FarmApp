package smartfarms.fragments;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import layout.layout.R;
import smartfarms.utils.Activity;
import smartfarms.utils.Crop;
import smartfarms.utils.FarmDBManager;
import smartfarms.validation1.Validation;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddOtherDetail extends Fragment {
	EditText odate, others, exp;
	Spinner category;
	String categoryName, imgpath, lName, cropname1;
	Button bt_saveOthers, bt_viewOthers;
	TextView txtvw;
	ImageView imgvw;
	Fragment f1;
	private int year;
	private int month;

	private int day;
	static final int DATE_PICKER_ID = 1111;

	final int THUMBSIZE = 150;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.addothersdetail, container, false);

		category = (Spinner) view.findViewById(R.id.spinner_category_others);
		others = (EditText) view.findViewById(R.id.otherdetails);
		imgvw = (ImageView) view.findViewById(R.id.addImageOthers);
		txtvw = (TextView) view.findViewById(R.id.OthersCropName);
		others.setEnabled(true);
		others.setInputType(InputType.TYPE_NULL);
		others.setFocusable(true);
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

		category.setAdapter(dataAdapter);

		OnItemSelectedListener selectedItemListener = new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {

				categoryName = parent.getItemAtPosition(pos).toString();
				if (categoryName.equals("Others")) {
					Toast.makeText(getActivity(), categoryName,
							Toast.LENGTH_LONG).show();
					others.setFocusable(true);
					others.setInputType(InputType.TYPE_CLASS_TEXT);
					others.setEnabled(true);
				} else {
					others.setEnabled(false);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		};

		category.setOnItemSelectedListener(selectedItemListener);

		final String cropname = getArguments().getString("message");

		odate = (EditText) view.findViewById(R.id.otdate);
		others = (EditText) view.findViewById(R.id.otherdetails);
		exp = (EditText) view.findViewById(R.id.amount_others);

		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

		odate.setText(new StringBuilder().append(day).append("-").append(month + 1).append("-").append(year)
				.append(" "));
		odate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {

				showDatePicker();
			}
		});

		List<Crop> details = new FarmDBManager().getCropDetails(getActivity());
		for (Crop crop : details) {
			Log.d("msg", crop.getLname() + crop.getName());
			if (crop.getName().equals(cropname)) {
				lName = crop.getLname();
				imgpath = crop.getImage();
				cropname1 = crop.getName();
				Log.d("name", lName);
				break;
			}
		}

		Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(
				BitmapFactory.decodeFile(imgpath), THUMBSIZE, THUMBSIZE);
		imgvw.setImageBitmap(ThumbImage);
		txtvw.setText(cropname1);

		bt_saveOthers = (Button) view.findViewById(R.id.btnSaveOthers);
		bt_saveOthers.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				Validation v1 = new Validation();
				if (!(v1.isEmpty(exp.getText().toString()))
						&& (v1.number(exp.getText().toString()))) {

					Activity activity = new Activity();
					activity.setDate(odate.getText().toString());
					activity.setLabourers(0);
					activity.setExpense(Float.parseFloat(exp.getText()
							.toString()));
					activity.setType("others");
					activity.setCrop(cropname);
					activity.setLand(lName);
					activity.setQuantity(0);
					activity.setIncome(0);
					if (categoryName.equals("Others"))
						activity.setCategory(others.getText().toString());
					else
						activity.setCategory(categoryName);
					new FarmDBManager().addActivity(getActivity(), activity);
					Fragment newFragment = new OthersListView();
					FragmentManager frgManager = getFragmentManager();
					frgManager.beginTransaction()
							.replace(R.id.content_frame, newFragment).commit();
					getActivity().overridePendingTransition(
							R.drawable.slide_in, R.drawable.slide_out);

				} else
					Toast.makeText(getActivity(), "Not a valid input",
							Toast.LENGTH_LONG).show();
			}
		});

		bt_viewOthers = (Button) view.findViewById(R.id.btnViewOthers);
		bt_viewOthers.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Fragment newFragment = new OthersListView();
				FragmentManager frgManager = getFragmentManager();
				frgManager.beginTransaction()
						.replace(R.id.content_frame, newFragment).commit();
				getActivity().overridePendingTransition(R.drawable.slide_in,
						R.drawable.slide_out);
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

			odate.setText(String.valueOf(dayOfMonth) + "-"
					+ String.valueOf(monthOfYear + 1) + "-"
					+ String.valueOf(year));
		}
	};
}
