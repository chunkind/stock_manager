package com.dev.ck.ackwd.utils.helper.connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.dev.ck.ackwd.utils.helper.model.TargetTableInfo;
import com.dev.ck.ackwd.utils.helper.util.DevHelperUtil;

public abstract class Connector {
	protected String driver;
	protected String url;
	protected String user;
	protected String pass;
	protected String space;
	
	public Connector(String driver, String url, String user, String pass){
		this.driver = driver;
		this.url = url;
		this.user = user;
		this.pass = pass;
		this.space = "\t";
	}
	
	public abstract String createTableDesc();
	public abstract String[] createSqlSelect(String tableName);
	
	public List<TargetTableInfo> getTableInfo(String tableName){
		List<TargetTableInfo> tableInfo = new ArrayList<TargetTableInfo>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pass);
			ps = conn.prepareStatement(createTableDesc());
			ps.setString(1, tableName);
			
			rs = ps.executeQuery();
			TargetTableInfo obj = null;
			while(rs.next()){
				obj = new TargetTableInfo();
				obj.setTableOwner(rs.getString("tableOwner"));
				obj.setTableName(rs.getString("tableName"));
				obj.setColumnType(rs.getString("columnType"));
				obj.setColumnName(rs.getString("columnName"));
				obj.setColumnComments(rs.getString("columnComments").replaceAll("\n", "").trim());
				tableInfo.add(obj);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(!conn.isClosed())
					conn.close();
				if(!ps.isClosed())
					ps.close();
				if(!rs.isClosed())
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return tableInfo;
	}
	
	public List<HashMap<String, String>> selectTargetTable(String tableName){
		List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
		String[] sqlResult = createSqlSelect(tableName);
		String sql = sqlResult[0];
		int columnSize = Integer.parseInt(sqlResult[1]);
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pass);
			ps = conn.prepareStatement(sql);
			System.out.println("sql: " + sql);
			rs = ps.executeQuery();
			
			while(rs.next()){
				for(int i=0; i<columnSize; i++) {
					System.out.print(rs.getString(i) + " ");
				}
				System.out.println();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(!conn.isClosed())
					conn.close();
				if(!ps.isClosed())
					ps.close();
				if(!rs.isClosed())
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public String createSqlCreate(List<TargetTableInfo> list){
		StringBuilder sb = new StringBuilder();
		sb.append(space+"CREATE TABLE " + list.get(0).getTableOwner() +"." + list.get(0).getTableName());
		DevHelperUtil.appendEnter(sb);
		for(int i=0; i<list.size(); i++){   
			if(i==0){
				sb.append(space+"( " + list.get(i).getColumnName() + " " + list.get(i).getColumnType() + " /*" + list.get(i).getColumnComments() + "*/");
			}else{
				sb.append(space+", " + list.get(i).getColumnName() + " " + list.get(i).getColumnType() + " /*" + list.get(i).getColumnComments() + "*/");
			}
			DevHelperUtil.appendEnter(sb);
		}
		sb.append(space+");");
		DevHelperUtil.appendEnter(sb);
		return sb.toString();
	}
	
	public String createSqlInsert(List<TargetTableInfo> list){
		StringBuilder sb = new StringBuilder();
		sb.append(space+"INSERT INTO " + list.get(0).getTableOwner() + "." + list.get(0).getTableName());
		DevHelperUtil.appendEnter(sb);
		for(int i=0; i<list.size(); i++){
			if(i==0){
				sb.append(space+"( " + list.get(i).getColumnName() + " /*" + list.get(i).getColumnComments() + "*/");
			}else{
				sb.append(space+", " + list.get(i).getColumnName() + " /*" + list.get(i).getColumnComments() + "*/");
			}
			DevHelperUtil.appendEnter(sb);
		}
		sb.append(space+") VALUES");
		DevHelperUtil.appendEnter(sb);
		for(int i=0; i<list.size(); i++){
			if(i==0){
				sb.append(space+"( #{" + DevHelperUtil.toCamelCase(list.get(i).getColumnName()) + "} /*" + list.get(i).getColumnComments() + "*/");
			}else{
				sb.append(space+", #{" + DevHelperUtil.toCamelCase(list.get(i).getColumnName()) + "} /*" + list.get(i).getColumnComments() + "*/");
			}
			DevHelperUtil.appendEnter(sb);
		}
		sb.append(space+")");
		DevHelperUtil.appendEnter(sb);
		return sb.toString();
	}
	
	public String createSqlSelect(List<TargetTableInfo> list, String alias){
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<list.size(); i++){
			if(i==0){
				sb.append(space+"SELECT " + alias + "." + list.get(i).getColumnName() + " AS " + DevHelperUtil.toCamelCase(list.get(i).getColumnName()) + " /*" + list.get(i).getColumnComments() + "*/");
			}else{
				sb.append(space+"\t, " + alias + "." + list.get(i).getColumnName() + " AS " + DevHelperUtil.toCamelCase(list.get(i).getColumnName()) + " /*" + list.get(i).getColumnComments() + "*/");
			}
			DevHelperUtil.appendEnter(sb);
		}
		sb.append(space+"  FROM " + list.get(0).getTableOwner() + "." + list.get(0).getTableName() + " " + alias);
		DevHelperUtil.appendEnter(sb);
		return sb.toString();
	}
	
	public String createSqlUpdate(List<TargetTableInfo> list){
		StringBuilder sb = new StringBuilder();
		sb.append(space+"UPDATE " + list.get(0).getTableOwner() + "." + list.get(0).getTableName());
		DevHelperUtil.appendEnter(sb);
		for(int i=0; i<list.size(); i++){
			if(i==0){
				sb.append(space+" SET " + list.get(i).getColumnName() + " = #{" + DevHelperUtil.toCamelCase(list.get(i).getColumnName()) + "} " + " /*" + list.get(i).getColumnComments() + "*/");
			}else{
				sb.append(space+"   , " + list.get(i).getColumnName() + " = #{" + DevHelperUtil.toCamelCase(list.get(i).getColumnName()) + "} " + " /*" + list.get(i).getColumnComments() + "*/");
			}
			DevHelperUtil.appendEnter(sb);
		}
		sb.append(space+" WHERE 1=1");
		DevHelperUtil.appendEnter(sb);
		for(int i=0; i<list.size(); i++){
			sb.append(space+"   AND " + list.get(i).getColumnName() + " = #{" + DevHelperUtil.toCamelCase(list.get(i).getColumnName()) + "} " + " /*" + list.get(i).getColumnComments() + "*/");
			DevHelperUtil.appendEnter(sb);
		}
		return sb.toString();
	}
	
	public String createSqlDelete(List<TargetTableInfo> list){
		StringBuilder sb = new StringBuilder();
		sb.append(space+"DELETE FROM " + list.get(0).getTableOwner() + "." +list.get(0).getTableName());
		DevHelperUtil.appendEnter(sb);
		return sb.toString();
	}
	
	public String createJavaFiled(List<TargetTableInfo> list, String className){
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<list.size(); i++){
			if(list.get(i).getColumnType().toUpperCase().contains("NUMBER")){
				sb.append(space+"private int " + DevHelperUtil.toCamelCase(list.get(i).getColumnName()) +  "; /*" + list.get(i).getColumnComments() +"*/");
			}else{
				sb.append(space+"private String " + DevHelperUtil.toCamelCase(list.get(i).getColumnName()) + "; /*" + list.get(i).getColumnComments() +"*/");
			}
			DevHelperUtil.appendEnter(sb);
		}
		return sb.toString();
	}
	
	public String createJavaConstructor(List<TargetTableInfo> list, String className){
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<list.size(); i++){
			if(i==0) {
				sb.append("\tpublic " + className + "( ");
			}
			if(i < list.size()-1) {
				if(list.get(i).getColumnType().toUpperCase().contains("NUMBER")){
					sb.append("int " + DevHelperUtil.toCamelCase(list.get(i).getColumnName()) + ", ");
				}else{
					sb.append("String " + DevHelperUtil.toCamelCase(list.get(i).getColumnName() + ", "));
				}
			}else {
				if(list.get(i).getColumnType().toUpperCase().contains("NUMBER")){
					sb.append("int " + DevHelperUtil.toCamelCase(list.get(i).getColumnName()) + "){");
				}else{
					sb.append("String " + DevHelperUtil.toCamelCase(list.get(i).getColumnName() + "){"));
				}
			}
			
		}
		sb.append("\n\t\tsuper();\n");
		
		for(int i=0; i<list.size(); i++){
			if(list.get(i).getColumnType().toUpperCase().contains("NUMBER")){
				sb.append("\t\tthis." + DevHelperUtil.toCamelCase(list.get(i).getColumnName()) + " = " + DevHelperUtil.toCamelCase(list.get(i).getColumnName()) + "; /*" + list.get(i).getColumnComments() +"*/");
			}else{
				sb.append("\t\tthis." + DevHelperUtil.toCamelCase(list.get(i).getColumnName()) + " = " + DevHelperUtil.toCamelCase(list.get(i).getColumnName()) + "; /*" + list.get(i).getColumnComments() +"*/");
			}
			DevHelperUtil.appendEnter(sb);
		}
		sb.append("\t}");
		return sb.toString();
	}
}
