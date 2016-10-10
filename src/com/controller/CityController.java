package com.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.entity.City;
import com.entity.Device;
import com.service.CityService;
import com.service.DeviceService;
import com.util.ResponseUtil;
import com.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Controller
@RequestMapping("/city")//添加city地址映射
public class CityController {
	
	@Resource
	private CityService cityService;//自动注入城市业务类
	
	@Resource
	private DeviceService deviceService;//自动注入测量设备类
	
	//城市列表显示，参数:需要显示的城市ID，城市名称搜索条件，要跳转的界面
	@RequestMapping("/list")
	public String list(@RequestParam(value="cityID")String cityID,@RequestParam(value="placesearch",required=false)String placesearch,@RequestParam(value="view",required=false)String view,HttpServletRequest request)throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("cityID", cityID);//添加城市ID条件
		if(StringUtil.isNotEmpty(placesearch)){
			map.put("placesearch", StringUtil.formatSQLlike(placesearch));//不为空则添加搜索条件
			HttpSession session=request.getSession();
			session.setAttribute("placesearch", placesearch);
		}
		List<City> cityList=cityService.findCityList(map);//从数据库取出符合条件的城市列表
		for(City c:cityList){//遍历城市，将每个城市下的device取出存入相应城市
			Map<String,Object> map2=new HashMap<String,Object>();
			map2.put("deviceName",c.getDeviceName());
			map2.put("start", 0);
			map2.put("pageSize", 1);
			c.setDeviceList(deviceService.findDeviceList(map2));
		}
		
		HttpSession session=request.getSession();
		session.setAttribute("cityList", cityList);//将城市列表塞进session
		if(StringUtil.isNotEmpty(view)){
			return "redirect:/map.jsp";//跳转至地图界面
		}else{
			return "redirect:/index.jsp";//跳转至列表界面
		}
	}
	
	//列表界面搜索提示 参数:搜索框输入的文本，以及城市ID
	@RequestMapping("/searchTip")
	public String searchTip(@RequestParam(value="search-text",required=false)String search_text,@RequestParam(value="cityID")String cityID,HttpServletResponse response)throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("cityID", cityID);//添加城市ID条件
		if(StringUtil.isNotEmpty(search_text)){
			map.put("placesearch", StringUtil.formatSQLlike(search_text));//添加搜索框输入的文本条件
		}
		List<City> cityList=cityService.findCityList(map);//从数据库取出符合条件的城市列表
		List<String> cityNameList=new ArrayList<>();
		for(City c:cityList) {//将城市列表的城市名称抽出，封装成JSON数组并写出到界面
			cityNameList.add(c.getCityName());
		}
		JSONArray result=JSONArray.fromObject(cityNameList);
		ResponseUtil.write(result, response);
		return null;
	}
	
	//获取地图界面的城市的显示列表 参数: 城市ID
	@RequestMapping("/getCityGeo")
	public String getCityGeo(@RequestParam(value="cityID")String cityID,@RequestParam(value="placesearch",required=false)String placesearch,HttpServletResponse response,HttpServletRequest request)throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("cityID", cityID);//添加城市ID条件
		if(StringUtil.isNotEmpty(placesearch)){
			map.put("placesearch", StringUtil.formatSQLlike(placesearch));
			HttpSession session=request.getSession();
			session.removeAttribute("placesearch");
		}
		List<City> cityList=cityService.findCityList(map);//从数据库取出符合条件的城市列表
		JSONArray result=new JSONArray();
		for(City c:cityList){//将城市经纬度信息和相应城市的device拼接成JSON数组
			//拼接城市经纬度信息
			JSONObject jsonObject=new JSONObject();
			float[] geo=new float[2];
			geo[0]=Float.parseFloat(c.getLng().split("E")[0]);
			geo[1]=Float.parseFloat(c.getLat().split("N")[0]);
			jsonObject.put("geo", geo);
			//拼接每个城市的dev信息
			Map<String,Object> map2=new HashMap<String,Object>();
			map2.put("deviceName",c.getDeviceName());
			map2.put("start", 0);
			map2.put("pageSize", 1);
			Device device=deviceService.findDeviceList(map2).get(0);
			jsonObject.put("name", c.getCityName());
			jsonObject.put("PM25", Float.parseFloat(device.getPM25()));
			jsonObject.put("CO", Float.parseFloat(device.getCO_act()));
			jsonObject.put("CO2", Float.parseFloat(device.getCO2()));
			jsonObject.put("NO", Float.parseFloat(device.getNO_act()));
			jsonObject.put("NO2", Float.parseFloat(device.getNO2_act()));
			jsonObject.put("Temp", Float.parseFloat(device.getTemp()));
			jsonObject.put("Humi", Float.parseFloat(device.getHumi()));
			jsonObject.put("deviceName", c.getDeviceName());
			result.add(jsonObject);
		}
		//将城市经纬度信息和相应城市的device拼接成JSON数组，并写出到界面
		ResponseUtil.write(result, response);
		return null;
	}
	
	
}
