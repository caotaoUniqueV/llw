package com.linwang.impl;
import com.alibaba.dubbo.config.annotation.Service;
import com.linwang.api.ISysTaskLogService;
import com.linwang.dao.DaoSupport;
import com.linwang.entity.SysTaskLog;
import com.linwang.impl.base.BaseServiceImpl;

import javax.annotation.Resource;

@Service(version="1.0.0")
public class SysTaskLogServiceImpl extends BaseServiceImpl<SysTaskLog,java.lang.String,DaoSupport<SysTaskLog>,java.lang.Integer> implements ISysTaskLogService {
 
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	protected DaoSupport getDao() {
        return dao;
    }
	
	protected String getMapper() {
        return "SysTaskLogMapper";
    }
}