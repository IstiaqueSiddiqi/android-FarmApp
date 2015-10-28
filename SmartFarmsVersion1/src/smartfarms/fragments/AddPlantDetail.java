package smartfarms.fragments;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AddPlantDetail extends Fragment {
	Button btnSavePlant, btnViewPlant;
	EditText pdate, exp, labours;
	ImageView imgvw;
	String imgpath, lName;
	TextView txtvw;
	Fragment f1;

	final int THUMBSIZE = 150;

	private int year;
	private int month;
	private int day;
	static final int DATE_PICKER_ID = 1111;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.addplantdetail, container, false);
		final String cropname = getArguments().getString("message");
		Log.d("cropgridplant", cropname);
		exp = (EditText) view.findViewById(R.id.expenses);
		labours = (EditText) view.findViewById(R.id.labours);
		pdate = (EditText) view.findViewById(R.id.plantdate);
		imgvw = (ImageView) view.findViewById(R.id.addImagePlant);
		txtvw = (TextView) view.findViewById(R.id.PlantCropName);
		txtvw.setText(cropname);

		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

		pdate = (EditText) view.findViewById(R.id.plantdate);
		pdate.setText(new StringBuilder().append(day).append("-").append(month + 1).append("-").append(year)
		.append(" "));

		pdate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {

				showDatePicker();
			}
		});

		List<Crop> details1 = new FarmDBManager().getCropDetails(getActivity());
		for (Crop crop : details1) {
			Log.d("msg", crop.getLname() + crop.getName());
			if (crop.getName().equals(cropname)) {
				lName = crop.getLname();
				imgpath = crop.getImage();
				break;
			}
		}

		Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(
				BitmapFactory.decodeFile(imgpath), THUMBSIZE, THUMBSIZE);
		imgvw.setImageBitmap(ThumbImage);

		btnSavePlant = (Button) view.findViewById(R.id.btnSavePlant);
		btnSavePlant.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				Validation v1 = new Validation();
				if (!(v1.isEmpty(exp.getText().toString()) && v1
						.isEmpty(labours.getText().toString()))
						&& (v1.number(exp.getText().toString()) && v1
								.number(labours.getText().toString()))) {

					Activity activity = new Activity();
					activity.setDate(pdate.getText().toString());
					activity.setLabourers(Integer.parseInt(labours.getText()
							.toString()));
					activity.setExpense(Float.parseFloat(exp.getText()
							.toString()));
					activity.setType("Plantation");
					activity.setCrop(cropname);
					activity.setLand(lName);
					activity.setQuantity(0);
					activity.setIncome(0);
					activity.setCategory("");

					new FarmDBManager().addActivity(getActivity(), activity);
					Fragment newFragment = new PlantListView();
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

		btnViewPlant = (Button) view.findViewById(R.id.btnViewPlant);
		btnViewPlant.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Fragment newFragment = new PlantListView();
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

			pdate.setText(String.valueOf(dayOfMonth) + "-"
					+ String.valueOf(monthOfYear + 1) + "-"
					+ String.valueOf(year));
		}

	};

}
