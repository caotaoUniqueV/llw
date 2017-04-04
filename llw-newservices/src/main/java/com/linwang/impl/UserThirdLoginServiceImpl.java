package com.linwang.impl;
import com.alibaba.dubbo.config.annotation.Service;
import com.linwang.api.IUserThirdLoginService;
import com.linwang.dao.DaoSupport;
import com.linwang.entity.UserThirdLogin;
import com.linwang.impl.base.BaseServiceImpl;

import javax.annotation.Resource;

@Service(version="1.0.0")
public class UserThirdLoginServiceImpl extends BaseServiceImpl<UserThirdLogin,java.lang.String,DaoSupport<UserThirdLogin>,java.lang.Integer> implements IUserThirdLoginService {
 
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	protected DaoSupport getDao() {
        return dao;
    }
	
	protected String getMapper() {
        return "UserThirdLoginMapper";
    }
}