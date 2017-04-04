package com.linwang.impl;
import com.alibaba.dubbo.config.annotation.Service;
import com.linwang.api.ITLinkService;
import com.linwang.dao.DaoSupport;
import com.linwang.entity.TLink;
import com.linwang.impl.base.BaseServiceImpl;

import javax.annotation.Resource;

@Service(version="1.0.0")
public class TLinkServiceImpl extends BaseServiceImpl<TLink,java.lang.String,DaoSupport<TLink>,java.lang.Integer> implements ITLinkService {
 
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	protected DaoSupport getDao() {
        return dao;
    }
	
	protected String getMapper() {
        return "TLinkMapper";
    }
}