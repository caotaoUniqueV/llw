package com.linwang.uitls;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;

public class Method {
	/**
	 * @param params
	 * @说明 返回查询条件
	 */
	public static String getQuery(Map<String, ?> params){
		String query="";
		
		if(params==null)
			return query;
		
		Iterator<String> iter=params.keySet().iterator();
		while(iter.hasNext()){
			String key=iter.next();
		
			// 不对 PAGENAME 做参数拼接
			if(!key.equals("page")){
				String value=getArray(params, key,null);
				if(null!=value && !"".equals(value)){
					try {
						query+="&"+key+"="+java.net.URLEncoder.encode(value,"UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		if(query.length()>1)
			query=query.substring(1);
		
		return query;
	}
	/**
	 * 分页条使用，获取传递的参数信息
	 * @param params
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getArray(Map<String, ?> params, String key, 
			String defaultValue){
		if(params == null){
			return defaultValue;
		}
		if(params.get(key)==null)
			return defaultValue;
		Object o = params.get(key);
		try {
			String[] values = (String[])o;
			return values[0];
		} catch (Exception e) {
		}
		try{
			return String.valueOf(o);
		}catch(ClassCastException e){
			//log.error(e);
			return defaultValue;
		}
	}
}
