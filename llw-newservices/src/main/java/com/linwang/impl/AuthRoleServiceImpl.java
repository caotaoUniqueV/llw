package com.linwang.impl;
import com.alibaba.dubbo.config.annotation.Service;
import com.linwang.api.IAuthRoleService;
import com.linwang.dao.DaoSupport;
import com.linwang.entity.AuthRole;
import com.linwang.impl.base.BaseServiceImpl;

import javax.annotation.Resource;

@Service(version="1.0.0")
public class AuthRoleServiceImpl extends BaseServiceImpl<AuthRole,java.lang.String,DaoSupport,java.lang.Integer> implements IAuthRoleService {
 
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	protected DaoSupport getDao() {
        return dao;
    }
	
	protected String getMapper() {
        return "AuthRoleMapper";
    }
}