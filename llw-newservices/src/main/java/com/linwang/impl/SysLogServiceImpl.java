package com.linwang.impl;
import com.alibaba.dubbo.config.annotation.Service;
import com.linwang.api.ISysLogService;
import com.linwang.dao.DaoSupport;
import com.linwang.entity.SysLog;
import com.linwang.impl.base.BaseServiceImpl;

import javax.annotation.Resource;

@Service(version="1.0.0")
public class SysLogServiceImpl extends BaseServiceImpl<SysLog,java.lang.String,DaoSupport,java.lang.Integer> implements ISysLogService {
 
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	protected DaoSupport getDao() {
        return dao;
    }
	
	protected String getMapper() {
        return "SysLogMapper";
    }
}