package com.linwang.listener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.io.IOUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.linwang.uitls.StaticProp;

/**
 * 
* 类名称：WebAppContextListener.java
* 类描述： 
* 作者：wa
* 联系方式：
* @version 1.0
 */
public class WebAppContextListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent event) {
		// TODO Auto-generated method stub
	}

	public void contextInitialized(ServletContextEvent event) {
		StaticProp.SERVLET_CONTEXT = event.getServletContext();
		StaticProp.WEB_APP_CONTEXT = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
		//加载配置
		Properties properties = new Properties();
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(
            		this.getClass().getResourceAsStream("/conf/config.properties"), 
            		"UTF-8"));
            properties.load(in);
        } catch (Exception e) {
            throw new RuntimeException("找不到 config.properties 配置文件", e);
        } finally {
            IOUtils.closeQuietly(in);
        }
        for (Object key : properties.keySet()) {
        	StaticProp.sysConfig.put((String) key, evaluate((String) key, properties));
        }
        properties = null;
		System.out.println("========获取Spring WebApplicationContext");
	}
	
	private String evaluate(String key, Properties properties) {
        String value = (String) properties.get(key);
        if (value == null) {
            throw new RuntimeException("Key [" + key + "] is undefined");
        }

        value = value.trim();

        StringBuilder sb = new StringBuilder();

        int index = 0;
        int beginIndex;
        int endIndex;
        for (;;) {
            beginIndex = value.indexOf("${", index);
            endIndex = value.indexOf("}", beginIndex);
            if (beginIndex == -1 ||  endIndex == -1) {
                sb.append(value.substring(index));
                break;
            } else {
                sb.append(value.substring(index, beginIndex));
                sb.append(evaluate(value.substring(beginIndex + 2, endIndex), properties));
                index = endIndex + 1;
                if (index >= value.length()) {
                    break;
                }
            }
        }
        return sb.toString();
    }

}
