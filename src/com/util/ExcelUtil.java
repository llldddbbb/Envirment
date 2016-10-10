package com.util;

import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.entity.Device;

public class ExcelUtil {

	public static void fillExcelData(List<Device> deviceList,Workbook wb)throws Exception{
		int rowIndex=0;
		String headers[]={"检测时间","PM2.5(ug/m^3)","CO(V)","CO2(V)","NO(V)","NO2(V)","温度(C)","湿度(%)"};
		Sheet sheet=wb.createSheet();
		sheet.setColumnWidth(0, 22 * 256);//20个字符宽 
		sheet.setColumnWidth(1, 15 * 256);
		sheet.setColumnWidth(2, 15 * 256);
		sheet.setColumnWidth(3, 15 * 256);
		sheet.setColumnWidth(4, 15 * 256);
		sheet.setColumnWidth(5, 15 * 256);
		sheet.setColumnWidth(6, 15 * 256);
		sheet.setColumnWidth(7, 15 * 256);
		Row row=sheet.createRow(rowIndex++);
		for(int i=0;i<headers.length;i++){
			row.createCell(i).setCellValue(headers[i]);
		}
		for(Device device:deviceList){
			row=sheet.createRow(rowIndex++);
			row.createCell(0).setCellValue(device.getTimeStamp());
			row.createCell(1).setCellValue(device.getPM25());
			row.createCell(2).setCellValue(device.getCO_act());
			row.createCell(3).setCellValue(device.getCO2());
			row.createCell(4).setCellValue(device.getNO_act());
			row.createCell(5).setCellValue(device.getNO2_act());
			row.createCell(6).setCellValue(device.getTemp());
			row.createCell(7).setCellValue(device.getHumi());
		}
		
	}
}
