package smartfarms.fragments;

import java.util.List;

import layout.layout.R;

import smartfarms.adapters.CustomGrid;
import smartfarms.utils.Crop;
import smartfarms.utils.FarmDBManager;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CropGridViewOthers extends Fragment {
	GridView grid;
	String[] web;
	String[] imageId;
	List<Crop> details;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.gridviewhome, container, false);

		details = new FarmDBManager().getCropDetails(getActivity());

		int index = 0;
		web = new String[details.size()];
		imageId = new String[details.size()];

		for (Crop crop : details) {
			Log.d("msg", crop.getImage());
			web[index] = crop.getName();
			imageId[index] = crop.getImage();
			index++;
		}

		CustomGrid adapter = new CustomGrid(getActivity(), web, imageId);
		grid = (GridView) view.findViewById(R.id.grid);
		grid.setAdapter(adapter);
		grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				LinearLayout linearLayoutParent = (LinearLayout) view;
				TextView tvCountry = (TextView) linearLayoutParent
						.getChildAt(1);
				Bundle bundle = new Bundle();
				bundle.putString("message", tvCountry.getText().toString());
				Fragment newFragment = new AddOtherDetail();
				FragmentManager frgManager = getFragmentManager();
				newFragment.setArguments(bundle);
				frgManager.beginTransaction()
						.replace(R.id.content_frame, newFragment).commit();
				getActivity().overridePendingTransition(R.drawable.slide_in,
						R.drawable.slide_out);

			}
		});

		return view;
	}
}
