package smartfarms.adapters;

import layout.layout.R;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ActivityAdapter extends ArrayAdapter<String> {

	private final Activity context;
	private final String[] web;
	private final String[] date1;
	private final String[] exp;

	public ActivityAdapter(Activity context, String[] web, String[] date1,
			String[] exp) {
		super(context, R.layout.listdata, web);
		this.context = context;
		this.web = web;
		this.date1 = date1;
		this.exp = exp;
	}

	@Override
	public View getView(final int position, View view, ViewGroup parent) {

		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.listdata, null, true);
		TextView txtTitle = (TextView) rowView.findViewById(R.id.plantActivity);
		TextView txtdate = (TextView) rowView.findViewById(R.id.ActDate);
		TextView txtexp = (TextView) rowView.findViewById(R.id.ActExpense);
		txtTitle.setText(web[position]);
		txtdate.setText(date1[position]);
		txtexp.setText(exp[position]);
		return rowView;
	}

}