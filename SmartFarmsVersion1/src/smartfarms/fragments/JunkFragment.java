package smartfarms.fragments;

import layout.layout.R;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class JunkFragment extends Fragment{
	ImageView mplant,mharvest,msales,mothers;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.d("oncreateview","yes");
		View view = inflater.inflate(R.layout.reporthome, container, false);
		getActivity().getActionBar().setTitle("Activity");
		Log.d("after","inflating");
   mplant = (ImageView) view.findViewById(R.id.rplant);
		 mharvest = (ImageView)view.findViewById(R.id.rharvest);
		 msales = (ImageView) view.findViewById(R.id.rsales);
		 mothers = (ImageView)view.findViewById(R.id.rothers);
		 
		 mplant.setOnClickListener(new View.OnClickListener() {
		   		@Override
		         public void onClick(View view) {
		   			Fragment newFragment = new PlantActHome();
    		        FragmentManager frgManager = getFragmentManager();
    		        frgManager.beginTransaction().replace(R.id.content_frame, newFragment).commit();
    		        getActivity().overridePendingTransition(R.drawable.slide_in, R.drawable.slide_out);
		         }
		       });
		 
		 mharvest.setOnClickListener(new View.OnClickListener() {
		   		@Override
		         public void onClick(View view) {
		   			Fragment newFragment = new HarvestActHome();
    		        FragmentManager frgManager = getFragmentManager();
    		        frgManager.beginTransaction().replace(R.id.content_frame, newFragment).commit();
    		        getActivity().overridePendingTransition(R.drawable.slide_in, R.drawable.slide_out);
		         }
		       });
		 
		 msales.setOnClickListener(new View.OnClickListener() {
		   		@Override
		         public void onClick(View view) {
		   			Fragment newFragment = new SalesActHome();
    		        FragmentManager frgManager = getFragmentManager();
    		        frgManager.beginTransaction().replace(R.id.content_frame, newFragment).commit();
    		        getActivity().overridePendingTransition(R.drawable.slide_in, R.drawable.slide_out);
		         }
		       });
		 
		 mothers.setOnClickListener(new View.OnClickListener() {
		   		@Override
		         public void onClick(View view) {
		   			Fragment newFragment = new OthersActHome();//AddOtherDetail();
    		        FragmentManager frgManager = getFragmentManager();
    		        frgManager.beginTransaction().replace(R.id.content_frame, newFragment).commit();
    		        getActivity().overridePendingTransition(R.drawable.slide_in, R.drawable.slide_out);
		         }
		       });
		 return view;
	
}
	
	
	public void onBackPressed() {
		getActivity().overridePendingTransition(0, R.drawable.slideback_in);
	}


}
