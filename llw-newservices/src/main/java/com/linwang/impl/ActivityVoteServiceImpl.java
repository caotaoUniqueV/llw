package com.linwang.impl;
import com.alibaba.dubbo.config.annotation.Service;
import com.linwang.api.IActivityVoteService;
import com.linwang.dao.DaoSupport;
import com.linwang.entity.ActivityVote;
import com.linwang.impl.base.BaseServiceImpl;

import javax.annotation.Resource;

@Service(version="1.0.0")
public class ActivityVoteServiceImpl extends BaseServiceImpl<ActivityVote,java.lang.String,DaoSupport<ActivityVote>,java.lang.Integer> implements IActivityVoteService {
 
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	protected DaoSupport getDao() {
        return dao;
    }
	
	protected String getMapper() {
        return "ActivityVoteMapper";
    }
}