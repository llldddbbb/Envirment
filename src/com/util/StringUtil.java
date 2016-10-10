package com.util;

//判断字符串的工具类
public class StringUtil {
	public static boolean isNotEmpty(String str){
		if(str!=null&&!str.trim().equals("")){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isEmpty(String str){
		if(str==null||str.trim().equals("")){
			return true;
		}else{
			return false;
		}
	}
	
	//模糊查询需要添加%号
	public static String formatSQLlike(String str){
		return "%"+str+"%";
	}
}
