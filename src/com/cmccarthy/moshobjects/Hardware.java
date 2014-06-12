package com.cmccarthy.moshobjects;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Hardware implements MoshObject, Serializable {

	public int id;
	private String name;
	private String aasuNumber;
	private String assignedTo;
	private String hardwareStatusComment;
	private int hardwareStatusId;
	private int hardwareTypeId;
	private String manufacturer;
	private String modelNumber;
	private String serialNumber;
	private String specs;
	private String year;
	
	public Hardware(int id, String aasuNumber, String assignedTo,
			String hardwareStatusComment, int hardwareStatusId,
			int hardwareTypeId, String manufacturer, String modelNumber,
			String name, String serialNumber, String specs, String year) {
		this.id = id;
		this.name = name;
		this.aasuNumber = aasuNumber;
		this.assignedTo = assignedTo;
		this.hardwareStatusComment = hardwareStatusComment;
		this.hardwareStatusId = hardwareStatusId;
		this.hardwareTypeId = hardwareTypeId;
		this.manufacturer = manufacturer;
		this.modelNumber = modelNumber;
		this.serialNumber = serialNumber;
		this.specs = specs;
		this.year = year;
	}
	@Override
	public int getId() {
		return id;
	}
	@Override
	public String getName() {
		return name;
	}
	public String getAasuNumber() {
		return aasuNumber;
	}
	public String getAssignedTo() {
		return assignedTo;
	}
	public String getHardwareStatusComment() {
		return hardwareStatusComment;
	}
	public int getHardwareStatusId() {
		return hardwareStatusId;
	}
	public int getHardwareTypeId() {
		return hardwareTypeId;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public String getModelNumber() {
		return modelNumber;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public String getSpecs() {
		return specs;
	}
	public String getYear() {
		return year;
	}
	@Override
	public String toString() {
		return "Hardware [id=" + id + ", aasuNumber=" + aasuNumber
				+ ", assignedTo=" + assignedTo + ", hardwareStatusComment="
				+ hardwareStatusComment + ", hardwareStatus=" + hardwareStatusId
				+ ", name=" + name + ", serialNumber=" + serialNumber
				+ ", specs=" + specs + ", year=" + year + "]";
	}
}
