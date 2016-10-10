package com.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.entity.Device;
import com.service.DeviceService;
import com.util.ExcelUtil;
import com.util.PageBean;
import com.util.PageUtil;
import com.util.PropertiesUtil;
import com.util.ResponseUtil;
import com.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Controller
@RequestMapping("/device")//添加device地址映射
public class DeviceController {
	
	@Resource
	private DeviceService deviceService;//自动注入device业务类
	
	//获取一个城市的device的详情 参数:数据库表名，城市名
	@RequestMapping("/detail")
	public String detail(@RequestParam(value="deviceName")String deviceName,@RequestParam(value="cityName")String cityName,HttpServletRequest request){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("deviceName",deviceName);//添加数据库表名条件
		map.put("start", 0);
		map.put("pageSize", 1);//添加只取最新一条device的条件
		Device device=deviceService.findDeviceList(map).get(0);//取出符合条件下的一条最新device
		HttpSession session=request.getSession();
		session.setAttribute("device", device);//将device塞进session
		session.setAttribute("deviceName", deviceName);//将数据库表名塞进session，更新图表的必须参数
		session.setAttribute("cityName", cityName);//将城市名塞进session
		return "redirect:/detail.jsp";//重定向到详情界面
	}
	//获取图表数据 参数:数据库名，显示的空气质量类别airquality，图表类型
	@RequestMapping("/getChartsData")
	public String getChartsData(@RequestParam(value="deviceName",required=false)String deviceName,@RequestParam(value="airquality",required=false)String airquality,@RequestParam(value="chartstype",required=false)String chartstype,HttpServletResponse response)throws Exception{
		JSONObject result=new JSONObject();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("deviceName",deviceName);//添加数据库名参数
		if(StringUtil.isEmpty(airquality)||airquality.equals("PM25")){//如果选择需要显示的图表类型是PM25
			map.put("airquality","PM25");//添加约束条件
			List<Device> deviceList=deviceService.getChartsData(map);//从数据库取出符合条件的数据
			for(Device d:deviceList){
				result.put(d.getTimeStamp(), d.getPM25());//拼接JSON数据
			}
		}else if(airquality.equals("CO2")){//如果选择需要显示的图表类型是CO2
			map.put("airquality","CO2");//添加约束条件
			List<Device> deviceList=deviceService.getChartsData(map);//从数据库取出符合条件的数据
			for(Device d:deviceList){
				result.put(d.getTimeStamp(), d.getCO2());//拼接JSON数据
			}
		}else if(airquality.equals("CO")){//如果选择需要显示的图表类型是CO
			map.put("airquality_act","CO_act");//添加约束条件
			map.put("airquality_ref","CO_ref");//添加约束条件
			List<Device> deviceList=deviceService.getChartsData(map);//从数据库取出符合条件的数据
			JSONArray jsonArray=new JSONArray();
			JSONObject jo1=new JSONObject();
			JSONObject jo2=new JSONObject();
			for(Device d:deviceList){
				jo1.put(d.getTimeStamp(), d.getCO_act());
				jo2.put(d.getTimeStamp(), d.getCO_ref());
			}
			jsonArray.add(jo1);
			jsonArray.add(jo2);//拼接JSON数组数据
			ResponseUtil.write(jsonArray, response);//如果是两列数据直接写出到界面
			return null;
		}else if(airquality.equals("NO")){//如果选择需要显示的图表类型是NO
			map.put("airquality_act","NO_act");//添加约束条件
			map.put("airquality_ref","NO_ref");//添加约束条件
			List<Device> deviceList=deviceService.getChartsData(map);//从数据库取出符合条件的数据
			JSONArray jsonArray=new JSONArray();
			JSONObject jo1=new JSONObject();
			JSONObject jo2=new JSONObject();
			for(Device d:deviceList){
				jo1.put(d.getTimeStamp(), d.getNO_act());
				jo2.put(d.getTimeStamp(), d.getNO_ref());
			}
			jsonArray.add(jo1);
			jsonArray.add(jo2);//拼接JSON数组数据
			ResponseUtil.write(jsonArray, response);//如果是两列数据直接写出到界面
			return null;
		}else if(airquality.equals("NO2")){//如果选择需要显示的图表类型是NO2
			map.put("airquality_act","NO2_act");//添加约束条件
			map.put("airquality_ref","NO2_ref");//添加约束条件
			List<Device> deviceList=deviceService.getChartsData(map);//从数据库取出符合条件的数据
			JSONArray jsonArray=new JSONArray();
			JSONObject jo1=new JSONObject();
			JSONObject jo2=new JSONObject();
			for(Device d:deviceList){
				jo1.put(d.getTimeStamp(), d.getNO2_act());
				jo2.put(d.getTimeStamp(), d.getNO2_ref());
			}
			jsonArray.add(jo1);
			jsonArray.add(jo2);//拼接JSON数组数据
			ResponseUtil.write(jsonArray, response);//如果是两列数据直接写出到 
			return null;
		}else if(airquality.equals("Temp")){//如果选择需要显示的图表类型是Temp
			map.put("airquality","Temp");//添加约束条件
			List<Device> deviceList=deviceService.getChartsData(map);//从数据库取出符合条件的数据
			for(Device d:deviceList){
				result.put(d.getTimeStamp(), d.getTemp());//拼接JSON数据
			}
		}else if(airquality.equals("Humi")){//如果选择需要显示的图表类型是Humi
			map.put("airquality","Humi");//添加约束条件
			List<Device> deviceList=deviceService.getChartsData(map);//从数据库取出符合条件的数据
			for(Device d:deviceList){
				result.put(d.getTimeStamp(), d.getHumi());//拼接JSON数据
			}
		}
		ResponseUtil.write(result, response);
		return null;
	}
	
	@RequestMapping("/findDeviceList")
	public String findDeviceList(@RequestParam(value="deviceName")String deviceName,@RequestParam(value="page",required=false)String page,HttpServletRequest request)throws Exception{
		if(StringUtil.isEmpty(page)){
			page="1";
		}
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(PropertiesUtil.getValue("tablePageSize")));
		Map<String ,Object> map=new HashMap<String,Object>();
		map.put("deviceName", deviceName);
		map.put("start", pageBean.getStart());
		map.put("pageSize", pageBean.getPageSize());
		List<Device> deviceList=deviceService.findDeviceList(map);
		int total=deviceService.getDeviceCount(map);
		String pageCode=PageUtil.genPaginationNoParam("/Environment/device/findDeviceList.do?deviceName="+deviceName, total, Integer.parseInt(page), pageBean.getPageSize());
		HttpSession session=request.getSession();
		session.setAttribute("deviceList", deviceList);
		session.setAttribute("pageCode", pageCode);
		return "redirect:/table.jsp";
	}
	
	@RequestMapping("exportExcel")
	public String exportExcel(@RequestParam(value="deviceName")String deviceName,@RequestParam(value="cityName")String cityName,HttpServletResponse response)throws Exception{
		Map<String ,Object> map=new HashMap<String,Object>();
		map.put("deviceName", deviceName);
		List<Device> deviceList=deviceService.findDeviceList(map);
		Workbook wb=new HSSFWorkbook();
		ExcelUtil.fillExcelData(deviceList, wb);
		ResponseUtil.export(response, wb, cityName+"环境质量数据.xls");
		return null;
	}
	
	
}
