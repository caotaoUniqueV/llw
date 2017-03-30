package com.linwang.uitls;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.linwang.dto.TemplateDto;

public class GenerateUitl {
	private static DataSource dataSource;
	public static void main(String[] args) throws SQLException {
		List<String> list=allTable();
		for (int i = 0; i < list.size(); i++) {
			String string = list.get(i);
			GenerateUitl.run(string);
			System.out.println(" "); 
		}
	}
	
	//获取数据库连接
	public static Connection getDatabaseConnection() throws SQLException {
		if(dataSource == null){
			DriverManagerDataSource ds = new DriverManagerDataSource();
            ds.setDriverClassName("com.mysql.jdbc.Driver");
            ds.setUrl("jdbc:mysql://121.40.205.102:3307/ylm?useUnicode=true&characterEncoding=utf-8");
            ds.setUsername("root");
            ds.setPassword("zju123456");
			dataSource = ds;
		}
        return dataSource.getConnection();
    } 
	
	//获取所有表名
	public static List allTable() throws SQLException{
		List<String> list=Lists.newArrayList();
		Connection conn = getDatabaseConnection();
		ResultSet rs =null;
		PreparedStatement ps = null;
		try {
			conn.setAutoCommit(true);
			ps =  conn.prepareStatement("SELECT TABLE_NAME FROM information_schema.tables WHERE table_schema = 'ylm'");
			rs = ps.executeQuery();
			while(rs.next()){
				list.add(rs.getString("TABLE_NAME"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			 //finally block used to close resources
			  try{
		         if(rs!=null)
		            rs.close();
		      }catch(SQLException se2){
		      }// nothing we can do
		      try{
		         if(ps!=null)
		            ps.close();
		      }catch(SQLException se2){
		      }// nothing we can do
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		}
		return list;
	}
	
	//查询数据库
	public static void run(String tableName) throws SQLException{
		Connection conn = getDatabaseConnection();
		ResultSet rs =null;
		PreparedStatement ps = null;
		List<TemplateDto> templateDtos=Lists.newArrayList();
		Map map=Maps.newHashMap();
		try {
			String sql="";
			conn.setAutoCommit(true);
			ps =  conn.prepareStatement("SELECT TABLE_NAME,COLUMN_NAME,COLUMN_KEY,DATA_TYPE,COLUMN_TYPE,COLUMN_COMMENT "
					+ "FROM information_schema. COLUMNS WHERE table_schema = 'ylm' AND table_name = ?");
			ps.setString(1, tableName);
			rs = ps.executeQuery();
			while(rs.next()){
				 TemplateDto tmDto=new TemplateDto();
				 if(!"id".equals(rs.getString("COLUMN_NAME"))){
					 tmDto.setCapitalAttribute(StringUitl.replaceUnderlineAndfirstToUpper(StringUitl.captureName(rs.getString("COLUMN_NAME")),"_",""));
					 tmDto.setLowerAttribute(StringUitl.replaceUnderlineAndfirstToUpper(rs.getString("COLUMN_NAME"),"_",""));
					 tmDto.setType(rs.getString("DATA_TYPE"));
					 tmDto.setComment(rs.getString("COLUMN_COMMENT"));
					 tmDto.setColumnName(StringUitl.replaceUnderlineAndfirstToUpper(rs.getString("COLUMN_NAME"),"_",""));
					 tmDto.setOnColumnName(rs.getString("COLUMN_NAME"));
					 templateDtos.add(tmDto);
					 tmDto=null;
				 }
			}
			 map.put("mapperName",StringUitl.replaceUnderlineAndfirstToUpper(StringUitl.captureName(tableName),"_","")+"Mapper");
		     map.put("methodName", StringUitl.replaceUnderlineAndfirstToUpper(StringUitl.captureName(tableName),"_",""));
		     map.put("rpcService", StringUitl.replaceUnderlineAndfirstToUpper(tableName,"_",""));
		     map.put("IServices", "I"+StringUitl.replaceUnderlineAndfirstToUpper(StringUitl.captureName(tableName),"_","")+"Service");
		     map.put("services", StringUitl.replaceUnderlineAndfirstToUpper(tableName,"_","")+"Service");
		     map.put("servicesImpl", StringUitl.replaceUnderlineAndfirstToUpper(StringUitl.captureName(tableName),"_","")+"ServiceImpl");
		     map.put("tableName",tableName);
		     map.put("templateDtos", templateDtos);
		     FreeMarkerUitl.run(map);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			 //finally block used to close resources
			  try{
		         if(rs!=null)
		            rs.close();
		      }catch(SQLException se2){
		      }// nothing we can do
		      try{
		         if(ps!=null)
		            ps.close();
		      }catch(SQLException se2){
		      }// nothing we can do
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		}
	}
}
