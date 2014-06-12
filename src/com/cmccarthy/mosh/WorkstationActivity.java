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
import android.widget.ListView;
import android.widget.TextView;

import com.cmccarthy.moshobjects.Workstation;

public class WorkstationActivity extends Activity {

	private int labId = 0;
	private ListView listView;
	private List<Workstation> workstations;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workstation);
		// Show the Up button in the action bar.
		setupActionBar();
		Bundle extras = getIntent().getExtras();
		labId = extras.getInt("labId");
		new WorkstationTask().execute(labId);
	}

	public void registerClickCallback() {
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				TextView textView = (TextView) v;
				int workstationId = 0;
				String workstationName = textView.getText().toString();
				for (int i = 0; i < workstations.size(); i++) {
					Workstation workstation = workstations.get(i);
					if (workstationName.equals(workstation.getName())) {
						workstationId = workstation.getId();
					}
				}
				if (workstationId != 0) {
					Intent intent = new Intent(WorkstationActivity.this,
							HardwareActivity.class);
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
	
	private class WorkstationTask extends AsyncTask<Integer, Void, List<String>> {

		@Override
		protected List<String> doInBackground(Integer... params) {
			workstations = MoshApiController.getWorkstations(params[0]);
			Log.d("doInBackground, getWorkstations", Arrays.toString(workstations.toArray()));
			return MoshApiController.getNames(workstations);
		}

		@Override
		protected void onPostExecute(List<String> workstationNames) {
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					WorkstationActivity.this, R.layout.workstation, workstationNames);
			listView = (ListView) findViewById(R.id.workstation_listView);
			listView.setAdapter(adapter);
			registerClickCallback();
		}
	}
}
