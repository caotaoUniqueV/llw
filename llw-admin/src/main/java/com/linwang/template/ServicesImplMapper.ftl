package com.linwang.impl;
import com.alibaba.dubbo.config.annotation.Service;
import com.linwang.api.${IServices};
import com.linwang.dao.DaoSupport;
import com.linwang.entity.${methodName};
import com.linwang.impl.base.BaseServiceImpl;

import javax.annotation.Resource;

@Service(version="1.0.0")
public class ${servicesImpl} extends BaseServiceImpl<${methodName},java.lang.String,DaoSupport,java.lang.Integer> implements ${IServices} {
 
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	protected DaoSupport getDao() {
        return dao;
    }
	
	protected String getMapper() {
        return "${methodName}Mapper";
    }
}