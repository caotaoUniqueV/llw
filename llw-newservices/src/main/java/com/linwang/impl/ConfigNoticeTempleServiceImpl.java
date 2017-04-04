package com.linwang.impl;
import com.alibaba.dubbo.config.annotation.Service;
import com.linwang.api.IConfigNoticeTempleService;
import com.linwang.dao.DaoSupport;
import com.linwang.entity.ConfigNoticeTemple;
import com.linwang.impl.base.BaseServiceImpl;

import javax.annotation.Resource;

@Service(version="1.0.0")
public class ConfigNoticeTempleServiceImpl extends BaseServiceImpl<ConfigNoticeTemple,java.lang.String,DaoSupport<ConfigNoticeTemple>,java.lang.Integer> implements IConfigNoticeTempleService {
 
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	protected DaoSupport getDao() {
        return dao;
    }
	
	protected String getMapper() {
        return "ConfigNoticeTempleMapper";
    }
}