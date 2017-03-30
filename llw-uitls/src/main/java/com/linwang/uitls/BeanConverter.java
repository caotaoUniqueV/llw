package com.linwang.uitls;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class BeanConverter {
	/**
	 * 将javaBean转换成Map
	 * 
	 * @param javaBean
	 *            javaBean
	 * @return Map对象
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> toMap(Object javaBean, boolean canNull) {
		
		if (javaBean instanceof Map) {
			return (Map<String, Object>) javaBean;
		}
		
		Map<String, Object> result = Maps.newHashMap();
		Method[] methods = javaBean.getClass().getMethods();

		for (Method method : methods) {
			try {
				if (method.getName().startsWith("get")) {
					String field = method.getName();
					field = field.substring(field.indexOf("get") + 3);
					field = field.toLowerCase().charAt(0) + field.substring(1);

					Object value = method.invoke(javaBean, (Object[]) null);
					if (value instanceof String) {
						if (Strings.isNullOrEmpty((String) value) && !canNull) {
							value = null;
						}
					}
					if (value == null) {
						if (canNull) {
							result.put(field, null);
						} else {
							// 不接收空值，直接跳过
							continue;
						}
					} else {
						result.put(field, value);
					}
				}
			} catch (Exception e) {
			}
		}

		return result;
	}

	/**
	 * 将json对象转换成Map
	 * 
	 * @param jsonObject
	 *            json对象
	 * @return Map对象
	 */
	public static Map<String, String> toMap(JSONObject jsonObject) {
		Map<String, String> result = new HashMap<String, String>();
		Set<String> keySets = jsonObject.keySet();
		Iterator<String> iterator = keySets.iterator();
		String key = null;
		String value = null;
		while (iterator.hasNext()) {
			key = iterator.next();
			value = jsonObject.getString(key);
			result.put(key, value);
		}
		return result;
	}

	/**
	 * 将map转换成Javabean
	 * 
	 * @param javabean
	 *            javaBean
	 * @param data
	 *            map数据
	 */
	public static Object toJavaBean(Object javabean, Map<String, String> data) {
		Method[] methods = javabean.getClass().getDeclaredMethods();
		for (Method method : methods) {
			try {
				if (method.getName().startsWith("set")) {
					String field = method.getName();
					field = field.substring(field.indexOf("set") + 3);
					field = field.toLowerCase().charAt(0) + field.substring(1);
					method.invoke(javabean, new Object[] { data.get(field) });
				}
			} catch (Exception e) {
			}
		}

		return javabean;
	}

	/**
	 * 将javaBean转换成JSONObject
	 * 
	 * @param bean
	 *            javaBean
	 * @return json对象
	 * @throws ParseException
	 *             json解析异常
	 */
	public static void toJavaBean(Object javabean, String data) throws ParseException {
		JSONObject jsonObject = JSONObject.parseObject(data);
		Map<String, String> datas = toMap(jsonObject);
		toJavaBean(javabean, datas);
	}
}
