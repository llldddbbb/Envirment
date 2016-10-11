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
@RequestMapping("/account")//���account��ַӳ��
public class AccountController {
	
	@Resource
	private AccountService accountService;//��̬ע���˻�ҵ������
	
	//��¼��֤
	@RequestMapping("/login")
	public String login(Account account,HttpServletRequest request,HttpServletResponse response)throws Exception{
		account.setPassword(MD5Util.getMD5(account.getPassword()));//MD5����
		Account resultAccount=accountService.login(account);
		JSONObject result=new JSONObject();
		if(resultAccount==null){//Ϊ���������ݿ���Ҳ������˻�������false��ǰ̨���������Ϣ
			result.put("success", false);
		}else{
			HttpSession session=request.getSession();
			session.setAttribute("currentAccount", resultAccount);//����ǰ�û����session
			
			Cookie cookie = new Cookie("account", resultAccount.getUsername()+"-"+resultAccount.getPassword());
			cookie.setMaxAge(resultAccount.getValidTime());//Ĭ��һ����
			cookie.setPath("/");
			response.addCookie(cookie);
			
			result.put("cityID", resultAccount.getPlace());//��cityID����request��������ת���б����
			result.put("success", true);
		}
		ResponseUtil.write(result, response);//��JSON��Ϣд������
		return null;
	}
	
	//ע��
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request,HttpServletResponse response){
		HttpSession session=request.getSession();
		String goLogin=((Account)session.getAttribute("currentAccount")).getUsername();
		if(StringUtil.isEmpty(goLogin)){
			goLogin="a.jsp";
		}else{
			goLogin=goLogin+".jsp";
		}
		session.invalidate();//���Session
		HttpSession session2=request.getSession();
		session2.setAttribute("goLogin", goLogin);
		Cookie cookie=new Cookie("account",null);
		cookie.setPath("/");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		return "redirect:/login/"+goLogin;//�ض��򵽵�¼����
	}
	
	//�ο͵�¼����
	@RequestMapping("/login_vistors")
	public String login_vistors(HttpServletRequest request){
		Account vistor=new Account();
		vistor.setPlace(2+"");
		HttpSession session=request.getSession();
		session.setAttribute("currentAccount", vistor);//���յ��˻���Ϣ����session��ģ���¼
		return "city/list.do?cityID=2";//����list�����������б����
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
