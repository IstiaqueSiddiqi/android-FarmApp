package smartfarms.fragments;

import layout.layout.R;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class OthersActHome extends Fragment {
	ImageView ladd, lview;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.actv_others, container, false);
		getActivity().getActionBar().setTitle("Others");
		ladd = (ImageView) view.findViewById(R.id.actaddothers);
		lview = (ImageView) view.findViewById(R.id.actviewothers);

		ladd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Fragment newFragment = new CropGridViewOthers();
				FragmentManager frgManager = getFragmentManager();
				frgManager.beginTransaction()
						.replace(R.id.content_frame, newFragment).commit();
				getActivity().overridePendingTransition(R.drawable.slide_in,
						R.drawable.slide_out);
			}
		});

		lview.setOnClickListener(new View.OnClickListener() {
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
}
