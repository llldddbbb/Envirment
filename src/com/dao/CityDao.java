package com.dao;

import java.util.List;
import java.util.Map;

import com.entity.City;

public interface CityDao {
	
	public List<City> findCityList(Map<String,Object> map);
	
}
