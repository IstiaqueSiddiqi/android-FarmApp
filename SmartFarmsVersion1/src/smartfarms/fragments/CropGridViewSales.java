package smartfarms.fragments;

import java.util.List;

import smartfarms.adapters.CustomGrid;
import smartfarms.utils.Crop;
import smartfarms.utils.FarmDBManager;
import layout.layout.R;
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

public class CropGridViewSales extends Fragment {

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

			Log.d("name", crop.getName());
			Log.d("name", crop.getImage());
			web[index] = crop.getName();
			imageId[index] = crop.getImage();
			index++;
		}

		for(int i=0;i<web.length;i++)
		{
			for(int j=i+1;j<web.length;j++)
			{
				if(web[i].compareTo(web[j])>0)
				{
					String temp1=web[i];
					web[i]=web[j];
					web[j]=temp1;
					String temp2 = imageId[i];
					imageId[i]=imageId[j];
					imageId[j]=temp2;
				}
			}
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
				Fragment newFragment = new AddSalesDetail();
				newFragment.setArguments(bundle);
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
