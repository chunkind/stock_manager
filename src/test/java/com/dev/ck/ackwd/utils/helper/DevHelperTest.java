package com.dev.ck.ackwd.utils.helper;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import com.dev.ck.TestApp;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DevHelperTest extends TestApp{
	@Value("${common.jdbc.classname}") String driver;
	@Value("${common.jdbc.url}") String url;
	@Value("${common.jdbc.username}") String user;
	@Value("${common.jdbc.password}") String pass;

	@Test
	public void DB정보로_CRUD_SQL출력() {
		log.info("driver: " + driver + ", url: " + url + ", user: " + user + ", pass: " + pass);
		
		String targetTable = "SM_STD_CD";
		
		DevHelper help = new DevHelper();
		help.setConnectInfo(driver, url, user, pass, "maria");
		
		help.printAllSql(targetTable);
	}
}
