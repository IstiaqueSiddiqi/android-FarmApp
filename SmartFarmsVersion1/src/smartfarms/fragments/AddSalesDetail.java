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

public class AddSalesDetail extends Fragment {
	Button btnSaveSales, btnViewSales;
	EditText sdate, quant, sincome, SalesExpence;
	String imgpath, lName;
	ImageView imgvw;
	TextView txtvw;
	Fragment f1;
	private int year;
	private int month;
	private int day;

	final int THUMBSIZE = 150;
	static final int DATE_PICKER_ID = 1111;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.addsalesdetail, container, false);
		final String cropname = getArguments().getString("message");
		sdate = (EditText) view.findViewById(R.id.sdate);
		quant = (EditText) view.findViewById(R.id.SalesQuant);
		sincome = (EditText) view.findViewById(R.id.SalesIncome);
		SalesExpence = (EditText) view.findViewById(R.id.SalesExpence);
		imgvw = (ImageView) view.findViewById(R.id.addImageSales);
		txtvw = (TextView) view.findViewById(R.id.SalesCropName);
		txtvw.setText(cropname);

		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

		sdate.setText(new StringBuilder()

		.append(day).append("-").append(month + 1).append("-").append(year)
				.append(" "));
		sdate.setOnClickListener(new View.OnClickListener() {

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
				Log.d("name", lName);
				break;
			}
		}

		Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(
				BitmapFactory.decodeFile(imgpath), THUMBSIZE, THUMBSIZE);
		imgvw.setImageBitmap(ThumbImage);

		btnSaveSales = (Button) view.findViewById(R.id.btnSaveSales);
		btnSaveSales.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				Validation v1 = new Validation();
				if (!(v1.isEmpty(sincome.getText().toString())
						&& v1.isEmpty(quant.getText().toString()) && v1
							.isEmpty(SalesExpence.getText().toString()))
						&& (v1.number(quant.getText().toString())
								&& v1.number(sincome.getText().toString()) && v1
									.number(SalesExpence.getText().toString()))) {

					Activity activity = new Activity();
					activity.setDate(sdate.getText().toString());
					activity.setLabourers(0);
					activity.setExpense(Float.parseFloat(SalesExpence.getText()
							.toString()));
					activity.setType("Sales");
					activity.setCrop(cropname);
					activity.setLand(lName);
					activity.setQuantity(Integer.parseInt(quant.getText()
							.toString()));
					activity.setIncome(Float.parseFloat(sincome.getText()
							.toString()));

					new FarmDBManager().addActivity(getActivity(), activity);

					Fragment newFragment = new SalesListView();
					FragmentManager frgManager = getFragmentManager();
					frgManager.beginTransaction()
							.replace(R.id.content_frame, newFragment).commit();
					getActivity().overridePendingTransition(
							R.drawable.slide_in, R.drawable.slide_out);

				}

				else
					Toast.makeText(getActivity(), "Not a valid input",
							Toast.LENGTH_LONG).show();
			}
		});

		btnViewSales = (Button) view.findViewById(R.id.btnViewSales);
		btnViewSales.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				Fragment newFragment = new SalesListView();
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
		/**
		 * Set Up Current Date Into dialog
		 */
		Calendar calender = Calendar.getInstance();
		Bundle args = new Bundle();
		args.putInt("year", calender.get(Calendar.YEAR));
		args.putInt("month", calender.get(Calendar.MONTH));
		args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
		date.setArguments(args);
		/**
		 * Set Call back to capture selected date
		 */
		date.setCallBack(ondate);
		date.show(getFragmentManager(), "Date Picker");
	}

	OnDateSetListener ondate = new OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {

			sdate.setText(String.valueOf(dayOfMonth) + "-"
					+ String.valueOf(monthOfYear + 1) + "-"
					+ String.valueOf(year));
		}

	};

}