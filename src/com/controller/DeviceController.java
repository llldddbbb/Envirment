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
import com.util.DateUtil;
import com.util.ExcelUtil;
import com.util.PageBean;
import com.util.PageUtil;
import com.util.PropertiesUtil;
import com.util.ResponseUtil;
import com.util.StringUtil;

import net.sf.json.JSONObject;


@Controller
@RequestMapping("/device")//���device��ַӳ��
public class DeviceController {
	
	@Resource
	private DeviceService deviceService;//�Զ�ע��deviceҵ����
	
	//��ȡһ�����е�device������ ����:���ݿ������������
	@RequestMapping("/detail")
	public String detail(@RequestParam(value="deviceName")String deviceName,@RequestParam(value="cityName")String cityName,HttpServletRequest request)throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("deviceName",deviceName);//������ݿ��������
		map.put("start", 0);
		map.put("pageSize", 1);//���ֻȡ����һ��device������
		Device device=deviceService.findDeviceList(map).get(0);//ȡ�����������µ�һ������device
		device.setTimeStamp(DateUtil.formatDate(device.getTimeStamp(), "yyyy-MM-dd HH:mm:ss"));
		HttpSession session=request.getSession();
		session.setAttribute("device", device);//��device����session
		session.setAttribute("deviceName", deviceName);//�����ݿ��������session������ͼ��ı������
		session.setAttribute("cityName", cityName);//������������session
		return "redirect:/detail.jsp";//�ض����������
	}
	//��ȡͼ������ ����:���ݿ�������ʾ�Ŀ����������airquality��ͼ������
	@RequestMapping("/getChartsData")
	public String getChartsData(@RequestParam(value="deviceName",required=false)String deviceName,@RequestParam(value="airquality",required=false)String airquality,@RequestParam(value="chartstype",required=false)String chartstype,HttpServletResponse response)throws Exception{
		JSONObject result=new JSONObject();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("deviceName",deviceName);//������ݿ�������
		if(StringUtil.isEmpty(airquality)||airquality.equals("PM25_ug")){//���ѡ����Ҫ��ʾ��ͼ��������PM25
			map.put("airquality","PM2.5_ug");//���Լ������
			List<Device> deviceList=deviceService.getChartsData(map);//�����ݿ�ȡ����������������
			for(Device d:deviceList){
				result.put(DateUtil.formatDateToStr(d.getTimeStamp(), "yyyy-MM-dd HHʱ"), d.getPM25_ug());//ƴ��JSON����
			}
		}else if(airquality.equals("CO2_ppm")){//���ѡ����Ҫ��ʾ��ͼ��������CO2
			map.put("airquality","CO2_ppm");//���Լ������
			List<Device> deviceList=deviceService.getChartsData(map);//�����ݿ�ȡ����������������
			for(Device d:deviceList){
				result.put(DateUtil.formatDateToStr(d.getTimeStamp(), "yyyy-MM-dd HHʱ"), d.getCO2_ppm());//ƴ��JSON����
			}
		}else if(airquality.equals("CO_ppm")){//���ѡ����Ҫ��ʾ��ͼ��������CO
			map.put("airquality","CO_ppm");//���Լ������
			//map.put("airquality_ref","CO_diff_mV");//���Լ������
			List<Device> deviceList=deviceService.getChartsData(map);//�����ݿ�ȡ����������������
//			JSONArray jsonArray=new JSONArray();
//			JSONObject jo1=new JSONObject();
//			JSONObject jo2=new JSONObject();
			for(Device d:deviceList){
				result.put(DateUtil.formatDateToStr(d.getTimeStamp(), "yyyy-MM-dd HHʱ"), d.getCO_ppm());
				//jo2.put(d.getTimeStamp(), d.getCO_ref());
			}
			//jsonArray.add(jo1);
			//jsonArray.add(jo2);//ƴ��JSON��������
//			ResponseUtil.write(result, response);//�������������ֱ��д��������
//			return null;
		}else if(airquality.equals("NO_ppb")){//���ѡ����Ҫ��ʾ��ͼ��������NO
			map.put("airquality","NO_ppb");//���Լ������
			//map.put("airquality_ref","NO_diff_mV");//���Լ������
			List<Device> deviceList=deviceService.getChartsData(map);//�����ݿ�ȡ����������������
//			JSONArray jsonArray=new JSONArray();
//			JSONObject jo1=new JSONObject();
//			JSONObject jo2=new JSONObject();
			for(Device d:deviceList){
				result.put(DateUtil.formatDateToStr(d.getTimeStamp(), "yyyy-MM-dd HHʱ"), d.getNO_ppb());
				//jo2.put(d.getTimeStamp(), d.getNO_ref());
			}
//			jsonArray.add(jo1);
//			jsonArray.add(jo2);//ƴ��JSON��������
//			ResponseUtil.write(jsonArray, response);//�������������ֱ��д��������
//			return null;
		}else if(airquality.equals("NO2_ppb")){//���ѡ����Ҫ��ʾ��ͼ��������NO2
			map.put("airquality","NO2_ppb");//���Լ������
			//map.put("airquality_ref","NO2_diff_mV");//���Լ������
			List<Device> deviceList=deviceService.getChartsData(map);//�����ݿ�ȡ����������������
//			JSONArray jsonArray=new JSONArray();
//			JSONObject jo1=new JSONObject();
//			JSONObject jo2=new JSONObject();
			for(Device d:deviceList){
				result.put(DateUtil.formatDateToStr(d.getTimeStamp(), "yyyy-MM-dd HHʱ"), d.getNO2_ppb());
				//jo2.put(d.getTimeStamp(), d.getNO2_ref());
			}
//			jsonArray.add(jo1);
//			jsonArray.add(jo2);//ƴ��JSON��������
//			ResponseUtil.write(jsonArray, response);//�������������ֱ��д���� 
//			return null;
		}else if(airquality.equals("Temp")){//���ѡ����Ҫ��ʾ��ͼ��������Temp
			map.put("airquality","Temp");//���Լ������
			List<Device> deviceList=deviceService.getChartsData(map);//�����ݿ�ȡ����������������
			for(Device d:deviceList){
				result.put(DateUtil.formatDateToStr(d.getTimeStamp(), "yyyy-MM-dd HHʱ"), d.getTemp());//ƴ��JSON����
			}
		}else if(airquality.equals("Humi")){//���ѡ����Ҫ��ʾ��ͼ��������Humi
			map.put("airquality","Humi");//���Լ������
			List<Device> deviceList=deviceService.getChartsData(map);//�����ݿ�ȡ����������������
			for(Device d:deviceList){
				result.put(DateUtil.formatDateToStr(d.getTimeStamp(), "yyyy-MM-dd HHʱ"), d.getHumi());//ƴ��JSON����
			}
		}
		System.out.println(result);
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
		ResponseUtil.export(response, wb, cityName+"������������.xls");
		return null;
	}
	
	
}
