package com.cmccarthy.mosh;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cmccarthy.moshobjects.Lab;

public class MoshActivity extends Activity {

	private ListView listView;
	private List<Lab> labs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mosh);
		new LabTask().execute();
	}

	public void registerClickCallback() {
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				TextView textView = (TextView) v;
				String labName = textView.getText().toString();
				int labId = 0;
				for (int i = 0; i < labs.size(); i++) {
					Lab lab = labs.get(i);
					if (labName.equals(lab.getName())) {
						labId = lab.getId();
					}
				}
				if (labId != 0) {
					Intent intent = new Intent(MoshActivity.this,
							WorkstationActivity.class);
					intent.putExtra("labId", labId);
					startActivity(intent);
				}
			}
		});

	}

	private class LabTask extends AsyncTask<Void, Void, List<String>> {

		@Override
		protected List<String> doInBackground(Void... params) {
			labs = MoshApiController.getLabs();
			Log.d("doInBackground, getLabs", Arrays.toString(labs.toArray()));
			return MoshApiController.getNames(labs);
		}

		@Override
		protected void onPostExecute(List<String> labNames) {
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					MoshActivity.this, R.layout.lab, labNames);
			listView = (ListView) findViewById(R.id.lab_listview);
			listView.setAdapter(adapter);
			registerClickCallback();
		}
	}

}
