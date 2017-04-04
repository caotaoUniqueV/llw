package com.linwang.impl;
import com.alibaba.dubbo.config.annotation.Service;
import com.linwang.api.IUserShippingAddressService;
import com.linwang.dao.DaoSupport;
import com.linwang.entity.UserShippingAddress;
import com.linwang.impl.base.BaseServiceImpl;

import javax.annotation.Resource;

@Service(version="1.0.0")
public class UserShippingAddressServiceImpl extends BaseServiceImpl<UserShippingAddress,java.lang.String,DaoSupport<UserShippingAddress>,java.lang.Integer> implements IUserShippingAddressService {
 
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	protected DaoSupport getDao() {
        return dao;
    }
	
	protected String getMapper() {
        return "UserShippingAddressMapper";
    }
}