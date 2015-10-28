package smartfarms.fragments;

import java.util.Calendar;
import java.util.List;

import smartfarms.utils.Activity;
import smartfarms.utils.Crop;
import smartfarms.utils.FarmDBManager;
import smartfarms.validation1.Validation;

import layout.layout.R;

import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.DatePickerDialog.OnDateSetListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class AddHarvestDetail extends Fragment {
	Button bt_save, bt_vh;
	EditText hdate, quant, hincome, Noflab;
	Fragment f1;
	ImageView imgvw;
	TextView txtvw;
	String imgpath, lName;
	private int year;
	private int month;
	private int day;
	static final int DATE_PICKER_ID = 1111;

	final int THUMBSIZE = 150;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.addharvestdetail, container,
				false);
		final String cropname = getArguments().getString("message");
		hdate = (EditText) view.findViewById(R.id.htdate);
		quant = (EditText) view.findViewById(R.id.quantity);
		hincome = (EditText) view.findViewById(R.id.IncomeHarvest);
		Noflab = (EditText) view.findViewById(R.id.nole);
		imgvw = (ImageView) view.findViewById(R.id.addImageHarvest);
		txtvw = (TextView) view.findViewById(R.id.HarvestCropName);
		txtvw.setText(cropname);
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

		hdate.setText(new StringBuilder().append(day).append("-").append(month + 1).append("-").append(year)
				.append(" "));
		hdate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {

				showDatePicker();
			}
		});

		List<Crop> details = new FarmDBManager().getCropDetails(getActivity());
		for (Crop crop : details) {
			Log.d("msg", crop.getLname() + crop.getName());
			if (crop.getName().equals(cropname)) {
				imgpath = crop.getImage();
				lName = crop.getLname();
				Log.d("name", lName);
				break;
			}
		}

		Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(
				BitmapFactory.decodeFile(imgpath), THUMBSIZE, THUMBSIZE);
		imgvw.setImageBitmap(ThumbImage);

		bt_save = (Button) view.findViewById(R.id.btnSaveHarvest);
		bt_save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				Validation v1 = new Validation();
				if (!(v1.isEmpty(quant.getText().toString())
						&& v1.isEmpty(hincome.getText().toString()) && v1
							.isEmpty(Noflab.getText().toString()))
						&& (v1.number(Noflab.getText().toString())
								&& v1.number(quant.getText().toString()) && v1
									.number(hincome.getText().toString()))) {

					Activity activity = new Activity();
					activity.setDate(hdate.getText().toString());
					activity.setLabourers(Integer.parseInt(Noflab.getText()
							.toString()));
					activity.setExpense(Float.parseFloat(hincome.getText()
							.toString()));
					activity.setType("Harvest");
					activity.setCrop(cropname);
					activity.setLand(lName);
					activity.setQuantity(Integer.parseInt(quant.getText()
							.toString()));
					activity.setIncome(0);
					activity.setCategory("");
					new FarmDBManager().addActivity(getActivity(), activity);
					Fragment newFragment = new HarvestListView();
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

		bt_vh = (Button) view.findViewById(R.id.btnViewHarvest);
		bt_vh.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Fragment newFragment = new HarvestListView();
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

			hdate.setText(String.valueOf(dayOfMonth) + "-"
					+ String.valueOf(monthOfYear + 1) + "-"
					+ String.valueOf(year));
		}

	};

}
