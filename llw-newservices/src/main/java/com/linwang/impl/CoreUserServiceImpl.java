package com.linwang.impl;
import com.alibaba.dubbo.config.annotation.Service;
import com.linwang.api.ICoreUserService;
import com.linwang.dao.DaoSupport;
import com.linwang.entity.CoreUser;
import com.linwang.impl.base.BaseServiceImpl;

import javax.annotation.Resource;

@Service(version="1.0.0")
public class CoreUserServiceImpl extends BaseServiceImpl<CoreUser,java.lang.String,DaoSupport<CoreUser>,java.lang.Integer> implements ICoreUserService {
 
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	protected DaoSupport getDao() {
        return dao;
    }
	
	protected String getMapper() {
        return "CoreUserMapper";
    }
}