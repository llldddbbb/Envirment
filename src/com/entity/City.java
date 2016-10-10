package com.entity;

import java.util.List;

public class City {
	
	private int cityID;
	private String deviceName;
	private String cityName;
	private String lat;
	private String lng;
	private String AQI_lat;
	private String AQI_lng;
	private float distance;
	
	private List<Device> deviceList;
	
	public int getCityID() {
		return cityID;
	}
	public void setCityID(int cityID) {
		this.cityID = cityID;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getAQI_lat() {
		return AQI_lat;
	}
	public void setAQI_lat(String aQI_lat) {
		AQI_lat = aQI_lat;
	}
	public String getAQI_lng() {
		return AQI_lng;
	}
	public void setAQI_lng(String aQI_lng) {
		AQI_lng = aQI_lng;
	}
	public float getDistance() {
		return distance;
	}
	public void setDistance(float distance) {
		this.distance = distance;
	}
	public List<Device> getDeviceList() {
		return deviceList;
	}
	public void setDeviceList(List<Device> deviceList) {
		this.deviceList = deviceList;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	
	
	
}
