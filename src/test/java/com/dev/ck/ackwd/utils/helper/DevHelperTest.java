package com.dev.ck.ackwd.utils.helper;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import com.dev.ck.TestApp;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DevHelperTest extends TestApp{
	@Value("${common.jdbc.classname}") private String driver;
	@Value("${common.jdbc.url}") private String url;
	@Value("${common.jdbc.username}") private String user;
	@Value("${common.jdbc.password}") private String pass;
	private DevHelper help;
	
	
	@Before
	public void setting() {
		log.info("driver: " + driver + ", url: " + url + ", user: " + user + ", pass: " + pass);
		help = new DevHelper();
		help.setConnectInfo(driver, url, user, pass, "maria");
	}

	@Test
	public void DB테이블정보로_CRUD_SQL출력() {
		String targetTable = "SM_STD_CD";
		help.printAllSql(targetTable);
	}
	
	@Test
	public void DB테이블정보로_CRUD_SQL파일생성() {
		final String projectName = "/stock_manager";
		final String rootPath = "/com/dev/ck";
		final String rootMapperPath = "/sqlmap/stmg";
		
		String targetTable = "SM_STD_CD";
		String bizName = "/ackwd/code";
		String fileName = "SmStdCd";
		
		createAllFile(
			targetTable,
			projectName+"/src/main/java"+rootPath+bizName+"/"+fileName+"Controller.java",
			projectName+"/src/main/java"+rootPath+bizName+"/service/impl/"+fileName+"ServiceImpl.java",
			projectName+"/src/main/java"+rootPath+bizName+"/service/"+fileName+"Service.java",
			projectName+"/src/main/java"+rootPath+bizName+"/service/"+fileName+"Mapper.java",
			projectName+"/src/main/java"+rootPath+bizName+"/"+fileName+"Dto.java",
			projectName+"/src/main/resources"+rootMapperPath+"/"+fileName+"Mapper.xml"
		);
	}
	
	public void createAllFile(String tableName, String controllerName, String serviceImplName, String serviceName, String daoName, String dtoName, String xmlName){
		help.createAllFile(tableName, controllerName, serviceImplName, serviceName, daoName, dtoName, xmlName);
	}
}
