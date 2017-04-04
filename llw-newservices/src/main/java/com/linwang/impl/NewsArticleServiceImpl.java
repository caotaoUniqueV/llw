package com.linwang.impl;
import com.alibaba.dubbo.config.annotation.Service;
import com.linwang.api.INewsArticleService;
import com.linwang.dao.DaoSupport;
import com.linwang.entity.NewsArticle;
import com.linwang.impl.base.BaseServiceImpl;

import javax.annotation.Resource;

@Service(version="1.0.0")
public class NewsArticleServiceImpl extends BaseServiceImpl<NewsArticle,java.lang.String,DaoSupport<NewsArticle>,java.lang.Integer> implements INewsArticleService {
 
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	protected DaoSupport getDao() {
        return dao;
    }
	
	protected String getMapper() {
        return "NewsArticleMapper";
    }
}