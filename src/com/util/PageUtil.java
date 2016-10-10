package com.util;

//该工具类是用来生成分页代码的
public class PageUtil {

	/**
	 * 生成分页代码
	 * @param targetUrl 目标地址
	 * @param totalNum 总记录数
	 * @param currentPage 当前页
	 * @param pageSize 每页大小
	 * @return 分页代码
	 */
	public static String genPaginationNoParam(String targetUrl,long totalNum,int currentPage,int pageSize){
		long totalPage=totalNum%pageSize==0?totalNum/pageSize:totalNum/pageSize+1;
		if(totalPage==0){
			return "未查询到数据";
		}else{
			StringBuffer pageCode=new StringBuffer();
			pageCode.append("<li><a href='"+targetUrl+"&page=1'>首页</a></li>");
			if(currentPage>1){
				pageCode.append("<li><a href='"+targetUrl+"&page="+(currentPage-1)+"'>上一页</a></li>");			
			}
			for(int i=currentPage-4;i<=currentPage+4;i++){
				if(i<1||i>totalPage){
					continue;
				}
				if(i==currentPage){
					pageCode.append("<li class='active'><a href='"+targetUrl+"&page="+i+"'>"+i+"</a></li>");		
				}else{
					pageCode.append("<li><a href='"+targetUrl+"&page="+i+"'>"+i+"</a></li>");	
				}
			}
			if(currentPage<totalPage){
				pageCode.append("<li><a href='"+targetUrl+"&page="+(currentPage+1)+"'>下一页</a></li>");		
			}
			pageCode.append("<li><a href='"+targetUrl+"&page="+totalPage+"'>尾页</a></li>");
			return pageCode.toString();
		}
	}
	
	
}
