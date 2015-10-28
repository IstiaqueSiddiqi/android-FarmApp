package smartfarms.fragments;

import java.util.List;

import layout.layout.R;
import smartfarms.fragments.LandHome;
import smartfarms.utils.FarmDBManager;
import smartfarms.utils.Land;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditLand extends Fragment {
	private Button btn;
	private EditText lname;
	private EditText area;
	private EditText lat;
	private EditText lng;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.land_edit, container, false);

		String landname = getActivity().getIntent().getExtras()
				.getString("land_name");
		Toast.makeText(getActivity().getApplicationContext(), landname,
				Toast.LENGTH_LONG).show();
		lname = (EditText) view.findViewById(R.id.lname);
		area = (EditText) view.findViewById(R.id.area);
		lat = (EditText) view.findViewById(R.id.address);
		lng = (EditText) view.findViewById(R.id.longitude);

		List<Land> details = new FarmDBManager().getLandDetails(getActivity()
				.getApplicationContext());
		for (Land land : details) {

			if (landname.equals(land.getName())) {

				lname.setText(land.getName());
				area.setText(Integer.toString(land.getArea()));
				lat.setText(Double.toString(land.getLat()));
				lng.setText(Double.toString(land.getLng()));
				break;
			}

		}

		btn = (Button) view.findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Land land = new Land();
				land.setName(lname.getText().toString());
				land.setArea(Integer.parseInt(area.getText().toString()));
				land.setLat(Double.parseDouble(lat.getText().toString()));
				land.setLng(Double.parseDouble(lng.getText().toString()));

				new FarmDBManager().editLand(getActivity()
						.getApplicationContext(), land);

				lname.setText("");
				area.setText("");
				lat.setText("");
				lng.setText("");

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
