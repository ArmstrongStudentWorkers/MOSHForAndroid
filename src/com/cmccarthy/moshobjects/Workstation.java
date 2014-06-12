package com.cmccarthy.moshobjects;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Workstation implements MoshObject, Serializable {
	
	private int id;
	private String name;
	private String number;
	private int workstationTypeId;
	
	public Workstation(int id, String name, String number,
			int workstationType) {
		this.id = id;
		this.name = name;
		this.number = number;
		this.workstationTypeId = workstationType;
	}
	@Override
	public int getId() {
		return id;
	}
	@Override
	public String getName() {
		return name;
	}
	public String getNumber() {
		return number;
	}

	public int getWorkstationType() {
		return workstationTypeId;
	}
	@Override
	public String toString() {
		return "Workstation [id=" + id + ", name=" + name + ", number="
				+ number + ", workstationTypeId=" + workstationTypeId + "]";
	}
}
