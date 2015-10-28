package smartfarms.fragments;

import java.util.ArrayList;
import java.util.List;

import layout.layout.R;
import smartfarms.adapters.CustomAdap;
import smartfarms.utils.FarmDBManager;
import smartfarms.utils.Land;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Landhm extends Fragment {
	ListView listview2;
	Button badd;
	int flag = 0;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.landhm, container, false);
        
		listview2 = (ListView) view.findViewById(R.id.listview1_land);
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<String>location= new ArrayList<String>();
		ArrayList<String>area= new ArrayList<String>();
		 List<Land> details = new FarmDBManager().getLandDetails(getActivity().getApplicationContext());
		 for(Land land:details) {
			 list.add(land.getName());
			 location.add(land.getLocation());
			 area.add(Integer.toString(land.getArea()));
		}
		 
		 int size = list.size();
		 String [] values = new String[size];
		 String [] values1 = new String[size];
		 String [] values2 = new String[size];
		 for(int i = 0; i < size; i++) {
			 values[i] = list.get(i);
			 values1[i]=location.get(i);
			 values2[i]=area.get(i);
		 }
		
		 CustomAdap adapter1 = new CustomAdap(getActivity(), values,values1,values2);
		listview2.setAdapter(adapter1);
		OnItemClickListener itemClickListener = new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View container, int position, long id) {
                RelativeLayout linearLayoutParent = (RelativeLayout) container;
                TextView tvCountry = (TextView) linearLayoutParent.getChildAt(1);
                Bundle bundle=new Bundle();
                bundle.putString("message", tvCountry.getText().toString());
                Fragment fragment=new ShowEditLandFrag();
                fragment.setArguments(bundle);
                FragmentManager frgManager = getFragmentManager();
		        frgManager.beginTransaction().replace(R.id.content_frame, fragment)
		                    .commit();
		        getActivity().overridePendingTransition(R.drawable.slide_in, R.drawable.slide_out);
				
                
            }
        };
 
        // Setting the item click listener for the listview
        listview2.setOnItemClickListener(itemClickListener);
		
        
        badd=(Button)view.findViewById(R.id.land_add1);
        badd.setOnClickListener(new View.OnClickListener() {
   		@Override
         public void onClick(View view) {
   		
   			Fragment fragment=new LandFrag();
			FragmentManager frgManager = getFragmentManager();
	        frgManager.beginTransaction().replace(R.id.content_frame, fragment)
	                    .commit();
	        getActivity().overridePendingTransition(R.drawable.slide_in, R.drawable.slide_out);
   		 }
       });
		return view;
	}



}
