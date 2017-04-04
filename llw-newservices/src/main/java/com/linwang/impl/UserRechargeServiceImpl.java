package com.linwang.impl;
import com.alibaba.dubbo.config.annotation.Service;
import com.linwang.api.IUserRechargeService;
import com.linwang.dao.DaoSupport;
import com.linwang.entity.UserRecharge;
import com.linwang.impl.base.BaseServiceImpl;

import javax.annotation.Resource;

@Service(version="1.0.0")
public class UserRechargeServiceImpl extends BaseServiceImpl<UserRecharge,java.lang.String,DaoSupport<UserRecharge>,java.lang.Integer> implements IUserRechargeService {
 
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	protected DaoSupport getDao() {
        return dao;
    }
	
	protected String getMapper() {
        return "UserRechargeMapper";
    }
}