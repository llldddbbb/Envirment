package com.util;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.entity.Device;

public class ExcelUtil {

	public static void fillExcelData(List<Device> deviceList,Workbook wb)throws Exception{
		int rowIndex=0;
		String headers[]={"¼ì²âÊ±¼ä","NO(ppb)","NO_diff(mV)","NO2(ppb)","NO2_diff(V)","CO(ppm)","CO_diff(mV)","CO2(ppm)","CO2(mV)","PM1(ug)","PM25(ug)","PM10(ug)","Temp(C)","Humi(%)"};
		Sheet sheet=wb.createSheet();
		HSSFCellStyle style = (HSSFCellStyle) wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		sheet.setColumnWidth(0, 22 * 256);//20¸ö×Ö·û¿í 
		sheet.setColumnWidth(1, 15 * 256);
		sheet.setColumnWidth(2, 15 * 256);
		sheet.setColumnWidth(3, 15 * 256);
		sheet.setColumnWidth(4, 15 * 256);
		sheet.setColumnWidth(5, 15 * 256);
		sheet.setColumnWidth(6, 15 * 256);
		sheet.setColumnWidth(7, 15 * 256);
		sheet.setColumnWidth(8, 15 * 256);
		sheet.setColumnWidth(9, 15 * 256);
		sheet.setColumnWidth(10, 15 * 256);
		sheet.setColumnWidth(11, 15 * 256);
		sheet.setColumnWidth(12, 15 * 256);
		sheet.setColumnWidth(13, 15 * 256);
		Row row=sheet.createRow(rowIndex++);
		for(int i=0;i<headers.length;i++){
			Cell cell=row.createCell(i);
			cell.setCellValue(headers[i]);
			cell.setCellStyle(style);
		}
		for(Device device:deviceList){
			row=sheet.createRow(rowIndex++);
			if(device.getTimeStamp()!=null){
				Cell cell=row.createCell(0);
				cell.setCellValue(DateUtil.formatDateToStr(device.getTimeStamp(), "yyyy-MM-dd HH:mm:ss"));
				cell.setCellStyle(style);
			}else{
				row.createCell(0).setCellValue("");
			}
			
			if(device.getNO_ppb()!=null){
				Cell cell=row.createCell(1);
				cell.setCellValue(device.getNO_ppb());
				cell.setCellStyle(style);
			}else{
				row.createCell(1).setCellValue("");
			}
			if(device.getNO_diff_mV()!=null){
				Cell cell=row.createCell(2);
				cell.setCellValue(device.getNO_diff_mV());
				cell.setCellStyle(style);
			}else{
				row.createCell(2).setCellValue("");
			}
			if(device.getNO2_ppb()!=null){
				Cell cell=row.createCell(3);
				cell.setCellValue(device.getNO2_ppb());
				cell.setCellStyle(style);
			}else{
				row.createCell(3).setCellValue("");
			}
			if(device.getNO2_diff_mV()!=null){
				Cell cell=row.createCell(4);
				cell.setCellValue(device.getNO2_diff_mV());
				cell.setCellStyle(style);
			}else{
				row.createCell(4).setCellValue("");
			}
			if(device.getCO_ppm()!=null){
				Cell cell=row.createCell(5);
				cell.setCellValue(device.getCO_ppm());
				cell.setCellStyle(style);
			}else{
				row.createCell(5).setCellValue("");
			}
			if(device.getCO_diff_mV()!=null){
				Cell cell=row.createCell(6);
				cell.setCellValue(device.getCO_diff_mV());
				cell.setCellStyle(style);
			}else{
				row.createCell(6).setCellValue("");
			}
			if(device.getCO2_ppm()!=null){
				Cell cell=row.createCell(7);
				cell.setCellValue(device.getCO2_ppm());
				cell.setCellStyle(style);
			}else{
				row.createCell(7).setCellValue("");
			}
			if(device.getCO2_mV()!=null){
				Cell cell=row.createCell(8);
				cell.setCellValue(device.getCO2_mV());
				cell.setCellStyle(style);
			}else{
				row.createCell(8).setCellValue("");
			}
			if(device.getPM1_ug()!=null){
				Cell cell=row.createCell(9);
				cell.setCellValue(device.getPM1_ug());
				cell.setCellStyle(style);
			}else{
				row.createCell(9).setCellValue("");
			}
			if(device.getPM25_ug()!=null){
				Cell cell=row.createCell(10);
				cell.setCellValue(device.getPM25_ug());
				cell.setCellStyle(style);
			}else{
				row.createCell(10).setCellValue("");
			}
			if(device.getPM10_ug()!=null){
				Cell cell=row.createCell(11);
				cell.setCellValue(device.getPM10_ug());
				cell.setCellStyle(style);
			}else{
				row.createCell(11).setCellValue("");
			}
			if(device.getTemp()!=null){
				Cell cell=row.createCell(12);
				cell.setCellValue(device.getTemp());
				cell.setCellStyle(style);
			}else{
				row.createCell(12).setCellValue("");
			}
			if(device.getHumi()!=null){
				Cell cell=row.createCell(13);
				cell.setCellValue(device.getHumi());
				cell.setCellStyle(style);
			}else{
				row.createCell(13).setCellValue("");
			}
			
			
		}
		
	}
}
