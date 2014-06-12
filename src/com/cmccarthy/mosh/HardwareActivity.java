package com.cmccarthy.mosh;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.cmccarthy.moshobjects.Hardware;

public class HardwareActivity extends Activity {

	private int workstationId = 0;
	private ListView listView;
	private List<Hardware> hardwares;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hardware);
		setupActionBar();
		Bundle extras = getIntent().getExtras();
		workstationId = extras.getInt("workstationId");
		new HardwareTask().execute(workstationId);

		Button addButton = (Button) findViewById(R.id.add_hardware_button);
		addButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HardwareActivity.this,
						AddHardwareActivity.class);
				intent.putExtra("workstationId", workstationId);
				startActivity(intent);
			}
		});
	}

	public void registerClickCallback() {
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				TextView textView = (TextView) v;
				int hardwareId = 0;
				Hardware hardware = null;
				String hardwareName = textView.getText().toString();
				for (int i = 0; i < hardwares.size(); i++) {
					hardware = hardwares.get(i);
					if (hardwareName.equals(hardware.getName())) {
						hardwareId = hardware.getId();
					}
				}
				if (hardwareId != 0) {
					Intent intent = new Intent(HardwareActivity.this,
							HardwareDetailActivity.class);
					intent.putExtra("hardwareId", hardwareId);
					intent.putExtra("hardware", hardware);
					intent.putExtra("workstationId", workstationId);
					startActivity(intent);
				}
			}
		});
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

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

	private class HardwareTask extends AsyncTask<Integer, Void, List<String>> {

		@Override
		protected List<String> doInBackground(Integer... params) {
			Log.d("doInBackground, getHardware id", String.valueOf(params[0]));
			hardwares = MoshApiController.getHardwares(params[0]);
			Log.d("doInBackground, getHardwares",
					Arrays.toString(hardwares.toArray()));
			return MoshApiController.getNames(hardwares);
		}

		@Override
		protected void onPostExecute(List<String> workstationNames) {
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					HardwareActivity.this, R.layout.hardware, workstationNames);
			listView = (ListView) findViewById(R.id.hardware_listView);
			listView.setAdapter(adapter);
			registerClickCallback();
		}
	}
}
