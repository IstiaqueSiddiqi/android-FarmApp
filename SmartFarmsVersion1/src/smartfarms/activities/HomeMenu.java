package smartfarms.activities;

import java.util.ArrayList;
import java.util.List;

import layout.layout.R;
import smartfarms.adapters.CustomDrawerAdapter;
import smartfarms.fragments.CropHome;
import smartfarms.fragments.DrawerItem;
import smartfarms.fragments.HomeMenuFrag;
import smartfarms.fragments.ActivityHome;
import smartfarms.fragments.AddLand;
import smartfarms.fragments.ReportSalesHome;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

@SuppressLint("NewApi")
public class HomeMenu extends Activity {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	int f = 0;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	CustomDrawerAdapter adapter;

	List<DrawerItem> dataList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homemenu);

		dataList = new ArrayList<DrawerItem>();
		mTitle = mDrawerTitle = getTitle();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		dataList.add(new DrawerItem(" Home", R.drawable.ic_home));
		dataList.add(new DrawerItem(" Land", R.drawable.ic_land));
		dataList.add(new DrawerItem(" Crops", R.drawable.ic_grocery));
		dataList.add(new DrawerItem(" Activity", R.drawable.ic_activity));
		dataList.add(new DrawerItem(" Report", R.drawable.ic_report));
		dataList.add(new DrawerItem(" Logout", R.drawable.ic_logout));

		adapter = new CustomDrawerAdapter(this, R.layout.custom_drawer_item,
				dataList);

		mDrawerList.setAdapter(adapter);

		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu();

			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu();

			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);
		android.app.FragmentTransaction tx = getFragmentManager()
				.beginTransaction();
		tx.replace(R.id.content_frame, new HomeMenuFrag());
		tx.commit();
		overridePendingTransition(R.drawable.slide_in, R.drawable.slide_out);
	}

	public void SelectItem(int possition) {

		Fragment fragment = null;
		Bundle args = new Bundle();
		switch (possition) {

		case 0:
			fragment = new HomeMenuFrag();
			break;
		case 1:

			fragment = new AddLand();

			break;
		case 2:
			fragment = new CropHome();

			break;
		case 3:
			fragment = new ActivityHome();

			break;
		case 4:
			fragment = new ReportSalesHome();

			break;

		case 5:
			f = 1;
			SharedPreferences settings = getApplicationContext()
					.getSharedPreferences("MYPREF", 0);
			settings.edit().clear().commit();

			Intent i = new Intent(getBaseContext(), FirstScreen.class);
			startActivity(i);
			finish();
			overridePendingTransition(R.drawable.fade_in, R.drawable.fade_out);

			break;
		default:
			break;
		}
		if (f == 0) {
			fragment.setArguments(args);
			FragmentManager frgManager = getFragmentManager();
			frgManager.beginTransaction().replace(R.id.content_frame, fragment)
					.commit();
			overridePendingTransition(R.drawable.slide_in, R.drawable.slide_out);

			mDrawerList.setItemChecked(possition, true);
			setTitle(dataList.get(possition).getItemName());
			mDrawerLayout.closeDrawer(mDrawerList);
		}
	}

	@Override
	public void onBackPressed() {
		Intent startMain = new Intent(Intent.ACTION_MAIN);
		startMain.addCategory(Intent.CATEGORY_HOME);
		startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(startMain);

	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		mDrawerToggle.syncState();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		return false;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			SelectItem(position);

		}
	}
}
