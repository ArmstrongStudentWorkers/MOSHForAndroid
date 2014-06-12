package com.cmccarthy.mosh;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cmccarthy.moshobjects.Hardware;

public class AddHardwareActivity extends Activity {

	int workstationId = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_hardware);
		setupActionBar();
		Bundle extras = getIntent().getExtras();
		workstationId = extras.getInt("workstationId");

		Button saveButton = (Button) findViewById(R.id.saveButton);

		saveButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				EditText aasuNumber = (EditText) findViewById(R.id.asu_number_field);
				EditText assignedTo = (EditText) findViewById(R.id.assigned_to_field);
				EditText hardwareStatusComment = (EditText) findViewById(R.id.hardware_status_comment_field);
				EditText hardwareStatus = (EditText) findViewById(R.id.hardware_status_field);
				EditText hardwareType = (EditText) findViewById(R.id.hardware_type_field);
				EditText manufacturer = (EditText) findViewById(R.id.manufacturer_field);
				EditText modelNumber = (EditText) findViewById(R.id.model_number_field);
				EditText serialNumber = (EditText) findViewById(R.id.serial_number_field);
				EditText specs = (EditText) findViewById(R.id.specs_field);
				EditText year = (EditText) findViewById(R.id.year_field);

				Hardware hardware = new Hardware(-1, aasuNumber.getText()
						.toString(), assignedTo.getText().toString(),
						hardwareStatusComment.getText().toString(), Integer
								.parseInt(hardwareStatus.getText().toString()),
						Integer.parseInt(hardwareType.getText().toString()),
						manufacturer.getText().toString(), modelNumber
								.getText().toString(), "", serialNumber
								.getText().toString(), specs.getText()
								.toString(), year.getText().toString());

				new HardwareTask().execute(hardware);
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	private class HardwareTask extends AsyncTask<Hardware, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Hardware... params) {
			Log.d("add Hardware", String.valueOf(params[0]));
			Hardware hardware = params[0];
			return MoshApiController.addHardware(hardware, workstationId);
		}
		
		@Override
		protected void onPostExecute(Boolean bool) {
			String text;
			if (bool) {
				text = "Post was successful.";
			}
			else {
				text = "Post was unsuccessful.";
			}
			Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
			Intent intent = new Intent(AddHardwareActivity.this,
					HardwareActivity.class);
			intent.putExtra("workstationId", workstationId);
			startActivity(intent);
		}
	}
}
