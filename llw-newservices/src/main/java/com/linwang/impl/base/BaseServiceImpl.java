package com.linwang.impl.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.linwang.dao.DAO;
import com.linwang.uitls.BeanConverter;
import com.linwang.uitls.Method;
import com.linwang.uitls.Page;
import com.linwang.uitls.PageUtil;
import com.linwang.uitls.web.DAOUtils;

public abstract class BaseServiceImpl<T,PKS extends Serializable,DaoSupport extends DAO<T>,PK extends Serializable> {

    protected abstract DaoSupport getDao();
    
    protected abstract PKS getMapper();

    public int deleteByPrimaryKey(PK id){
    	return getDao().delete(""+getMapper()+".deleteByPrimaryKey",id);
    }
//    
    public int delete(Object condition){
        Map<String, Object> map = Maps.newHashMap();
        if (condition != null) {
            map.putAll(BeanConverter.toMap(condition, false));
        }
        return getDao().delete(""+getMapper()+".delete",map);
    }

    public int insert(T record){
        return getDao().insert(""+getMapper()+".insert",record);
    }

    public int insertSelective(T record){
        return getDao().insert(""+getMapper()+".insertSelective",record);
    }

    public T selectByPrimaryKey(PK id){
        return (T)getDao().findForObject(""+getMapper()+".selectByPrimaryKey", id);
    }

    public T getByCondition(Object record){
        Map<String, Object> params = Maps.newHashMap();
        if (record != null) {
            params.putAll(BeanConverter.toMap(record, false));
        }
        return (T)getDao().findForObject(""+getMapper()+".getByCondition", params);
    }

    public int updateByPrimaryKey(Object record){
        return getDao().update(""+getMapper()+".updateByPrimaryKey",record);
    }
    
    public int updateByPrimaryKeySelective(Object record){
        return getDao().update(""+getMapper()+".updateByPrimaryKeySelective",record);
    }

    public int updateByCondition(Object record, Object condition){
        Map<String, Object> params = Maps.newHashMap();
        params.put("entity", record);
        if (condition != null) {
            Map<String, Object> paramsCondition = BeanConverter.toMap(condition, false);
            if (paramsCondition.containsKey("entity")) {
                throw new RuntimeException("the update entity bean has the same colum entity");
            }
            params.putAll(paramsCondition);
        }
        params.put("condition", condition);
        return getDao().update(""+getMapper()+".updateByCondition",params);
    }

    public int updateIncreateNumbers(Object condition, String[] colums, Object[] numbers){
        Map<String, Object> map = Maps.newHashMap();
        if (condition != null) {
            map.putAll(BeanConverter.toMap(condition, false));
        }
        
        if (colums == null || colums.length == 0 ||
                numbers == null || numbers.length == 0) {
            throw new RuntimeException("colums or numbers can not be empty!");
        }
        if (colums.length != numbers.length) {
            throw new RuntimeException("colums and numbers do not have the same size");
        }
        
        List<Map<String,Object>> increateNumbers = Lists.newArrayList();
        for (int i = 0; i < colums.length; i++) {
            Map<String,Object> columnNumber = Maps.newHashMap();
            DAOUtils.checkColumn(colums[i]);
            columnNumber.put("column", colums[i]);
            columnNumber.put("number", numbers[i]);
            increateNumbers.add(columnNumber);
        }
        map.put("increateNumbers", increateNumbers);
        
        return getDao().update(""+getMapper()+".updateIncreateNumbers",map);
    }

    public List<T> getList(){
        Map<String, Object> map = Maps.newHashMap();
        return (List<T>)getDao().findForList(""+getMapper()+".getList", map);
    }
    
    public List<T> getList(Object condition){
        Map<String, Object> map = Maps.newHashMap();
        if (condition != null) {
            map.putAll(BeanConverter.toMap(condition, false));
        }
        return (List<T>)getDao().findForList(""+getMapper()+".getList", map);
    }

    public int getCount(){
        Map<String, Object> map = Maps.newHashMap();
        List list=(List)getDao().findForList(""+getMapper()+".getCount", map);
        return list.size();
    }
    
    public int getCount(Object condition){
        Map<String, Object> map = Maps.newHashMap();
        if (condition != null) {
            map.putAll(BeanConverter.toMap(condition, false));
        }
        List list=(List)getDao().findForList(""+getMapper()+".getCount", map);
        return (Integer) list.get(0);
    }

    public Page<T> getPage(Object condition, Map<String, ?> paramsMap){
        return getPage(null, condition, paramsMap);
    }

    public Page<T> getPage(String url, Object condition, Map<String, ?> paramsMap){
        Map<String, Object> map = Maps.newHashMap();
        map.putAll(BeanConverter.toMap(condition, false));
        Integer pageNumber = (Integer) map.get("pageNumber");
        if (pageNumber == null) {
            throw new RuntimeException("Please Set the Condition's pageNumber Property");
        }
        Integer pageSize = (Integer) map.get("pageSize");
        if (pageSize == null) {
            throw new RuntimeException("Please Set the Condition's pageSize Property");
        }
        String params = Method.getQuery(paramsMap);// 取参数
        int totalRow = getCount(map);// 总记录数
        Page<T> page = new Page<T>(pageSize, url, params);
        page.setCurPage(pageNumber);
        page = PageUtil.createPage(page, totalRow);
        page.setResult(getList(condition));
        return page;
    }
    public JSONObject aggregateJson(Object condition, String[] functions, String[] columns){
    	Map<String, ?> map = aggregate(condition, functions, columns);
    	return (JSONObject) JSONObject.toJSON(map);
    }
    public Map<String, ?> aggregate(Object condition, String[] functions, String[] columns){
        Map<String, Object> map = Maps.newHashMap();
        if (condition != null) {
            map.putAll(BeanConverter.toMap(condition, false));
        }
        if (functions == null || functions.length == 0 ||
                columns == null || columns.length == 0) {
            throw new RuntimeException("functions or columns can not be empty!");
        }
        if (functions.length != columns.length) {
            throw new RuntimeException("functions and columns do not have the same size");
        }
        map.put("aggregate_sql", DAOUtils.buildAggregateSql(functions, columns));
        return (Map<String, ?>)getDao().findForList(""+getMapper()+".aggregate", map);
    }
}
