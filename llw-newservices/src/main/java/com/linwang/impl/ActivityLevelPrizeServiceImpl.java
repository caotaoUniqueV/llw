package com.linwang.impl;
import com.alibaba.dubbo.config.annotation.Service;
import com.linwang.api.IActivityLevelPrizeService;
import com.linwang.dao.DaoSupport;
import com.linwang.entity.ActivityLevelPrize;
import com.linwang.impl.base.BaseServiceImpl;

import javax.annotation.Resource;

@Service(version="1.0.0")
public class ActivityLevelPrizeServiceImpl extends BaseServiceImpl<ActivityLevelPrize,java.lang.String,DaoSupport<ActivityLevelPrize>,java.lang.Integer> implements IActivityLevelPrizeService {
 
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	protected DaoSupport getDao() {
        return dao;
    }
	
	protected String getMapper() {
        return "ActivityLevelPrizeMapper";
    }
}