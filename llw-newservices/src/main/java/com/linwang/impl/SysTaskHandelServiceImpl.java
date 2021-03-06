package com.linwang.impl;
import com.alibaba.dubbo.config.annotation.Service;
import com.linwang.api.ISysTaskHandelService;
import com.linwang.dao.DaoSupport;
import com.linwang.entity.SysTaskHandel;
import com.linwang.impl.base.BaseServiceImpl;

import javax.annotation.Resource;

@Service(version="1.0.0")
public class SysTaskHandelServiceImpl extends BaseServiceImpl<SysTaskHandel,java.lang.String,DaoSupport<SysTaskHandel>,java.lang.Integer> implements ISysTaskHandelService {
 
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	protected DaoSupport getDao() {
        return dao;
    }
	
	protected String getMapper() {
        return "SysTaskHandelMapper";
    }
}