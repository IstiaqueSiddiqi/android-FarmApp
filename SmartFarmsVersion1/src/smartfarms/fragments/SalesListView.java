package smartfarms.fragments;

import java.util.ArrayList;
import java.util.List;

import layout.layout.R;
import smartfarms.adapters.ActivityAdapter;
import smartfarms.utils.Activity;
import smartfarms.utils.FarmDBManager;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SalesListView extends Fragment {
	Button sadd;
	ListView listView;
	String[] values;
	String[] date1;
	String[] expenses;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_content_sales,
				container, false);
		listView = (ListView) view.findViewById(R.id.listviewSales);

		List<Activity> details = new FarmDBManager()
				.getActivityDetails(getActivity());
		ArrayList<String> datelist = new ArrayList<String>();
		ArrayList<String> expenseslist = new ArrayList<String>();

		ArrayList<String> list = new ArrayList<String>();

		for (Activity activity : details) {
			if (activity.getType().equals("Sales")) {
				list.add(activity.getCrop());
				datelist.add(activity.getDate());
				expenseslist.add(Double.toString(activity.getExpense()));
			}
		}

		int size = list.size();
		values = new String[size];
		date1 = new String[size];
		expenses = new String[size];
		for (int i = 0; i < size; i++) {

			values[i] = list.get(i);
			date1[i] = datelist.get(i);
			expenses[i] = expenseslist.get(i);

		}
		ActivityAdapter adapter = new ActivityAdapter(getActivity(), values,
				date1, expenses);
		listView.setAdapter(adapter);

		OnItemClickListener itemClickListener = new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View container,
					int position, long id) {
				RelativeLayout linearLayoutParent = (RelativeLayout) container;
				TextView tvCountry = (TextView) linearLayoutParent
						.getChildAt(4);
				Bundle bundle = new Bundle();
				bundle.putString("message", tvCountry.getText().toString());

				Fragment fragment = new ShowEditSales();
				fragment.setArguments(bundle);
				FragmentManager frgManager = getFragmentManager();
				frgManager.beginTransaction()
						.replace(R.id.content_frame, fragment).commit();
				getActivity().overridePendingTransition(R.drawable.slide_in,
						R.drawable.slide_out);

			}
		};

		listView.setOnItemClickListener(itemClickListener);

		sadd = (Button) view.findViewById(R.id.SalesAdd);
		sadd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				Fragment fragment = new CropGridViewSales();
				FragmentManager frgManager = getFragmentManager();
				frgManager.beginTransaction()
						.replace(R.id.content_frame, fragment).commit();
				getActivity().overridePendingTransition(R.drawable.slide_in,
						R.drawable.slide_out);

			}
		});
		return view;

	}
}