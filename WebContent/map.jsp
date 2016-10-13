<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<title>CAM-Campus Air Qulity Measure</title>
	<link rel="stylesheet" href="css/environment.css">
	<script src="js/jquery-1.9.1.min.js"></script>
	<script src="js/environment.js"></script>
	<script src="js/echarts.min.js"></script>
	<script src="js/china.js"></script>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=Um05HtLey0wwsiGAy01LWoQu9KaMAaEq"></script>
	<script type="text/javascript">
	function searchPlace(){
		var placesearch=$("#mapsearch").val();
		window.location.href="${pageContext.request.contextPath}/city/list.do?cityID=${currentAccount.place}&view=map&placesearch="+placesearch;
	}
	</script>
	<style type="text/css">
	#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
	</style>
<%
if(session.getAttribute("currentAccount")==null){
	response.sendRedirect("login/a.jsp");
}
%>
</head>
<body style="background:#4585d7">
	<div class="header">
		<div class="header-nav">
			<a class="logo" href="${pageContext.request.contextPath}/city/list.do?cityID=${currentAccount.place}">CAM-Campus Air Qulity Measure</a>
			<a class="header-btn" href="map.jsp">地图</a>
			<a class="header-btn" href="${pageContext.request.contextPath}/city/list.do?cityID=${currentAccount.place}">列表</a>			
			<a class="header-btn" href="${pageContext.request.contextPath}/account/logout.do">注销</a>
		</div>
	</div>
	<div class="detail-main">
		<div class="detail-left">
			<input class="map-input" type="text" id="mapsearch" placeholder="地点搜索框" onkeyup="if(event.keyCode==13) searchPlace()">
			<ul class="places map-input">
			<c:forEach var="city" items="${cityList }">
				<li>${city.cityName }</li>
			</c:forEach>
			</ul>
			<a class="backhome" href="${pageContext.request.contextPath}/city/list.do?cityID=${currentAccount.place}">返回</a>	
		</div>
		<div class="detail-right">
			<div class="mapcanvas" id="allmap" style="height: 600px;width: 880px;margin-top: 100px">
            </div>
		</div>
	</div>
	<script type="text/javascript">
	// 百度地图API功能
	var map = new BMap.Map("allmap");    // 创建Map实例
	map.centerAndZoom(new BMap.Point(116.404, 39.915), 5);  // 初始化地图,设置中心点坐标和地图级别
	map.addControl(new BMap.MapTypeControl());   //添加地图类型控件
	map.setCurrentCity("北京");          // 设置地图显示的城市 此项是必须设置的
	var top_left_navigation = new BMap.NavigationControl();  //左上角，添加默认缩放平移控件
	map.addControl(top_left_navigation);     
	map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
	
	function loadGeo(placesearch){
		var url;
		
		if(placesearch==''||placesearch==null){
			url='${pageContext.request.contextPath}/city/getCityGeo.do?cityID=${currentAccount.place}'
		}else{
			url='${pageContext.request.contextPath}/city/getCityGeo.do?cityID=${currentAccount.place}&placesearch='+placesearch;
		}
		//异步加载地图数据
		$.post(url,{},function(result){ 
			var result=eval("("+result+")");
			if(result){
				$.each(result, function(i,val){
					var point = new BMap.Point(val.geo[0],val.geo[1]);
					var marker = new BMap.Marker(point);
				    map.addOverlay(marker);
				    marker.addEventListener("mouseover",function(e){
					var opts = {
							width : 250,     // 信息窗口宽度
							height: 180,     // 信息窗口高度
							title : val.name , // 信息窗口标题
							enableMessage:true//设置允许信息窗发送短息
					};
					
					var p = e.target;
					var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
					var content="PM2.5:"+val.PM25.toFixed(2)+"ug/m^3<br>CO:"+val.CO.toFixed(2)+"V<br>CO2:"+val.CO2.toFixed(2)+"V<br>NO:"+val.NO.toFixed(2)+"V<br>NO2:"+val.NO2.toFixed(2)+"V<br>温度:"+val.Temp.toFixed(2)+"C<br>湿度:"+val.Humi.toFixed(2)+"%";
					var infoWindow = new BMap.InfoWindow(content,opts);  // 创建信息窗口对象 
					map.openInfoWindow(infoWindow,point); //开启信息窗口
					});
				    marker.addEventListener("click",function(e){
				    	window.location.href='${pageContext.request.contextPath}/device/findDeviceList.do?deviceName='+val.deviceName+"_1h"; 
				    });
				});
			}else{
				alert("加载地图失败!");
			}
		});
		} 
		//调用方法
		loadGeo('${placesearch}');
		
	</script>
</body>
</html>