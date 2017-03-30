package com.linwang.uitls;

import java.io.BufferedWriter;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

public class FreeMarkerUitl {
	/**
	 * 生成文件
	 */
	public static void createFile(Map map,String path,String outPath,String templlate,String name) throws Exception{
		/* 创建配置 */
        Configuration cfg = new Configuration();
        /* 指定模板存放的路径*/
        cfg.setDirectoryForTemplateLoading(new File(path));
        cfg.setObjectWrapper(new DefaultObjectWrapper());
        //加载并设置freemarker.properties  
//        Properties p = new Properties();  
//        FileInputStream in = new FileInputStream("D:/Users/Administrator/llw/llw-uitls/src/main/WEB-INF/macro/freemarker.properties");
//        p.load(in);  
//        cfg.setSettings(p);
        /* 从上面指定的模板目录中加载对应的模板文件*/
        Template temp = cfg.getTemplate(templlate); 
        
        File file=new File(outPath);
        if  (!file .exists()  && !file .isDirectory())      
        {       
            file .mkdir();    
        }
		Writer tempWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file+"/"+name)));
		temp.process(map, tempWriter);
		tempWriter.flush();  
	}
	
	/**
	 * 生成Mapper文件
	 * @param map
	 * @throws Exception 
	 */
	public static void run(Map map) throws Exception{
		String path=java.net.URLDecoder.decode(FreeMarkerUitl.class.getProtectionDomain().getCodeSource().getLocation().getPath(),"UTF-8");
		createFile(map,path+"com/linwang/template",path.replace("llw-admin","llw-dao").replace("target/classes/","")+"src/main/resources/mapper/ext/","ExtMapper.ftl",MapUtils.getString(map, "mapperName")+".xml");
		createFile(map,path+"com/linwang/template",path.replace("llw-admin","llw-dao").replace("target/classes/","")+"src/main/resources/mapper/base/","Mapper.ftl",MapUtils.getString(map, "mapperName")+".xml");
		System.out.println("--------------------------生成"+MapUtils.getString(map, "methodName")+"Mapper.xml文件成功--------------------------");
		
		createFile(map,path+"com/linwang/template",path.replace("llw-admin","llw-api").replace("target/classes/","")+"src/main/java/com/linwang/entity","JavaMapper.ftl",MapUtils.getString(map, "methodName")+".java");
		System.out.println("--------------------------生成"+MapUtils.getString(map, "methodName")+".java实体类文件成功--------------------------");
		
		createFile(map,path+"com/linwang/template",path.replace("llw-admin","llw-api").replace("target/classes/","")+"src/main/java/com/linwang/api","IServicesMapper.ftl",MapUtils.getString(map, "IServices")+".java");
		System.out.println("--------------------------生成I"+MapUtils.getString(map, "methodName")+"Service.java接口类文件成功--------------------------");
		
		createFile(map,path+"com/linwang/template",path.replace("llw-admin","llw-newservices").replace("target/classes/","")+"src/main/java/com/linwang/impl","ServicesImplMapper.ftl",MapUtils.getString(map, "servicesImpl")+".java");
		System.out.println("--------------------------生成"+MapUtils.getString(map, "methodName")+"ServiceImpl.java实现类文件成功--------------------------");
		
//		createFile(map,path+"com/linwang/template",path.replace("target/classes/","")+"src/main/java/com/linwang/rpc","ActionMapper.ftl",MapUtils.getString(map, "methodName")+"Rpc.java");
//		System.out.println("--------------------------生成"+MapUtils.getString(map, "methodName")+"Rpc.java实现类文件成功--------------------------");
	}
	
}
