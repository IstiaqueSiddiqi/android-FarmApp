package smartfarms.fragments;

import layout.layout.R;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HomeMenuFrag extends Fragment {
	LinearLayout land1, crp, actv1, rpt;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.homemenufrag, container, false);

		land1 = (LinearLayout) view.findViewById(R.id.fland);
		land1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				Fragment newFragment = new AddLand();
				FragmentManager frgManager = getFragmentManager();
				frgManager.beginTransaction()
						.replace(R.id.content_frame, newFragment).commit();
			}
		});

		actv1 = (LinearLayout) view.findViewById(R.id.factivity);
		actv1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Fragment newFragment = new ActivityHome();
				FragmentManager frgManager = getFragmentManager();
				frgManager.beginTransaction()
						.replace(R.id.content_frame, newFragment).commit();

			}
		});

		crp = (LinearLayout) view.findViewById(R.id.crop1);
		crp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				Fragment newFragment = new CropHome();
				FragmentManager frgManager = getFragmentManager();
				frgManager.beginTransaction()
						.replace(R.id.content_frame, newFragment).commit();
				getActivity().overridePendingTransition(R.drawable.slide_in,
						R.drawable.slide_out);
			}
		});

		rpt = (LinearLayout) view.findViewById(R.id.reports1);
		rpt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Fragment newFragment = new ReportSalesHome();
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
