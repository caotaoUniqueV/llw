package com.linwang.impl;
import com.alibaba.dubbo.config.annotation.Service;
import com.linwang.api.IAuthUserService;
import com.linwang.dao.DaoSupport;
import com.linwang.entity.AuthUser;
import com.linwang.impl.base.BaseServiceImpl;

import javax.annotation.Resource;

@Service(version="1.0.0")
public class AuthUserServiceImpl extends BaseServiceImpl<AuthUser,java.lang.String,DaoSupport<AuthUser>,java.lang.Integer> implements IAuthUserService {
 
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	protected DaoSupport getDao() {
        return dao;
    }
	
	protected String getMapper() {
        return "AuthUserMapper";
    }
}