package smartfarms.activities;

import android.os.Bundle;
import android.util.Log;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import layout.layout.R;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SharedPreferences prefs = getSharedPreferences("MYPREF", 0);
		final String temp = prefs.getString("hello", "false");
		hideActionBar();

		Thread background = new Thread() {
			public void run() {
				try {
					sleep(3 * 1000);
					if (temp.equals("true")) {
						Log.d("flag+ ", temp);
						Intent intent = new Intent(MainActivity.this,
								HomeMenu.class);
						startActivity(intent);
						overridePendingTransition(R.drawable.slide_in,
								R.drawable.slide_out);
					} else {
						Intent i = new Intent(getBaseContext(),
								FirstScreen.class);
						startActivity(i);
						finish();
						overridePendingTransition(R.drawable.fade_in,
								R.drawable.fade_out);
					}
				} catch (Exception e) {
				}
			}
		};
		background.start();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@SuppressLint("NewApi")
	public void hideActionBar() {
		ActionBar ab = getActionBar();
		ab.hide();
	}
}
