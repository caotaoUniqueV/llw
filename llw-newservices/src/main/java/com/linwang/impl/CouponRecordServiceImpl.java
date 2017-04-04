package com.linwang.impl;
import com.alibaba.dubbo.config.annotation.Service;
import com.linwang.api.ICouponRecordService;
import com.linwang.dao.DaoSupport;
import com.linwang.entity.CouponRecord;
import com.linwang.impl.base.BaseServiceImpl;

import javax.annotation.Resource;

@Service(version="1.0.0")
public class CouponRecordServiceImpl extends BaseServiceImpl<CouponRecord,java.lang.String,DaoSupport<CouponRecord>,java.lang.Integer> implements ICouponRecordService {
 
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	protected DaoSupport getDao() {
        return dao;
    }
	
	protected String getMapper() {
        return "CouponRecordMapper";
    }
}