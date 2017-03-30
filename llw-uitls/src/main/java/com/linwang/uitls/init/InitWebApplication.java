package com.linwang.uitls.init;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.linwang.uitls.StaticProp;
import com.linwang.uitls.idsquence.SequenceUtils;

@Component
public class InitWebApplication {
	private static final Logger LOGGER = LogManager.getLogger(InitWebApplication.class);
	
	@Autowired(required = false)
	private @Qualifier("dataSource")DataSource dataSource;
	@Autowired(required = false)
	private @Qualifier("dataSourceMaster")DataSource dataSourceMaster;
	
	@PostConstruct
	protected void initWeb(){
		if (dataSource != null) {
			SequenceUtils.setDataSource(dataSource);
		}
		if (dataSourceMaster != null) {
			SequenceUtils.setDataSource(dataSourceMaster);
		}
//		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
//		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
//		String upYunFold = StaticProp.sysConfig.get("upyun.fold");
//		if (!Strings.isNullOrEmpty(upYunFold)) {
//			upYunFold += "/";
//		}
//		StaticProp.upYunPath = upYunFold + format.format(new Date()) + "/";
		System.setProperty("org.freemarker.loggerLibrary", "none");
		try {
			//又拍云开关
//			String upyunOpen = StaticProp.sysConfig.get("upyun.open");
//			if(Strings.isNullOrEmpty(upyunOpen) || upyunOpen.equalsIgnoreCase("off")){
//				StaticProp.IS_USER_UPYUN = false;
//			}else{
//				StaticProp.UP_YUN = new UpYun(
//						StaticProp.sysConfig.get("upyun.bucketName"), 
//						StaticProp.sysConfig.get("upyun.userName"), 
//						StaticProp.sysConfig.get("upyun.password"));
//				StaticProp.UP_YUN.setApiDomain(UpYun.ED_AUTO);
//			}
			StaticProp.cookieID = StaticProp.sysConfig.get("cookie_id");
			StaticProp.SYSTEM = LogManager.getLogger("SYSLOG");
		} catch (Exception e) {
			LOGGER.error("读取sysConfig系统配置信息错误：",e);
		}
	}
}
