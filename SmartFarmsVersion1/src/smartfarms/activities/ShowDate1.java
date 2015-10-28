package smartfarms.activities;

import layout.layout.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ShowDate1 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_date1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
