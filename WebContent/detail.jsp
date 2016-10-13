<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<title>CAM-Campus Air Qulity Measure</title>
	<link href="css/bootstrap.min.css" rel="stylesheet">
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
			<a class="header-btn" href="${pageContext.request.contextPath}/device/findDeviceList.do?deviceName=${deviceName}">数据</a>
			<a class="header-btn" href="${pageContext.request.contextPath}/account/logout.do">注销</a>
		</div>
	</div>
	<div class="detail-main">
		<div class="detail-left">
			<h1 class="detail-place">${cityName }</h1>
			<h1><fmt:formatDate value="${device.getTimeStamp()}" type="Date" pattern="yyyy-MM-dd HH:mm:ss"/></h1>
			<h2>PM2.5(ug/m^3)</h2>
			<table class="detail-data">
				<tr>					
					<th colspan="2" class="detail-PM"><fmt:formatNumber type="number" value="${device.PM25_ug }" pattern="0.00" maxFractionDigits="2"/></th>
				</tr>
				<tr>
					<td>CO2浓度</td>
					<td class="detail-level-1">优</td>
					<td><fmt:formatNumber type="number" value="${device.CO2_ppm }" pattern="0.00" maxFractionDigits="2"/></td>
				</tr>
				<tr>
					<td>湿度</td>
					 <td class="detail-level-2">良</td>
					<td><fmt:formatNumber type="number" value="${device.getHumi() }" pattern="0.00" maxFractionDigits="2"/>%</td>
				</tr>
				<tr>
					<td>温度</td>
					<td class="detail-level-3">差</td>
					<td><fmt:formatNumber type="number" value="${device.getTemp() }" pattern="0.00" maxFractionDigits="2"/>℃</td>
				</tr>
			</table>
			<a class="backhome" href="index.jsp">返回</a>	
		</div>
		<div class="detail-right">
			<h2>图表---${cityName }</h2>
			<select name="airquality" id="airquality">
				<option value="PM25_ug">PM2.5</option>
				<option value="CO2_ppm">CO2浓度</option>
				<option value="CO_ppm">CO浓度</option>
				<option value="NO_ppb">NO浓度</option>
				<option value="NO2_ppb">NO2浓度</option>
				<option value="Temp">温度</option>
				<option value="Humi">湿度</option>
			</select>
			<select name="timepicker" id="timepicker">
				<option value="">最近一天</option>
				<option value="">最近一周</option>
				<option value="">最近一月</option>
				<option value="">最近一年</option>
				<option value="custom">自定义</option>
			</select>
			<input type="text" class="dateControl">
			<select name="chartstype" id="chartstype">
				<option value="line">折线图</option>
				<option value="bar">柱状图</option>
			</select>
			<a id="detail-submit" href="javascript:getChartsData()">提交</a>
			<div id="main" style="width: 800px;height:500px;"></div>
		</div>
	</div>
<script type="text/javascript">
		//获取图表实例
        var myChart = echarts.init(document.getElementById('main'));
		//生成图表数据
        var option = {
            title: {
                text: ''
            },
            tooltip : {},
            xAxis: {
                data: []
            },
            yAxis: {},
            series: [
            {
                name: 'PM25_ug',
                type: 'line',
                data: [],
                itemStyle : {  
                    normal : {  
                        color:'#FF0000',  
                        lineStyle:{  
                            color:'#FF0000'  
                        }  
                    }  
                }
            }, {
                name: 'CO2_ppm',
                type: 'line',
                data: [],
                itemStyle : {  
                    normal : {  
                        color:'#FF0000',  
                        lineStyle:{  
                            color:'#FF0000'  
                        }  
                    }  
                }
            }, {
                name: 'Temp',
                type: 'line',
                data: [],
                itemStyle : {  
                    normal : {  
                        color:'#FF0000',  
                        lineStyle:{  
                            color:'#FF0000'  
                        }  
                    }  
                }
            }, {
                name: 'Humi',
                type: 'line',
                data: [],
                itemStyle : {  
                    normal : {  
                        color:'#FF0000',  
                        lineStyle:{  
                            color:'#FF0000'  
                        }  
                    }  
                }
            }, {
                name: 'CO_ppm',
                type: 'line',
                data: [],
                itemStyle : {  
                    normal : {  
                        color:'#FF0000',  
                        lineStyle:{  
                            color:'#FF0000'  
                        }  
                    }  
                }
            }, {
                name: 'NO_ppb',
                type: 'line',
                data: [],
                itemStyle : {  
                    normal : {  
                        color:'#FF0000',  
                        lineStyle:{  
                            color:'#FF0000'  
                        }  
                    }  
                }
            }, {
                name: 'NO2_ppb',
                type: 'line',
                data: [],
                itemStyle : {  
                    normal : {  
                        color:'#FF0000',  
                        lineStyle:{  
                            color:'#FF0000'  
                        }  
                    }  
                }
            }
            /* ,{
            	name: 'CO_ref',
                type: 'line',
                data: [],
                itemStyle : {  
                    normal : {  
                        color:'#B0E11E',  
                        lineStyle:{  
                            color:'#B0E11E'  
                        }  
                    }  
                }
            }
            ,{
            	name: 'NO_ref',
                type: 'line',
                itemStyle : {  
                    normal : {  
                        color:'#B0E11E',  
                        lineStyle:{  
                            color:'#B0E11E'  
                        }
                    }  
                }
            }
            ,{
            	name: 'NO2_ref',
                type: 'line',
                data: [],
                itemStyle : {  
                    normal : {  
                        color:'#B0E11E',  
                        lineStyle:{  
                            color:'#B0E11E'  
                        }  
                    }  
                }
            } */
            ]
        };
		//生成图表
        myChart.setOption(option,false);
        
        //初始化图表数据，异步加载PM25数据
        myChart.showLoading(); 
        var names=[];    
        var nums=[];    
        $.ajax({type : "post",async : true,url : "${pageContext.request.contextPath}/device/getChartsData.do?deviceName=${deviceName}", data : {},dataType : "json",        //返回数据形式为json
        success : function(result) {
            if (result) {
            	   for(i in result){
            		   names.push(i);
            		   nums.push(result[i]);
            	   }
                   myChart.hideLoading();
                   myChart.setOption({
                	   title: {
                           text: 'PM25_ug'
                       },
                       yAxis: {
                       	name: '单位(ug/m^3)'
                       },
                       toolbox: {
                           feature: {
                               saveAsImage: {}
                           }
                       },
	               	    legend: {
	               	        data:['PM25_ug']
	               	    },
                       xAxis: {
                           data: names
                       },
                       series: [{
                           name: 'PM25_ug',
                           data: nums
                       }]
                   });
            }
       },
        error : function(errorMsg) {
      	  alert("图表请求数据失败!");
        	myChart.hideLoading();
        }
   });
        
//动态改变图表数据的方法
function getChartsData(){
	 myChart. clear();
	 myChart.setOption(option,false);
    var chartstype=$("#chartstype").val();
	var airquality=$("#airquality").val();
	var unit;
	if(airquality=="PM25_ug"){
		unit="ug/m^3";
	}else if(airquality=='CO_ppm'||airquality=='NO_ppb'||airquality=='NO2_ppb'||airquality=='CO2_ppm'){
		unit="V";
	}else if(airquality=="Temp"){
		unit="°C";
	}else{
		unit="%";
	}
	$.post("${pageContext.request.contextPath}/device/getChartsData.do?deviceName=${deviceName}",{"airquality":airquality},function(result){
		var result=eval("("+result+")");
		if(result){
			 myChart.showLoading(); 
		     var names=[];   
		     var nums=[];   
		     var nums_ref=[];
		    /*  if(airquality=='CO'||airquality=='NO'||airquality=='NO2'){
		    	 $(result).each(function(i) {
		    		 var json = result[i];
			    	 for(j in json){
		           		   names.push(j);
		           		   if(i==0){
			           		   nums.push(json[j]);
		           		   }else{
			           		   nums_ref.push(json[j]);
		           		   }
		           	   }
			    	 
		    	 });
                  myChart.hideLoading();  
                  myChart.setOption({ 
                	  title: {
                          text: airquality
                      },
                      yAxis: {
                         	name: "单位("+unit+")"
                      },
                      toolbox: {
                          feature: {
                              saveAsImage: {}
                          }
                      },
                      legend: {
                          data:[airquality+"_act",airquality+"_ref"]
                      },
                      xAxis: {
                          data: names
                      },
                      series: [{
                          name: airquality+"_act",
                          type: chartstype,
                          itemStyle : {  
                              normal : {  
                                  color:'#FF0000',  
                                  lineStyle:{  
                                      color:'#FF0000'  
                                  }  
                              }  
                          },
                          data: nums
                     },{
                    	 name: airquality+"_ref",
                    	 type: chartstype,
                    	 itemStyle : {  
                             normal : {  
                                 color:'#B0E11E',  
                                 lineStyle:{  
                                     color:'#B0E11E'  
                                 }  
                             }  
                         },
                    	 data:nums_ref
                     }]
                  },false); */
		     
		    	 for(i in result){
	           		   names.push(i);
	           		   nums.push(result[i]);
	           	   }
                myChart.hideLoading();
                myChart.setOption({ 
                	title: {
                        text: airquality
                    },
                    yAxis: {
                     	name: "单位("+unit+")"
                    },
                    legend: {
                        data:[airquality+'']
                    },
                    toolbox: {
                        feature: {
                            saveAsImage: {}
                        }
                    },
                    tooltip: {},
                    legend: {
                        data:[airquality]
                    },
                    xAxis: {
                        data: names
                    },
                    series: [{
                        name: airquality+'',
                        type: chartstype,
                        data: nums
                   }]
                });
			 
		}else{
			alert("图表请求数据失败!");
	        myChart.hideLoading();
		}
	});
}
</script>
	
</body>
</html>