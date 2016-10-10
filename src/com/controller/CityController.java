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
@RequestMapping("/city")//���city��ַӳ��
public class CityController {
	
	@Resource
	private CityService cityService;//�Զ�ע�����ҵ����
	
	@Resource
	private DeviceService deviceService;//�Զ�ע������豸��
	
	//�����б���ʾ������:��Ҫ��ʾ�ĳ���ID��������������������Ҫ��ת�Ľ���
	@RequestMapping("/list")
	public String list(@RequestParam(value="cityID")String cityID,@RequestParam(value="placesearch",required=false)String placesearch,@RequestParam(value="view",required=false)String view,HttpServletRequest request)throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("cityID", cityID);//��ӳ���ID����
		if(StringUtil.isNotEmpty(placesearch)){
			map.put("placesearch", StringUtil.formatSQLlike(placesearch));//��Ϊ���������������
			HttpSession session=request.getSession();
			session.setAttribute("placesearch", placesearch);
		}
		List<City> cityList=cityService.findCityList(map);//�����ݿ�ȡ�����������ĳ����б�
		for(City c:cityList){//�������У���ÿ�������µ�deviceȡ��������Ӧ����
			Map<String,Object> map2=new HashMap<String,Object>();
			map2.put("deviceName",c.getDeviceName());
			map2.put("start", 0);
			map2.put("pageSize", 1);
			c.setDeviceList(deviceService.findDeviceList(map2));
		}
		
		HttpSession session=request.getSession();
		session.setAttribute("cityList", cityList);//�������б�����session
		if(StringUtil.isNotEmpty(view)){
			return "redirect:/map.jsp";//��ת����ͼ����
		}else{
			return "redirect:/index.jsp";//��ת���б����
		}
	}
	
	//�б����������ʾ ����:������������ı����Լ�����ID
	@RequestMapping("/searchTip")
	public String searchTip(@RequestParam(value="search-text",required=false)String search_text,@RequestParam(value="cityID")String cityID,HttpServletResponse response)throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("cityID", cityID);//��ӳ���ID����
		if(StringUtil.isNotEmpty(search_text)){
			map.put("placesearch", StringUtil.formatSQLlike(search_text));//���������������ı�����
		}
		List<City> cityList=cityService.findCityList(map);//�����ݿ�ȡ�����������ĳ����б�
		List<String> cityNameList=new ArrayList<>();
		for(City c:cityList) {//�������б�ĳ������Ƴ������װ��JSON���鲢д��������
			cityNameList.add(c.getCityName());
		}
		JSONArray result=JSONArray.fromObject(cityNameList);
		ResponseUtil.write(result, response);
		return null;
	}
	
	//��ȡ��ͼ����ĳ��е���ʾ�б� ����: ����ID
	@RequestMapping("/getCityGeo")
	public String getCityGeo(@RequestParam(value="cityID")String cityID,@RequestParam(value="placesearch",required=false)String placesearch,HttpServletResponse response,HttpServletRequest request)throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("cityID", cityID);//��ӳ���ID����
		if(StringUtil.isNotEmpty(placesearch)){
			map.put("placesearch", StringUtil.formatSQLlike(placesearch));
			HttpSession session=request.getSession();
			session.removeAttribute("placesearch");
		}
		List<City> cityList=cityService.findCityList(map);//�����ݿ�ȡ�����������ĳ����б�
		JSONArray result=new JSONArray();
		for(City c:cityList){//�����о�γ����Ϣ����Ӧ���е�deviceƴ�ӳ�JSON����
			//ƴ�ӳ��о�γ����Ϣ
			JSONObject jsonObject=new JSONObject();
			float[] geo=new float[2];
			geo[0]=Float.parseFloat(c.getLng().split("E")[0]);
			geo[1]=Float.parseFloat(c.getLat().split("N")[0]);
			jsonObject.put("geo", geo);
			//ƴ��ÿ�����е�dev��Ϣ
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
		//�����о�γ����Ϣ����Ӧ���е�deviceƴ�ӳ�JSON���飬��д��������
		ResponseUtil.write(result, response);
		return null;
	}
	
	
}
