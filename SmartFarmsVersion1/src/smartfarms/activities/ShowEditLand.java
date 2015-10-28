package smartfarms.activities;

import smartfarms.fragments.ShowEditLandFrag;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class ShowEditLand extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(null);
		Intent intent = new Intent(ShowEditLand.this, ShowEditLandFrag.class);
		startActivity(intent);
	}
}
