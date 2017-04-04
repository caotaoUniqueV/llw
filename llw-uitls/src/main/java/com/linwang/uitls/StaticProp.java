package com.linwang.uitls;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletContext;
import javax.sql.DataSource;

import main.java.com.UpYun;

import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.google.common.collect.Maps;

public class StaticProp {
	public static Map<String,String> sysConfig = Maps.newHashMap();//系统设置
	public static ServletContext SERVLET_CONTEXT;//获取根目录用
	public static ApplicationContext WEB_APP_CONTEXT;
	private static DataSource dataSource;
	public static Logger SYSTEM;
	public static ExecutorService execute = Executors.newFixedThreadPool(20);//开启最多 10 个线程运行的线程池
	
	// 文件允许格式
	public static String[] allowFiles = {
				".rar",
				".doc",
				".docx",
				".zip",
				".pdf",
				".txt",
				".swf",
				".wmv",
				".gif",
				".png",
				".jpg",
				".jpeg",
				".bmp",
				"xls",
				"xlsx"
	};
	public static UpYun UP_YUN;
	public static String upYunPath;
	
	public static boolean IS_USER_UPYUN = true;
	public static String cookieID;//身份认证cookie标志ID
	
	public static Connection getDatabaseConnection() throws SQLException {
		if(dataSource == null){
			DriverManagerDataSource ds = new DriverManagerDataSource();
            ds.setDriverClassName("com.mysql.jdbc.Driver");
            ds.setUrl(sysConfig.get("jdbc.url"));
            ds.setUsername(sysConfig.get("jdbc.username"));
            ds.setPassword(sysConfig.get("jdbc.password"));
			dataSource = ds;
		}
        return dataSource.getConnection();
    }
}
