package com.linwang.uitls;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class ParamUtil {

	public ParamUtil() {
	}
	
	public static List<List<String>> formatModConfig(String str){
		String[] items=str.split(",");
		List<List<String>> configs=new ArrayList<List<String>>();
		for(int i=0;i<items.length;i++){
			List<String> l=new ArrayList<String>();
			for(String s:items[i].split(":")){
				l.add(s);
			}
			configs.add(l);
		}
		return configs;
	}
	
	
	public static List<String> getKeyByServletPath(String servletPath, String configDir){
		List<String> items=new ArrayList<String>();
		String[] sp_url=servletPath.split("/");
		String[] sp_dir=configDir.split("/");
		for(int i=0;i<sp_dir.length;i++){
			String d=sp_dir[i];
			if(-1 != d.indexOf("{") && -1 != d.indexOf("}")){
				items.add(sp_url[i]);
			}
		}
		return items;
	}
	
	/**
	 * 转换方法
	 * 
	 * @param aMask
	 * @param strDate
	 * @return
	 * @throws ParseException
	 */
	public static final Date convertStringToDate(String aMask, String strDate) {
		SimpleDateFormat format = null;
		Date date = null;
		format = new SimpleDateFormat(aMask);
		try {
			date = format.parse(strDate);
		} catch (ParseException e) {
			return null;
		}
		return (date);
	}

	/**
	 * 将TEXTAREA里面的内容转换成HTML格式
	 * 
	 * @param aMask
	 * @param strDate
	 * @return
	 * @throws ParseException
	 */
	public static String convertTextareaToHTML(String textarea) {
		String str2 = "";

		if (textarea == null) {
			return "";
		}
		textarea = textarea.trim();
		textarea = textarea.replaceAll("&", "&amp;");
		textarea = textarea.replaceAll("<", "&lt;");
		textarea = textarea.replaceAll(">", "&gt;");
		textarea = textarea.replaceAll(" ", "&nbsp;");
		StringTokenizer strToken = new StringTokenizer(textarea, "\r");
		while (strToken.hasMoreTokens()) {
			str2 = str2 + "<br>" + strToken.nextToken();
		}
		if (str2.indexOf("<br>") == 0) {
			str2 = str2.substring(4);
		}
		strToken = new StringTokenizer(str2, "\n");
		str2 = "";
		while (strToken.hasMoreTokens()) {
			str2 = str2 + "<br>" + strToken.nextToken();
		}
		if (str2.indexOf("<br>") == 0) {
			str2 = str2.substring(4);
		}
		strToken = new StringTokenizer(str2, "\r\n");
		str2 = "";
		while (strToken.hasMoreTokens()) {
			str2 = str2 + "<br>" + strToken.nextToken();
		}
		if (str2.indexOf("<br>") == 0) {
			str2 = str2.substring(4);
		}

		return str2;
	}

	/**
	 * 将TEXTAREA里面的内容转换成HTML格式，变成一行
	 * 
	 * @param aMask
	 * @param strDate
	 * @return
	 * @throws ParseException
	 */
	public static String convertTextareaToHTMLLess(String textarea) {
		String str2 = "";

		if (textarea == null) {
			return "";
		}
		textarea = textarea.trim();
		textarea = textarea.replaceAll("&", "");
		textarea = textarea.replaceAll("<", "");
		textarea = textarea.replaceAll(">", "");
		textarea = textarea.replaceAll(" ", " ");
		StringTokenizer strToken = new StringTokenizer(textarea, "\r");
		while (strToken.hasMoreTokens()) {
			str2 = str2 + "" + strToken.nextToken();
		}
		strToken = new StringTokenizer(str2, "\n");
		str2 = "";
		while (strToken.hasMoreTokens()) {
			str2 = str2 + "" + strToken.nextToken();
		}
		strToken = new StringTokenizer(str2, "\r\n");
		str2 = "";
		while (strToken.hasMoreTokens()) {
			str2 = str2 + "" + strToken.nextToken();
		}

		return str2;
	}

	/**
	 * 数组转换成以defaultParamName为分割符的字符串
	 * 
	 * @param request
	 * @param paramName
	 * @param defaultParamName
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String changeArrayToString(String[] values,
			String defaultParamName) throws UnsupportedEncodingException {
		String str = new String();
		for (int i = 0; i < values.length; i++) {
			str = str + (i == 0 ? "" : defaultParamName) + values[i];
		}
		return str;

	}

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @param scale
	 *            表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 数组转换成以defaultParamName为分割符的字符串用于Struts2
	 * 
	 * @param request
	 * @param paramName
	 * @param defaultParamName
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getArrayToString(String[] paramName,
			String defaultParamName) {
		String str = "";
		if (paramName != null) {
			for (int i = 0; i < paramName.length; i++) {
				try {
					str = str
							+ (i == 0 ? "" : defaultParamName)
							+ new String(paramName[i].getBytes("ISO-8859-1"),
									"GBK");
				} catch (UnsupportedEncodingException e) {
					return "";
				}
			}
		}
		return str;
	}
	
	/** QZY add Struts2 ParamUtil BEGIN **/
	
	public static Map<String, String[]> setString(Map<String, String[]> params, String key, String value){
		String[] str={value};
		params.put(key, str);
		return params;
	}
	
	public static String getString(Map<String, Object> params, String key){
		return getString(params, key, null);
	}
	
	public static String getString(Map<String, Object> params, String key, 
			String defaultValue){
		if(getArray(params, key)==null)
			return defaultValue;
		String temp = getArray(params, key);
		if(temp != null){
			temp = temp.replaceAll("%", "\\\\%");
			temp = temp.replaceAll("_", "\\\\_");
		}
		return temp;
	}
	
	public static Integer getInteger(Map<String, Object> params, String key){
		return getInteger(params, key, null);
	}
	
	public static Integer getInteger(Map<String, Object> params, String key, 
			Integer defaultValue){
		String value=getString(params, key);
		Integer i=defaultValue;
		i=defaultValue;
		try{
			i=new Integer(value);
		}catch(NumberFormatException e){
			//log.error(e);
		}
		return i;
	}
	
	public static Byte getByte(Map<String, Object> params, String key){
		return getByte(params, key, null);
	}
	
	public static Byte getByte(Map<String, Object> params, String key,
			Byte defaultValue){
		Byte b=defaultValue;
		String s=getString(params, key, null);
		if(s!=null){
			try{
				b=Byte.valueOf(s);
			}catch(NumberFormatException e){
				//log.error(e);
			}
		}
		return b;
	}
	
	public static Date getDate(Map<String, Object> params, String key){
		return getDate(params, key, null);
	}
	
	public static Date getDate(Map<String,Object> params, String key,
			Date defaultValue){
		Date date=null;
		String d=getString(params, key);
		if(d==null || "".equals(d)){
			date=defaultValue;
		}else{
			try{
				date=java.sql.Date.valueOf(d);
			}catch(Exception e){
				date=defaultValue;
			}
		}
		return date;
	}

	public static String getArray(Map<String, Object> params, String key){
		return getArray(params, key, null);
	}
	
	public static String getArray(Map<String, Object> params, String key, 
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
			String values=(String)o;
			return values;
		}catch(ClassCastException e){
			//log.error(e);
			return defaultValue;
		}
	}
	
	public static Float getFloat(Map<String, Object> params, String key){
		return getFloat(params, key, null);
	}
	
	public static Float getFloat(Map<String, Object> params, String key, Float defaultValue){
		String s=getString(params, key);
		Float f=null;
		if(s!=null){
			try{
				f=Float.valueOf(s);
			}catch(Exception e){
				//log.error(e);
				f=defaultValue;
			}
		}
		return f;
	}
	
	/**
	 * @author QZY
	 * @param params
	 * @return
	 * @说明 把 Map<String, String> 转为 Map<String, String[]>
	 */
	public static Map<String, String[]> convertParamsMap(Map<String, String> params){
		Map<String, String[]> map=new HashMap<String, String[]>();
		
		Iterator<String> iter=params.keySet().iterator();
		
		while(iter.hasNext()){
			String key=iter.next();
			String[] values={params.get(key)};
			map.put(key, values);
		}
		
		return map;
	}
}

