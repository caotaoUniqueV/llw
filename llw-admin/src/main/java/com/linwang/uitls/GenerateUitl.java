package com.linwang.uitls;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.linwang.dto.TemplateDto;
import com.linwang.entity.AuthFunction;
import com.linwang.entity.AuthRoleFunction;

public class GenerateUitl {
	private static DataSource dataSource;
	public static void main(String[] args) throws SQLException {
//		List<String> list=allTable();
//		for (int i = 0; i < list.size(); i++) {
//			String string = list.get(i);
			GenerateUitl.run("t_link","省市区管理",0,0);
//			System.out.println(" "); 
//		}
	}
	
	//获取数据库连接
	public static Connection getDatabaseConnection() throws SQLException {
		if(dataSource == null){
			DriverManagerDataSource ds = new DriverManagerDataSource();
            ds.setDriverClassName("com.mysql.jdbc.Driver");
            ds.setUrl("jdbc:mysql://127.0.0.1:3306/ylm?useUnicode=true&characterEncoding=utf-8");
            ds.setUsername("root");
            ds.setPassword("root");
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
	
	public static int saveAuthFunction(AuthFunction authFunction) throws SQLException{
		int id=0;
		Connection conn = getDatabaseConnection();
		PreparedStatement  stmt = null;
		try {
			StringBuilder sb=new StringBuilder();
			sb.append("INSERT INTO `ylm`.`auth_function` (");
			sb.append("`name`, `url`, `pid`, `level`, `paixu`, `is_menu`, `menu_pos_str`,`icon`)");
			sb.append(" VALUES ('"+authFunction.getName()+"','"+authFunction.getUrl()+"',"+authFunction.getPid()+", '0',"+authFunction.getPaixu()+", b'0', NULL, NULL)");
			stmt = conn.prepareStatement(sb.toString(),Statement.RETURN_GENERATED_KEYS);
			stmt.executeUpdate();
			ResultSet rs =stmt.getGeneratedKeys();
			if (rs.next()) { 
				id = rs.getInt(1); 
				System.out.println("数据主键：" + id); 
			} 
		}catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }finally{
		      //finally block used to close resources
		      try{
		         if(stmt!=null)
		            conn.close();
		      }catch(SQLException se){
		      }// do nothing
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		}
		return id;
	}
	
	public static void saveAuthRoleFunction(AuthRoleFunction authRoleFunction) throws SQLException{
		int id=0;
		Connection conn = getDatabaseConnection();
		java.sql.Statement stmt = null;
		try {
			stmt = conn.createStatement();
			StringBuilder sb=new StringBuilder();
			sb.append("INSERT INTO `ylm`.`auth_role_function` (`role_id`, `function_id`) ");
			sb.append(" VALUES ("+authRoleFunction.getRoleId()+","+authRoleFunction.getFunctionId()+");");
			stmt.executeUpdate(sb.toString());
		}catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }finally{
		      //finally block used to close resources
		      try{
		         if(stmt!=null)
		            conn.close();
		      }catch(SQLException se){
		      }// do nothing
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		}
	}
	
	//查询数据库
	public static void run(String tableName,String modular,int add,int del) throws SQLException{
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
		     map.put("modular",modular);
		     map.put("add",add);
		     map.put("del",del);
		     map.put("templateDtos", templateDtos);
		     FreeMarkerUitl.run(map);
		     
//		     List<Integer> actionIds=Lists.newArrayList();
//		     String str=StringUitl.replaceUnderlineAndfirstToUpper(tableName,"_","")+"Page";
//		     AuthFunction authFunction=new AuthFunction();
//		     authFunction.setUrl("/"+StringUitl.replaceUnderlineAndfirstToUpper(tableName,"_","")+"/"+str);
//		     authFunction.setName(modular);
//		     authFunction.setIsMenu(false);
//		     authFunction.setPaixu(1);
//		     authFunction.setPid(203);
//		     int pid=saveAuthFunction(authFunction);
//		     actionIds.add(pid);
//		     
//		     if(add==0){
//		    	 authFunction=new AuthFunction();
//			     authFunction.setUrl("/"+StringUitl.replaceUnderlineAndfirstToUpper(tableName,"_","")+"/add");
//			     authFunction.setName("添加/编辑");
//			     authFunction.setIsMenu(false);
//			     authFunction.setPaixu(1);
//			     authFunction.setPid(pid);
//			     int id=saveAuthFunction(authFunction);
//			     actionIds.add(id);
//			     
//			     authFunction=new AuthFunction();
//			     authFunction.setUrl("/"+StringUitl.replaceUnderlineAndfirstToUpper(tableName,"_","")+"/save");
//			     authFunction.setName("修改/保存");
//			     authFunction.setIsMenu(false);
//			     authFunction.setPaixu(1);
//			     authFunction.setPid(id);
//			     actionIds.add(saveAuthFunction(authFunction));
//		     }
//		     
//		     if(del==0){
//			     authFunction=new AuthFunction();
//			     authFunction.setUrl("/"+StringUitl.replaceUnderlineAndfirstToUpper(tableName,"_","")+"/del");
//			     authFunction.setName("删除");
//			     authFunction.setIsMenu(false);
//			     authFunction.setPaixu(1);
//			     authFunction.setPid(pid);
//			     actionIds.add(saveAuthFunction(authFunction));
//		     }
//		     
//		     for (Integer integer : actionIds) {
//		    	 AuthRoleFunction authRoleFunction=new AuthRoleFunction();
//			     authRoleFunction.setRoleId(1);
//			     authRoleFunction.setFunctionId(integer);
//		    	 saveAuthRoleFunction(authRoleFunction);
//		    	 authRoleFunction=null;
//			 }
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
