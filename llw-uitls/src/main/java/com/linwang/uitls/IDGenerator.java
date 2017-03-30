package com.linwang.uitls;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.google.common.base.Strings;
import com.linwang.uitls.idsquence.SequenceUtils;

/**
 * 主键ID生成规则
 * @author Administrator
 *
 */
public class IDGenerator {
	
	private static int maxLenOrder = 5;//订单号序号默认长度,6 为最大一天100万个订单
	private static int orderIdStep = 10;//订单序列号池子一次取几个ID
	
	private static Map<String,Integer> jinzhiToDic = new HashMap<String,Integer>();//进制转换对应表
	private static Map<Integer,String> dicToJinzhi = new HashMap<Integer,String>();//进制转换对应表
	private static String keyArry = "123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";//进制
	static{
		initMinUrlMem(keyArry);
	}
	/**
	* @Title: setMaxLenOrder
	* @Description: 订单号序号默认长度,6 为最大一天100万个订单，不够再增加
	* @param @param maxLenOrder    设定文件
	* @return void    返回类型
	* @throws
	 */
	public static void setMaxLenOrder(int maxLenOrder){
		IDGenerator.maxLenOrder = maxLenOrder;
	}
	/**
	* @Title: setOrderIdStep
	* @Description: 订单序列号池子一次取几个ID,默认一次取10个
	* @param @param orderIdStep    设定文件
	* @return void    返回类型
	* @throws
	 */
	public static void setOrderIdStep(int orderIdStep){
		IDGenerator.orderIdStep = orderIdStep;
	}
	public static int getOrderIdStep(){
		return IDGenerator.orderIdStep;
	}
	
	/**
	 * @throws SQLException 
	 * @throws NumberFormatException 
	* @Title: builderTradeNo
	* @Description: 生成唯一订单号
	* @param @param type 订单类型
	* @param @return    设定文件
	* @return long    返回类型
	* @throws
	 */
	public static String builderTradeNo(String name){
        String strDate = new SimpleDateFormat("yyMMdd").format(new Date());
        long zeroNum = 1;
        for(int i = 0;i<maxLenOrder;i++){
        	zeroNum *= 10;
        }
        try {
        	return name + "" + Long.parseLong(strDate) * zeroNum 
            		+ SequenceUtils.nextValue(name) % zeroNum;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	/**
	* @Title: builderTradeNoUnSync 
	* @Description: 获取唯一编号【高分布式环境下有重复可能性】
	* @author 龚国君 javazj@qq.com
	* @date 2016年4月21日 下午3:17:45 
	* @version V1.0   
	* @param @param name
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String builderTradeNoUnSync(String name){
		int hashCode = UUID.randomUUID().hashCode();
		hashCode = Math.abs(hashCode);
		String strDate = new SimpleDateFormat("yyMMdd").format(new Date());
		return Strings.nullToEmpty(name) + strDate + String.format("%010d", hashCode);
	}
	
	// 重新初始化短连接
	public static void initMinUrlMem (String keyArryNew) {
		keyArry = keyArryNew;
		jinzhiToDic.clear();
		dicToJinzhi.clear();
		for(int i = 0;i<10;i++){
			jinzhiToDic.put(String.valueOf(i),i);
			dicToJinzhi.put(i,String.valueOf(i));
		}
		for(int i = 10;i<=keyArry.length();i++){
			jinzhiToDic.put(String.valueOf(keyArry.charAt(i-1)), i);
			dicToJinzhi.put(i,String.valueOf(keyArry.charAt(i-1)));
		}
	}
	
	/**
	 * 获取最小ID
	 * 将数字转换为进制形式
	 * @return
	 */
	public static String minUrlId(Integer a){
		int jinzhi = jinzhiToDic.size()-1;
		StringBuffer hex = new StringBuffer();
		if(a == jinzhi){
			hex.insert(0, dicToJinzhi.get(jinzhi));
			a = 0;
		}
		while(a > jinzhi){
			hex.insert(0, dicToJinzhi.get(a%jinzhi));
			a = a/jinzhi;
		}
		if(a > 0){
			hex.insert(0, dicToJinzhi.get(a%jinzhi));
		}
		return hex.toString();
	}
	/**
	 * 获取UUID
	 * eg:	959b9e02-9c75-42bf-b5d9-e77c873aeb5e
	* @Title: uuid
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	 */
	public static String uuid(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
	
	private IDGenerator(){}
}

