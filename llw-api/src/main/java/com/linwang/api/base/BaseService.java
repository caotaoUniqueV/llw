package com.linwang.api.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.linwang.uitls.Page;

public interface BaseService<T,PK extends Serializable> {
	List<T> getList(Object condition);
    List<T> getList();
    int getCount();
    int getCount(Object condition);
    
    Page<T> getPage(Object condition, Map<String, ?> paramsMap);
    
    Page<T> getPage(String url, Object condition, Map<String, ?> paramsMap);
    
    abstract int deleteByPrimaryKey(PK id);
    int delete(Object condition);

    int insert(T record);

    int insertSelective(T record);

    T selectByPrimaryKey(PK id);
    
    T getByCondition(Object record);

    int updateByPrimaryKeySelective(Object record);

    int updateByPrimaryKey(Object record);
    
    int updateByCondition(Object record,Object condition);
    
    int updateIncreateNumbers(Object condition, String[] colums, Object[] numbers);
    
    JSONObject aggregateJson(Object condition, String[] functions, String[] columns);
    Map<String, ?> aggregate(Object condition, String[] functions, String[] columns);
}
