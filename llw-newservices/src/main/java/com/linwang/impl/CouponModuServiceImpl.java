package com.linwang.impl;
import com.alibaba.dubbo.config.annotation.Service;
import com.linwang.api.ICouponModuService;
import com.linwang.dao.DaoSupport;
import com.linwang.entity.CouponModu;
import com.linwang.impl.base.BaseServiceImpl;

import javax.annotation.Resource;

@Service(version="1.0.0")
public class CouponModuServiceImpl extends BaseServiceImpl<CouponModu,java.lang.String,DaoSupport<CouponModu>,java.lang.Integer> implements ICouponModuService {
 
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	protected DaoSupport getDao() {
        return dao;
    }
	
	protected String getMapper() {
        return "CouponModuMapper";
    }
}