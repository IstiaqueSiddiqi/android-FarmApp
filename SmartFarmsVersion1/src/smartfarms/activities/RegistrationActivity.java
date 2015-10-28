package smartfarms.activities;

import smartfarms.validation1.Validation;
import layout.layout.R;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationActivity extends Activity {
	Button signup;
	EditText fname, lname, email, pwd, cpwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registerlayout);
		signup = (Button) findViewById(R.id.button1);
		fname = (EditText) findViewById(R.id.firstname);
		lname = (EditText) findViewById(R.id.lastname);
		email = (EditText) findViewById(R.id.email);
		pwd = (EditText) findViewById(R.id.password);
		cpwd = (EditText) findViewById(R.id.cpassword);

		SharedPreferences rprefs = getSharedPreferences("MYPREFR", 0);
		fname.setText(rprefs.getString("firstName", ""));
		lname.setText(rprefs.getString("lastName", ""));
		email.setText(rprefs.getString("email", ""));
		pwd.setText(rprefs.getString("Password", ""));
		cpwd.setText(rprefs.getString("CPassword", ""));

		signup.setOnClickListener(new View.OnClickListener() {
			@SuppressWarnings("static-access")
			@Override
			public void onClick(View view) {
				SharedPreferences rprefs = getSharedPreferences("MYPREFR", 0);
				SharedPreferences.Editor editor1 = rprefs.edit();
				editor1.putString("firstName", fname.getText().toString());
				editor1.putString("lastName", lname.getText().toString());
				editor1.putString("email", email.getText().toString());
				editor1.putString("Password", pwd.getText().toString());
				editor1.putString("CPassword", cpwd.getText().toString());
				editor1.commit();

				Validation valid1 = new Validation();
				if ((!(valid1.isEmpty(fname.getText().toString())
						|| valid1.isEmpty(lname.getText().toString())
						|| valid1.isEmpty(email.getText().toString())
						|| valid1.isEmpty(pwd.getText().toString()) || valid1
						.isEmpty(cpwd.getText().toString())))) {
					if ((valid1.validateFirstName(fname.getText().toString()) || valid1
							.validateFirstName(lname.getText().toString()))) {
						if ((valid1.isEmail(email.getText().toString()))) {
							if ((valid1.isPassword(pwd.getText().toString()) && valid1
									.isPassword(cpwd.getText().toString()))) {
								if ((pwd.getText().toString().equals(cpwd
										.getText().toString()))) {

									Intent intent = new Intent(
											RegistrationActivity.this,
											FirstScreen.class);
									startActivity(intent);
									overridePendingTransition(
											R.drawable.slide_in,
											R.drawable.slide_out);

								} else
									Toast.makeText(getBaseContext(),
											"passwords do not match",
											Toast.LENGTH_LONG).show();
							} else
								Toast.makeText(
										getBaseContext(),
										"password length cannot be less than 5",
										Toast.LENGTH_LONG).show();
						} else
							Toast.makeText(getBaseContext(),
									"Not a valid email id", Toast.LENGTH_LONG)
									.show();

					} else
						Toast.makeText(getBaseContext(), "Not a valid name",
								Toast.LENGTH_LONG).show();
				}

				else
					Toast.makeText(getBaseContext(), "No fields can be empty",
							Toast.LENGTH_LONG).show();

			}

		});
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(0, R.drawable.slideback_in);
	}

}