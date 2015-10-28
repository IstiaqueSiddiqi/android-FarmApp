package smartfarms.adapters;

import layout.layout.R;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomAdap extends ArrayAdapter<String> {

	private final Activity context;
	private final String[] web;
	private final String[] location;
	private final String[] area;

	public CustomAdap(Activity context, String[] web, String[] location,
			String[] area) {
		super(context, R.layout.items, web);
		this.context = context;
		this.web = web;
		this.location = location;
		this.area = area;
	}

	@Override
	public View getView(final int position, View view, ViewGroup parent) {

		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.items, null, true);
		TextView txtTitle = (TextView) rowView.findViewById(R.id.landlistitem);
		TextView loc = (TextView) rowView.findViewById(R.id.landLocation);
		TextView are = (TextView) rowView.findViewById(R.id.landArea);
		txtTitle.setText(web[position]);
		loc.setText(location[position]);
		are.setText(area[position] + "sq mtrs.");

		return rowView;
	}

}