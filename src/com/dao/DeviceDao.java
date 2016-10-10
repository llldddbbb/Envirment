package com.dao;

import java.util.List;
import java.util.Map;

import com.entity.Device;

public interface DeviceDao {
	
	public List<Device> findDeviceList(Map<String,Object> map);
	
	public List<Device> getChartsData(Map<String,Object> map);
	
	public int getDeviceCount(Map<String,Object> map);
	
}
