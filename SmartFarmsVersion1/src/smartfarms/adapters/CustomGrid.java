package smartfarms.adapters;

import layout.layout.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomGrid extends BaseAdapter {
	private Context mContext;
	private final String[] web;
	private final String[] Imageid;

	public CustomGrid(Context c, String[] web, String[] Imageid) {
		mContext = c;
		this.Imageid = Imageid;
		this.web = web;
	}

	@Override
	public int getCount() {
		return web.length;
	}

	@Override
	public Object getItem(int position) {

		return null;
	}

	@Override
	public long getItemId(int position) {

		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View grid;
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			grid = new View(mContext);
			grid = inflater.inflate(R.layout.grid_items, null);
			TextView textView = (TextView) grid.findViewById(R.id.grid_text);
			ImageView imageView = (ImageView) grid
					.findViewById(R.id.grid_image);
			textView.setText(web[position]);
			Bitmap photo = new BitmapFactory().decodeFile(Imageid[position]);
			imageView.setImageBitmap(photo);
		} else {
			grid = (View) convertView;
		}
		return grid;
	}
}