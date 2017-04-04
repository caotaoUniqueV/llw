package com.linwang.impl;
import com.alibaba.dubbo.config.annotation.Service;
import com.linwang.api.IAuthUserRoleService;
import com.linwang.dao.DaoSupport;
import com.linwang.entity.AuthUserRole;
import com.linwang.impl.base.BaseServiceImpl;

import javax.annotation.Resource;

@Service(version="1.0.0")
public class AuthUserRoleServiceImpl extends BaseServiceImpl<AuthUserRole,java.lang.String,DaoSupport<AuthUserRole>,java.lang.Integer> implements IAuthUserRoleService {
 
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	protected DaoSupport getDao() {
        return dao;
    }
	
	protected String getMapper() {
        return "AuthUserRoleMapper";
    }
}