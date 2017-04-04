package com.linwang.impl;
import com.alibaba.dubbo.config.annotation.Service;
import com.linwang.api.IActivityInfoService;
import com.linwang.dao.DaoSupport;
import com.linwang.entity.ActivityInfo;
import com.linwang.impl.base.BaseServiceImpl;

import javax.annotation.Resource;

@Service(version="1.0.0")
public class ActivityInfoServiceImpl extends BaseServiceImpl<ActivityInfo,java.lang.String,DaoSupport<ActivityInfo>,java.lang.Integer> implements IActivityInfoService {
 
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	protected DaoSupport getDao() {
        return dao;
    }
	
	protected String getMapper() {
        return "ActivityInfoMapper";
    }
}