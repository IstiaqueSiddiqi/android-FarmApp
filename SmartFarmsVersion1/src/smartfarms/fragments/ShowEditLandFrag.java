package smartfarms.fragments;

import java.util.List;

import smartfarms.utils.FarmDBManager;
import smartfarms.utils.Land;
import layout.layout.R;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class ShowEditLandFrag extends Fragment {
	private EditText lname;
	private EditText area;
	private EditText location;
	private Button btn, delbtn;
	private CheckBox landChkBx;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.show_or_edit_land, container,
				false);
		String landname = getArguments().getString("message");
		Log.d("SHOWEDITLAND", getArguments().getString("message"));
		lname = (EditText) view.findViewById(R.id.lnameshowedit);
		area = (EditText) view.findViewById(R.id.areashowedit);
		location = (EditText) view.findViewById(R.id.addressshowedit);
		landChkBx = (CheckBox) view.findViewById(R.id.DefaultLandCheckBox);

		List<Land> details = new FarmDBManager().getLandDetails(getActivity());
		for (Land land : details) {

			if (landname.equals(land.getName())) {

				lname.setText(land.getName());
				area.setText(Integer.toString(land.getArea()));
				location.setText(land.getLocation());
				if (land.getStatus().equals("true"))
					landChkBx.setChecked(true);
				else
					landChkBx.setChecked(false);
				break;
			}
		}

		btn = (Button) view.findViewById(R.id.UpdateLandBtn);
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Land land = new Land();
				land.setName(lname.getText().toString());
				land.setArea(Integer.parseInt(area.getText().toString()));
				land.setLocation(location.getText().toString());

				if (landChkBx.isChecked()) {
					land.setStatus("true");
				} else {
					land.setStatus("false");
				}

				new FarmDBManager().editLand(getActivity(), land);

				Fragment newFragment = new LandHome();
				FragmentManager frgManager = getFragmentManager();
				frgManager.beginTransaction()
						.replace(R.id.content_frame, newFragment).commit();
				lname.setText("");
				area.setText("");
				location.setText("");

			}
		});

		delbtn = (Button) view.findViewById(R.id.button2);
		delbtn.setOnClickListener(new View.OnClickListener() {
			Land land = new Land();

			@Override
			public void onClick(View view) {

				land.setName(lname.getText().toString());

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						getActivity());
				alertDialogBuilder.setMessage(R.string.decision1);
				alertDialogBuilder.setPositiveButton(R.string.positive_button1,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								new FarmDBManager().deleteLand(getActivity(),
										land);
								Fragment newFragment = new LandHome();
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
								Fragment newFragment = new LandHome();
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
}
