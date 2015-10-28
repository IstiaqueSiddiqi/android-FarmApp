package smartfarms.activities;

import layout.layout.R;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FirstScreen extends Activity {
	Button login;
	EditText uname, pwd;
	String flag = "false";
	String str = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first_screen);
		login = (Button) findViewById(R.id.button1);
		getActionBar().hide();

		uname = (EditText) findViewById(R.id.userName);
		pwd = (EditText) findViewById(R.id.pwd);

		SharedPreferences prefs = getSharedPreferences("MYPREF", 0);
		uname.setText(prefs.getString("uvalue", ""));
		pwd.setText(prefs.getString("pvalue", ""));
		str = prefs.getString("hello", flag);
		flag = str;
		Log.d("flag inside+ ", flag);
		login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				SharedPreferences prefs = getSharedPreferences("MYPREF", 0);
				SharedPreferences.Editor editor1 = prefs.edit();
				editor1.putString("uvalue", uname.getText().toString());
				editor1.putString("pvalue", pwd.getText().toString());
				editor1.putString("hello", "true");
				editor1.commit();

				SharedPreferences rprefs = getSharedPreferences("MYPREFR", 0);
				String uname1 = rprefs.getString("email", "false");
				String pwd1 = rprefs.getString("Password", "false");

				String p1 = pwd.getText().toString();
				String u1 = uname.getText().toString();

				if (p1.equals(pwd1) && u1.equals(uname1)) {

					Intent intent = new Intent(FirstScreen.this, HomeMenu.class);
					startActivity(intent);
					overridePendingTransition(R.drawable.slide_in,
							R.drawable.slide_out);
				} else {
					Toast.makeText(
							getBaseContext(),
							"You have entered either wrong username or password",
							Toast.LENGTH_LONG).show();
				}
			}
		});

		TextView txt1 = (TextView) findViewById(R.id.textView1);
		txt1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent intt = new Intent(getApplicationContext(),
						RegistrationActivity.class);
				startActivity(intt);
				overridePendingTransition(R.drawable.slide_in,
						R.drawable.slide_out);

			}
		});

	}

	@Override
	public void onBackPressed() {
		FirstScreen.this.finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
