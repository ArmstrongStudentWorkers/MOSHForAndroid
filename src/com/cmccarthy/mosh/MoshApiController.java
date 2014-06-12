package com.cmccarthy.mosh;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cmccarthy.moshobjects.Hardware;
import com.cmccarthy.moshobjects.Lab;
import com.cmccarthy.moshobjects.MoshObject;
import com.cmccarthy.moshobjects.Workstation;

import android.util.Log;

public class MoshApiController {

	private static String moshApiUrl = "http://mosh.c-mccarthy.com:3000/api/v1/";
	private static String labsIndexUrl = moshApiUrl + "labs.json";
	private static String workstationsIndexUrl = moshApiUrl + "labs/";
	private static String hardwaresIndexUrl = moshApiUrl + "workstations/";
	private static String newHardwaresUrl = hardwaresIndexUrl;

	public static boolean addHardware(Hardware hardware, int workstationId) {
		try {
			JSONObject params = new JSONObject();
			params.put("name", hardware.getName());
			params.put("serial_number", hardware.getSerialNumber());
			params.put("aasu_number", hardware.getAasuNumber());
			params.put("manufacturer", hardware.getManufacturer());
			params.put("model_number", hardware.getModelNumber());
			params.put("assigned_to", hardware.getAssignedTo());
			params.put("specs", hardware.getSpecs());
			params.put("hardware_type_id", hardware.getHardwareTypeId());
			params.put("hardware_status_id", hardware.getHardwareStatusId());
			params.put("hardware_status_comment",
					hardware.getHardwareStatusComment());
			params.put("year", hardware.getYear());
			params.put("workstation_id", workstationId);
			try {
				InputStream is = null;
				String url = newHardwaresUrl + workstationId
						+ "/hardwares.json";
				StringEntity entity = new StringEntity(params.toString());
				HttpPost httpPost = new HttpPost(url);
				httpPost.setEntity(entity);
				httpPost.setHeader("Content-Type", "application/json");
				httpPost.setHeader("Accept", "application/json");

				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();
				is.close();
				return true;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return false;
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static List<Hardware> getHardwares(int workstationId) {
		Log.d("MOSHAPIController, workstationId", String.valueOf(workstationId));

		String url = hardwaresIndexUrl + workstationId + "/hardwares.json";

		Log.d("MOSHAPIController, hardwaresIndexUrl", hardwaresIndexUrl);

		List<Hardware> hardwares = new ArrayList<Hardware>();
		String results = connectToURL(url);

		Log.d("MOSHAPIController, results", results);
		if (results != null) {
			try {
				JSONArray hardwaresJSON = new JSONArray(results);
				for (int i = 0; i < hardwaresJSON.length(); i++) {
					JSONObject element = hardwaresJSON.getJSONObject(i);

					int id = element.getInt("id");
					String aasuNumber = element.getString("aasu_number");
					String assignedTo = element.getString("assigned_to");
					String hardwareStatusComment = element
							.getString("hardware_status_comment");
					int hardwareStatusId = element.getInt("hardware_status_id");
					int hardwareTypeId = element.getInt("hardware_type_id");
					String manufacturer = element.getString("manufacturer");
					String modelNumber = element.getString("model_number");
					String name = element.getString("name");
					String serialNumber = element.getString("serial_number");
					String specs = element.getString("specs");
					String year = element.getString("year");

					hardwares.add(new Hardware(id, aasuNumber, assignedTo,
							hardwareStatusComment, hardwareStatusId,
							hardwareTypeId, manufacturer, modelNumber, name,
							serialNumber, specs, year));
				}
			} catch (JSONException e) {
				Log.d("MOSHAPIController, getHardwares",
						"JSONException: " + e.getMessage());
			}
		}
		return hardwares;
	}

	public static List<Workstation> getWorkstations(int labId) {
		String url = workstationsIndexUrl + labId + "/workstations.json";
		List<Workstation> workstations = new ArrayList<Workstation>();
		String results = connectToURL(url);
		if (results != null) {
			try {
				JSONArray workstationsJSON = new JSONArray(results);
				for (int i = 0; i < workstationsJSON.length(); i++) {
					JSONObject element = workstationsJSON.getJSONObject(i);

					int id = element.getInt("id");
					String name = element.getString("name");
					String number = element.getString("number");
					int workstationTypeId = element
							.getInt("workstation_type_id");

					workstations.add(new Workstation(id, name, number,
							workstationTypeId));
				}
			} catch (JSONException e) {
				Log.d("MOSHAPIController, getWorkstations", "JSONException: "
						+ e.getMessage());
			}
		}
		return workstations;
	}

	public static List<Lab> getLabs() {
		List<Lab> labs = new ArrayList<Lab>();
		String results = connectToURL(labsIndexUrl);
		if (results != null) {
			try {
				JSONArray labsJSON = new JSONArray(results);
				for (int i = 0; i < labsJSON.length(); i++) {
					JSONObject element = labsJSON.getJSONObject(i);

					int id = element.getInt("id");
					String comment = element.getString("comment");
					String name = element.getString("name");
					String room = element.getString("room");

					labs.add(new Lab(id, comment, name, room));
				}
			} catch (JSONException e) {
				Log.d("MOSHAPIController, getLabs",
						"JSONException: " + e.getMessage());
			}
		}
		return labs;
	}

	private static String connectToURL(String url) {
		String result = null;
		try {
			URL moshUrl = new URL(url);
			HttpURLConnection urlConnection = (HttpURLConnection) moshUrl
					.openConnection();
			InputStream in = new BufferedInputStream(
					urlConnection.getInputStream());
			result = convertStreamToString(in);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String convertStreamToString(InputStream is) {
		String result = "";
		Scanner s = new Scanner(is);
		s.useDelimiter("\\A");
		if (s.hasNext()) {
			result += s.next();
		}
		s.close();
		return result;
	}

	public static List<String> getNames(List<? extends MoshObject> list) {
		List<String> names = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			names.add(list.get(i).getName());
		}
		return names;
	}
}