package com.linwang.uitls;

public class StringUitl {

	public static String captureName(String name) {
		char[] cs=name.toCharArray();
        cs[0]-=32;
        return String.valueOf(cs);
	}
	
	/** 
	* 替换字符串并让它的下一个字母为大写 
	* @param srcStr 
	* @param org 
	* @param ob 
	* @return 
	*/  
	public static String replaceUnderlineAndfirstToUpper(String srcStr,String org,String ob)  
	{  
	   String newString = "";  
	   int first=0;  
	   while(srcStr.indexOf(org)!=-1)  
	   {  
	    first=srcStr.indexOf(org);  
	    if(first!=srcStr.length())  
	    {  
	     newString=newString+srcStr.substring(0,first)+ob;  
	     srcStr=srcStr.substring(first+org.length(),srcStr.length());  
	     srcStr=captureName(srcStr);  
	    }  
	   }  
	   newString=newString+srcStr;  
	   return newString;  
	}  
}
