package smartfarms.adapters;

import layout.layout.R;
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapCrop extends ArrayAdapter<String> {
	private final Activity context;
	private final String[] web;
	private final String[] crop1;

	public CustomAdapCrop(Activity context, String[] web, String[] crop) {
		super(context, R.layout.crophome, web);
		this.context = context;
		this.web = web;
		this.crop1 = crop;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.crophome, null, true);

		TextView txtTitle = (TextView) rowView.findViewById(R.id.cropname);
		txtTitle.setText(web[position]);
		ImageView img1 = (ImageView) rowView.findViewById(R.id.proimg);
		img1.setImageBitmap(new BitmapFactory().decodeFile(crop1[position]));

		return rowView;
	}
}
