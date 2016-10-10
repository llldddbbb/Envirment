<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<title>CAM-Campus Air Qulity Measure</title>
	<link rel="stylesheet" href="../css/environment.css">
	<script src="../js/jquery-1.9.1.min.js"></script>
	<script src="../js/environment.js"></script>
	<style>
		body{
			background: url(../img/img3.JPG);
			background-size: cover;
		}
	</style>
	<script type="text/javascript">
		function login(){
			var username=$("#username").val();
			var password=$("#password").val();
			$.post("${pageContext.request.contextPath}/account/login.do",{username:username,password:password},function(result){
				var result=eval("("+result+")");
				if(result.success){
					window.location.href="${pageContext.request.contextPath}/city/list.do?cityID="+result.cityID;
				}else{
					alert("用户名或密码错误!");
					return;
				}
			});
		}
		
	</script>
</head>
<body>
	<div style="margin-top:30px;margin-right:30px; float:right">
        <a href="${pageContext.request.contextPath}/account/login_vistors.do" class="visitors">For Vistors</a>
    </div>
    <div class="login-main" >
        <h1>NING</h1>
        <h1>LABORATOTY</h1>
        <input type="text" id="username"><label for="username">ACCOUNT</label>
        <input type="text" id="password"><label for="password">PASSPOST</label>
    </div>
    <div class="login-btn" style="margin-top:5px">  
        <h1>login</h1>
        <a href="javascript:login()"></a>
    </div>
		
</body>
</html>