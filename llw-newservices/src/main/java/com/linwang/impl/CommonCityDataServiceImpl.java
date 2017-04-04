package com.linwang.impl;
import com.alibaba.dubbo.config.annotation.Service;
import com.linwang.api.ICommonCityDataService;
import com.linwang.dao.DaoSupport;
import com.linwang.entity.CommonCityData;
import com.linwang.impl.base.BaseServiceImpl;

import javax.annotation.Resource;

@Service(version="1.0.0")
public class CommonCityDataServiceImpl extends BaseServiceImpl<CommonCityData,java.lang.String,DaoSupport<CommonCityData>,java.lang.Integer> implements ICommonCityDataService {
 
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	protected DaoSupport getDao() {
        return dao;
    }
	
	protected String getMapper() {
        return "CommonCityDataMapper";
    }
}