package com.linwang.impl;
import com.alibaba.dubbo.config.annotation.Service;
import com.linwang.api.IAuthRoleFunctionService;
import com.linwang.dao.DaoSupport;
import com.linwang.entity.AuthRoleFunction;
import com.linwang.impl.base.BaseServiceImpl;

import javax.annotation.Resource;

@Service(version="1.0.0")
public class AuthRoleFunctionServiceImpl extends BaseServiceImpl<AuthRoleFunction,java.lang.String,DaoSupport,java.lang.Integer> implements IAuthRoleFunctionService {
 
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	protected DaoSupport getDao() {
        return dao;
    }
	
	protected String getMapper() {
        return "AuthRoleFunctionMapper";
    }
}