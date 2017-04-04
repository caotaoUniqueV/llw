package com.linwang.impl;
import com.alibaba.dubbo.config.annotation.Service;
import com.linwang.api.ISysConfigService;
import com.linwang.dao.DaoSupport;
import com.linwang.entity.SysConfig;
import com.linwang.impl.base.BaseServiceImpl;

import javax.annotation.Resource;

@Service(version="1.0.0")
public class SysConfigServiceImpl extends BaseServiceImpl<SysConfig,java.lang.String,DaoSupport<SysConfig>,java.lang.Integer> implements ISysConfigService {
 
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	protected DaoSupport getDao() {
        return dao;
    }
	
	protected String getMapper() {
        return "SysConfigMapper";
    }
}