package smartfarms.fragments;

import layout.layout.R;
import smartfarms.utils.FarmDBManager;
import smartfarms.utils.Land;
import smartfarms.validation1.Validation;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class AddLand extends Fragment {
	Button btn, viewhist;
	EditText lname, area, location;
	CheckBox defaultLand;
	String result;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.land_add, container, false);
		getActivity().getActionBar().setTitle("Land");
		lname = (EditText) view.findViewById(R.id.lname);
		area = (EditText) view.findViewById(R.id.area);
		location = (EditText) view.findViewById(R.id.address);

		btn = (Button) view.findViewById(R.id.btnLandAdd);
		defaultLand = (CheckBox) view.findViewById(R.id.checkBox2);

		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				Validation v1 = new Validation();
				if (!(v1.isEmpty(lname.getText().toString())
						&& v1.isEmpty(area.getText().toString()) && v1
							.isEmpty(location.getText().toString()))
						&& (v1.number(area.getText().toString())
								&& v1.validateAlpha(lname.getText().toString()) && v1
									.validateAlpha(location.getText()
											.toString()))) {

					if (defaultLand.isChecked())
						result = "true";
					else
						result = "false";
					Land land = new Land();
					land.setName(lname.getText().toString());
					land.setArea(Integer.parseInt(area.getText().toString()));
					land.setLat(1.3);
					land.setLng(1.3);
					land.setStatus(result);
					land.setLocation(location.getText().toString());
					new FarmDBManager().addLand(getActivity(), land);
					lname.setText("");
					area.setText("");
					location.setText("");

					Toast.makeText(getActivity(),
							"Land details added successfully.",
							Toast.LENGTH_LONG).show();

					Fragment newFragment = new LandHome();
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

		viewhist = (Button) view.findViewById(R.id.addLandBtn);
		viewhist.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Fragment newFragment = new LandHome();
				FragmentManager frgManager = getFragmentManager();
				frgManager.beginTransaction()
						.replace(R.id.content_frame, newFragment).commit();
				getActivity().overridePendingTransition(R.drawable.slide_in,
						R.drawable.slide_out);

			}
		});
		return view;
	}

}
