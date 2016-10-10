<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<title>CAM-Campus Air Qulity Measure</title>
	<link href="css/bootstrap.css" rel="stylesheet">
    <link href='css/bootstrap-datepicker.css' rel='stylesheet'>
	<link rel="stylesheet" href="css/environment.css">
	<script src="js/jquery-1.9.1.min.js"></script>
	<script src="js/environment.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/bootstrap-datepicker.js"></script>
	<script src="js/bootstrap-datepicker.zh-CN.js"></script>
	<script src="js/echarts.min.js"></script>
	<script>
		$(function(){
			$('.dateControl').datepicker();
			$('#timepicker').change(function(event) {
				if ($(this).val()=="custom") {
					$('.dateControl').show();
				}else{
					$('.dateControl').hide();
				}
			});
		})
		function exportExcel(){
			window.location.href="${pageContext.request.contextPath}/device/exportExcel.do?deviceName=${deviceName}&cityName=${cityName}";
		}
	</script>
<%
if(session.getAttribute("currentAccount")==null){
	response.sendRedirect("login.jsp");
}
%>

</head>
<body style="background:#4585d7">
	<div class="header">
		<div class="header-nav">
			<a class="logo">CAM-Campus Air Qulity Measure</a>
			<a class="header-btn" href="map.jsp">地图</a>
			<a class="header-btn" href="${pageContext.request.contextPath}/city/list.do?cityID=${currentAccount.place}">列表</a>	
			<a class="header-btn" href="table.jsp">数据</a>
			<a class="header-btn" href="${pageContext.request.contextPath}/account/logout.do">注销</a>
		</div>
	</div>
	<div class="detail-main">
		<div class="detail-left" style="margin-left: 0px;margin-top: 80px;">
			<a class="map-left">历史数据查询</a>
			<a class="map-left">报警数据查询</a>
			<a class="map-left">异常信息查询</a>	
			<a class="map-left" href="index.jsp">返回</a>	
		</div>
		<div class="detail-right">
			<input type="button" style="margin-top: 82px;width:100px;height: 25px;font-size: xx-small;float: right;" onclick="javascript:exportExcel()" value="导出到Excel"/>
			<table style="solid:#000; width: 98%;" border="2" cellspacing="20px">
			<tr>
				<th>监测时间</th>
    			<th>PM2.5(ug/m^3)</th>
    			<th>CO(V)</th>
    			<th>CO2(V)</th>
    			<th>NO(V)</th>
    			<th>NO2(V)</th>
    			<th>温度(C)</th>
    			<th>湿度(%)</th>
			</tr>
			<c:forEach var="device" items="${deviceList }">
				<tr>
					<td>${device.getTimeStamp()}</td>
					<td>${device.PM25 }</td>
					<td>${device.CO_act }</td>
					<td>${device.CO2 }</td>
					<td>${device.NO_act }</td>
					<td>${device.NO2_act }</td>
					<td>${device.getTemp() }</td>
					<td>${device.getHumi() }</td>
				</tr>	
			</c:forEach>
			</table>
			<div class="pagination">
  				<ul>${pageCode }</ul>
  			</div>
		</div>
	</div>	
</body>
</html>