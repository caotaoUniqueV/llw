package com.linwang.impl;
import com.alibaba.dubbo.config.annotation.Service;
import com.linwang.api.ISysLogSmsService;
import com.linwang.dao.DaoSupport;
import com.linwang.entity.SysLogSms;
import com.linwang.impl.base.BaseServiceImpl;

import javax.annotation.Resource;

@Service(version="1.0.0")
public class SysLogSmsServiceImpl extends BaseServiceImpl<SysLogSms,java.lang.String,DaoSupport<SysLogSms>,java.lang.Integer> implements ISysLogSmsService {
 
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	protected DaoSupport getDao() {
        return dao;
    }
	
	protected String getMapper() {
        return "SysLogSmsMapper";
    }
}