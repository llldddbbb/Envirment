package com.controller;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.entity.Account;
import com.service.AccountService;
import com.util.MD5Util;
import com.util.ResponseUtil;
import com.util.StringUtil;

@Controller
@RequestMapping("/account")//添加account地址映射
public class AccountController {
	
	@Resource
	private AccountService accountService;//动态注入账户业务处理类
	
	//登录验证
	@RequestMapping("/login")
	public String login(Account account,HttpServletRequest request,HttpServletResponse response)throws Exception{
		account.setPassword(MD5Util.getMD5(account.getPassword()));//MD5加密
		Account resultAccount=accountService.login(account);
		JSONObject result=new JSONObject();
		if(resultAccount==null){//为空则是数据库查找不到该账户，返回false，前台输出错误信息
			result.put("success", false);
		}else{
			HttpSession session=request.getSession();
			session.setAttribute("currentAccount", resultAccount);//将当前用户存进session
			
			Cookie cookie = new Cookie("account", resultAccount.getUsername()+"-"+resultAccount.getPassword());
			cookie.setMaxAge(resultAccount.getValidTime());//默认一星期
			cookie.setPath("/");
			response.addCookie(cookie);
			
			result.put("cityID", resultAccount.getPlace());//将cityID塞进request，用于跳转到列表界面
			result.put("success", true);
		}
		ResponseUtil.write(result, response);//将JSON信息写出界面
		return null;
	}
	
	//注销
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request,HttpServletResponse response){
		HttpSession session=request.getSession();
		String goLogin=((Account)session.getAttribute("currentAccount")).getUsername();
		if(StringUtil.isEmpty(goLogin)){
			goLogin="a.jsp";
		}else{
			goLogin=goLogin+".jsp";
		}
		session.invalidate();//清空Session
		HttpSession session2=request.getSession();
		session2.setAttribute("goLogin", goLogin);
		Cookie cookie=new Cookie("account",null);
		cookie.setPath("/");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		return "redirect:/login/"+goLogin;//重定向到登录界面
	}
	
	//游客登录操作
	@RequestMapping("/login_vistors")
	public String login_vistors(HttpServletRequest request){
		Account vistor=new Account();
		vistor.setPlace(2+"");
		HttpSession session=request.getSession();
		session.setAttribute("currentAccount", vistor);//将空的账户信息塞进session，模拟登录
		return "city/list.do?cityID=2";//请求list方法，加载列表界面
	}
	
	@RequestMapping("/checkLogin")
	public String checkLogin(HttpServletRequest request){
		HttpSession session=request.getSession();
		Cookie[] cookies=request.getCookies();
		String goLogin=(String)session.getAttribute("goLogin");
		if(StringUtil.isEmpty(goLogin)){
			goLogin="a.jsp";
		}
		if(cookies==null){
			return "redirect:/login/"+goLogin;
		}
		String userName = null;
		String password = null;
		for(Cookie c:cookies){
			if(c.getName().equals("account")){
				String userNameAndPassword=c.getValue();
				userName=userNameAndPassword.split("-")[0];
				password=userNameAndPassword.split("-")[1];
				break;
			}
		}
		if(userName==null||password==null){
			return "redirect:/login/"+goLogin;
		}
		Account account=new Account(userName,password);
		Account resultAccount=accountService.login(account);
		if(resultAccount==null){
			return "redirect:/login/"+goLogin;
		}
		session.setAttribute("currentAccount", resultAccount);
		return "redirect:/city/list.do?view=map&cityID="+resultAccount.getPlace();
	}
	
	
}
