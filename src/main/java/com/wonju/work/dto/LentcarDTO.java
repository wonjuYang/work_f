package com.wonju.work.dto;

public class LentcarDTO {
	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}

	private String name;
	private String addr;
	private String lat;
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLogt() {
		return logt;
	}
	public void setLogt(String logt) {
		this.logt = logt;
	}

	private String logt;
}
