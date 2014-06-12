package com.cmccarthy.mosh;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.TextView;

import com.cmccarthy.moshobjects.Hardware;

public class HardwareDetailActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hardware_detail);
		setupActionBar();
		Bundle extras = getIntent().getExtras();
		Hardware hardware = (Hardware) extras.get("hardware");
		
		TextView hardwareDetails = (TextView) findViewById(R.id.hardware_details);
		TextView serialNumber = (TextView) findViewById(R.id.serial_number);
		TextView asuNumber = (TextView) findViewById(R.id.asu_number);
		TextView manufacturer = (TextView) findViewById(R.id.manufacturer);
		TextView modelNumber = (TextView) findViewById(R.id.model_number);
		TextView assignedTo = (TextView) findViewById(R.id.assigned_to);
		TextView specs = (TextView) findViewById(R.id.specs);
		TextView hardwareType = (TextView) findViewById(R.id.hardware_type);
		TextView hardwareStatus = (TextView) findViewById(R.id.hardware_status);
		TextView hardwareStatusComment = (TextView) findViewById(R.id.hardware_status_comment);
		TextView year = (TextView) findViewById(R.id.year);
		
		hardwareDetails.setText(hardware.getName() + " Details");
		serialNumber.setText(hardware.getSerialNumber());
		asuNumber.setText(hardware.getAasuNumber());
		manufacturer.setText(hardware.getManufacturer());
		modelNumber.setText(hardware.getModelNumber());
		assignedTo.setText(hardware.getAssignedTo());
		specs.setText(hardware.getSpecs());
		hardwareType.setText(String.valueOf(hardware.getHardwareTypeId()));
		hardwareStatus.setText(String.valueOf(hardware.getHardwareStatusId()));
		hardwareStatusComment.setText(hardware.getHardwareStatusComment());
		year.setText(hardware.getYear());
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
}