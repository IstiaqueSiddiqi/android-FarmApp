package smartfarms.fragments;

import smartfarms.fragments.DatewiseReport;
import smartfarms.fragments.IncomeExpenseCropwise;
import smartfarms.fragments.IncomeExpenseDateWise;
import layout.layout.R;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ReportSalesHome extends Fragment {
	Button datebtn, cropbtn;
	TextView selectedTab1, selectedTab2;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.report_new_layout, container,
				false);

		datebtn = (Button) view.findViewById(R.id.datewise1);
		cropbtn = (Button) view.findViewById(R.id.cropwise1);

		selectedTab1 = (TextView) view.findViewById(R.id.selectedLined);
		selectedTab2 = (TextView) view.findViewById(R.id.selectedLinec);

		android.app.FragmentTransaction tx = getFragmentManager()
				.beginTransaction();
		tx.replace(R.id.showReport, new IncomeExpenseDateWise());
		tx.commit();
		getActivity().overridePendingTransition(R.drawable.slide_in,
				R.drawable.slide_out);

		FragmentManager fm = getFragmentManager();
		android.app.FragmentTransaction ft = fm.beginTransaction();

		Button.OnClickListener btnOnClickListener = new Button.OnClickListener() {

			@Override
			public void onClick(View v1) {

				Fragment newFragment;
				if (v1 == datebtn) {
					selectedTab1.setVisibility(View.VISIBLE);
					selectedTab2.setVisibility(View.INVISIBLE);
					newFragment = new IncomeExpenseDateWise();
				} else if (v1 == cropbtn) {
					selectedTab2.setVisibility(View.VISIBLE);
					selectedTab1.setVisibility(View.INVISIBLE);
					newFragment = new IncomeExpenseCropwise();
				} else {
					selectedTab1.setVisibility(View.VISIBLE);
					selectedTab2.setVisibility(View.INVISIBLE);
					newFragment = new IncomeExpenseDateWise();
				}

				FragmentTransaction transaction = getFragmentManager()
						.beginTransaction();
				transaction.replace(R.id.showReport, newFragment);
				transaction.addToBackStack(null);
				transaction
						.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
				transaction.commit();
			}
		};
		datebtn.setOnClickListener(btnOnClickListener);
		cropbtn.setOnClickListener(btnOnClickListener);
		return view;
	}
}
