package com.cmccarthy.moshobjects;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Lab implements MoshObject, Serializable {
	
	private int id;
	private String name;
	private String comment;
	private String room;
	
	public Lab(int id, String comment, String name, String room) {
		this.id = id;
		this.name = name;
		this.comment = comment;
		this.room = room;
	}
	@Override
	public int getId() {
		return id;
	}
	@Override
	public String getName() {
		return name;
	}
	public String getComment() {
		return comment;
	}

	public String room() {
		return room;
	}

	@Override
	public String toString() {
		return "Lab [id=" + id + ", comment=" + comment + ", name=" + name
				+ ", room=" + room + "]";
	}
}