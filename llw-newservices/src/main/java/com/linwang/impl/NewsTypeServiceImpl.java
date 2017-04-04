package com.linwang.impl;
import com.alibaba.dubbo.config.annotation.Service;
import com.linwang.api.INewsTypeService;
import com.linwang.dao.DaoSupport;
import com.linwang.entity.NewsType;
import com.linwang.impl.base.BaseServiceImpl;

import javax.annotation.Resource;

@Service(version="1.0.0")
public class NewsTypeServiceImpl extends BaseServiceImpl<NewsType,java.lang.String,DaoSupport<NewsType>,java.lang.Integer> implements INewsTypeService {
 
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	protected DaoSupport getDao() {
        return dao;
    }
	
	protected String getMapper() {
        return "NewsTypeMapper";
    }
}