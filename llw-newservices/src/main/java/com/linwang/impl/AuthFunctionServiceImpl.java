package com.linwang.impl;
import com.alibaba.dubbo.config.annotation.Service;
import com.linwang.api.IAuthFunctionService;
import com.linwang.dao.DaoSupport;
import com.linwang.entity.AuthFunction;
import com.linwang.impl.base.BaseServiceImpl;

import javax.annotation.Resource;

@Service(version="1.0.0")
public class AuthFunctionServiceImpl extends BaseServiceImpl<AuthFunction,java.lang.String,DaoSupport,java.lang.Integer> implements IAuthFunctionService {
 
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	protected DaoSupport getDao() {
        return dao;
    }
	
	protected String getMapper() {
        return "AuthFunctionMapper";
    }
}