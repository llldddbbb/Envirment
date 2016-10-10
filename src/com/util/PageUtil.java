package com.util;

//�ù��������������ɷ�ҳ�����
public class PageUtil {

	/**
	 * ���ɷ�ҳ����
	 * @param targetUrl Ŀ���ַ
	 * @param totalNum �ܼ�¼��
	 * @param currentPage ��ǰҳ
	 * @param pageSize ÿҳ��С
	 * @return ��ҳ����
	 */
	public static String genPaginationNoParam(String targetUrl,long totalNum,int currentPage,int pageSize){
		long totalPage=totalNum%pageSize==0?totalNum/pageSize:totalNum/pageSize+1;
		if(totalPage==0){
			return "δ��ѯ������";
		}else{
			StringBuffer pageCode=new StringBuffer();
			pageCode.append("<li><a href='"+targetUrl+"&page=1'>��ҳ</a></li>");
			if(currentPage>1){
				pageCode.append("<li><a href='"+targetUrl+"&page="+(currentPage-1)+"'>��һҳ</a></li>");			
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
				pageCode.append("<li><a href='"+targetUrl+"&page="+(currentPage+1)+"'>��һҳ</a></li>");		
			}
			pageCode.append("<li><a href='"+targetUrl+"&page="+totalPage+"'>βҳ</a></li>");
			return pageCode.toString();
		}
	}
	
	
}
