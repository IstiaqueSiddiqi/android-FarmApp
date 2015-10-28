package smartfarms.fragments;

import java.util.List;

import layout.layout.R;
import smartfarms.adapters.CustomAdapCrop;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CropListView extends Fragment {
	ListView listview2;
	Button badd;
	String crpcate;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.croplistview, container, false);

		listview2 = (ListView) view.findViewById(R.id.listview1_crop);

		List<Crop> details = new FarmDBManager().getCropDetails(getActivity());

		String[] values = new String[details.size()];
		String[] img1 = new String[details.size()];
		int index = 0;
		for (Crop crop : details) {
			values[index] = crop.getName();
			img1[index] = crop.getImage();
			index++;
		}

		CustomAdapCrop adapter1 = new CustomAdapCrop(getActivity(), values,
				img1);
		listview2.setAdapter(adapter1);

		OnItemClickListener itemClickListener = new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View container,
					int position, long id) {

				RelativeLayout linearLayoutParent = (RelativeLayout) container;

				TextView tvCountry = (TextView) linearLayoutParent
						.getChildAt(1);
				List<Crop> details = new FarmDBManager()
						.getCropDetails(getActivity());

				String imgPath = "";
				for (Crop crop : details) {
					if (crop.getName().equals(tvCountry.getText().toString())) {
						imgPath = crop.getImage();
						crpcate = crop.getCropcategory();
						break;
					}
				}

				Bundle bundle = new Bundle();
				bundle.putString("message", tvCountry.getText().toString());
				bundle.putString("imgPath", imgPath);
				bundle.putString("cropcategory", crpcate);

				Fragment fragment = new ShowEditCrop();
				fragment.setArguments(bundle);
				FragmentManager frgManager = getFragmentManager();
				frgManager.beginTransaction()
						.replace(R.id.content_frame, fragment).commit();
				getActivity().overridePendingTransition(R.drawable.slide_in,
						R.drawable.slide_out);
			}
		};

		listview2.setOnItemClickListener(itemClickListener);

		badd = (Button) view.findViewById(R.id.CropAdd);
		badd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Toast.makeText(getActivity().getApplicationContext(),
						"Grocery Add page.", Toast.LENGTH_LONG).show();
				Fragment newFragment = new CropHome();
				FragmentManager frgManager = getFragmentManager();
				frgManager.beginTransaction()
						.replace(R.id.content_frame, newFragment).commit();

			}
		});
		return view;
	}
}
