package com.dev.ck.ackwd.utils.helper;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

import com.dev.ck.ackwd.utils.helper.connector.Connector;
import com.dev.ck.ackwd.utils.helper.connector.impl.MariaDbConnector;
import com.dev.ck.ackwd.utils.helper.connector.impl.OracleDbConnector;
import com.dev.ck.ackwd.utils.helper.model.TargetTableInfo;
import com.dev.ck.ackwd.utils.helper.temp.BasicTemplate;
import com.dev.ck.ackwd.utils.helper.util.DevHelperUtil;

public class DevHelper {
	//테스트 서버 DB 정보
	public String driver = "";
	public String url = "";
	public String user = "";
	public String pass = "";
	public Connector odb = null;
	
	public void setConnectInfo(String driver, String url, String user, String pass, String type) {
		this.driver = driver;
		this.url = url;
		this.user = user;
		this.pass = pass;
		if(type.equals("oracle")) {
			this.odb = new OracleDbConnector(driver, url, user, pass);
		}else {
			this.odb = new MariaDbConnector(driver, url, user, pass);
		}
	}
	
	public void printAllSql(String tableName){
		String alias = "A";
		String className = DevHelperUtil.toPascalCase(tableName) + "Dto";
		
		List<TargetTableInfo> list = odb.getTableInfo(tableName);
		String createSql = odb.createSqlCreate(list);
		String insertSql = odb.createSqlInsert(list);
		String selectSql = odb.createSqlSelect(list, alias);
		String updateSql = odb.createSqlUpdate(list);
		String deleteSql = odb.createSqlDelete(list);
		String vo = odb.createJavaFiled(list, className);
		
		System.out.println("==createSqlCreate================================================================");
		System.out.println(createSql);
		System.out.println("==createSqlInsert================================================================");
		System.out.println(insertSql);
		System.out.println("==createSqlSelect================================================================");
		System.out.println(selectSql);
		System.out.println("==createSqlUpdate================================================================");
		System.out.println(updateSql);
		System.out.println("==createSqlDelete================================================================");
		System.out.println(deleteSql);
		System.out.println("==createJavaFiled================================================================");
		System.out.println(vo);
		System.out.println("=============================================================================");
	}

	public void createAllFile(String tableName, String controllerName, String serviceImplName, String serviceName, String mapperName, String dtoName, String xmlName) {
		String dtoPackPath = DevHelperUtil.getPackageName(dtoName);
		String dtoPath = dtoName.substring(dtoName.lastIndexOf("/")+1);
		String dtoClazzName = dtoPath.split("\\.")[0];
		String dtoImportPath = dtoPackPath+"."+dtoClazzName;
		
		String mapperPackPath = DevHelperUtil.getPackageName(mapperName);
		String mapperPath = mapperName.substring(mapperName.lastIndexOf("/")+1);
		String mapperClazzName = mapperPath.split("\\.")[0];
		String mapperImportPath = mapperPackPath+"."+mapperClazzName;
		
		String servicePackPath = DevHelperUtil.getPackageName(serviceName);
		String servicePath = serviceName.substring(serviceName.lastIndexOf("/")+1);
		String serviceClazzName = servicePath.split("\\.")[0];
		String serviceImportPath = servicePackPath+"."+serviceClazzName;
		
		createXmlFile(tableName, xmlName);
		createDtoFile(tableName, dtoName);
		createMapperFile(tableName, mapperName, dtoImportPath, dtoClazzName);
		createServiceFile(tableName, serviceName, dtoImportPath, dtoClazzName);
		createServiceImplFile(tableName, serviceImplName, dtoImportPath, dtoClazzName
			, serviceImportPath, serviceClazzName, mapperImportPath, mapperClazzName);
		createControllerFile(tableName, controllerName, serviceImplName, dtoImportPath
			, dtoClazzName , serviceImportPath, serviceClazzName, mapperImportPath, mapperClazzName);
	}
	
	public void createXmlFile(String tableName, String xmlName) {
		String alias = "A";
		String template = BasicTemplate.getBasicXmlTemplate();
		String nameSpace = "";
		String resultType = "";
		
		List<TargetTableInfo> list = odb.getTableInfo(tableName);
		String insertSql = odb.createSqlInsert(list);
		String selectSql = odb.createSqlSelect(list, alias);
		String updateSql = odb.createSqlUpdate(list);
		String deleteSql = odb.createSqlDelete(list);
		
		String text = MessageFormat.format(
			template,
			nameSpace,
			"insert" + DevHelperUtil.toPascalCase(tableName), resultType,
			nameSpace + ".insert" + DevHelperUtil.toPascalCase(tableName) + " - ",
			insertSql,
			"select" + DevHelperUtil.toPascalCase(tableName), resultType,
			nameSpace + ".select" + DevHelperUtil.toPascalCase(tableName) + " - ",
			selectSql,
			"update" + DevHelperUtil.toPascalCase(tableName), resultType,
			nameSpace + ".update" + DevHelperUtil.toPascalCase(tableName) + " - ",
			updateSql,
			"delete" + DevHelperUtil.toPascalCase(tableName), resultType,
			nameSpace + ".delete" + DevHelperUtil.toPascalCase(tableName) + " - ",
			deleteSql
		);
		DevHelperUtil.createFile(xmlName, text);
	}
	
	public void createDtoFile(String tableName, String dtoName) {
		String fileName = dtoName.substring(dtoName.lastIndexOf("/")+1);
		String clazzName = fileName.split("\\.")[0];
		String template = BasicTemplate.getBasicDtoTemplate();
		String packageName = DevHelperUtil.getPackageName(dtoName);
		
		List<TargetTableInfo> list = odb.getTableInfo(tableName);
		
		String filed = odb.createJavaFiled(list, clazzName);
		String constructors = odb.createJavaConstructor(list, clazzName);
		
		String text = MessageFormat.format(
			template,
			packageName,
			clazzName + " {\n"
			+ filed + "\n"
			+ "\t@Builder\n"
			+ constructors + "\n" 
			+ "}"
		);
		
		DevHelperUtil.createFile(dtoName, text);
	}
	
	public void createMapperFile(String tableName, String mapperName, String dtoImportPath, String dtoClazzName) {
		String fileName = mapperName.substring(mapperName.lastIndexOf("/")+1);
		String clazzName = fileName.split("\\.")[0];
		String template = BasicTemplate.getBasicMapperTemplate();
		String packageName = DevHelperUtil.getPackageName(mapperName);
		
		String mapperCode = "";
		mapperCode += clazzName + " {\n";
		mapperCode += "\tint insert"+dtoClazzName+"One("+dtoClazzName +" "+ DevHelperUtil.toCamelCase(dtoClazzName)+");\n";
		mapperCode += "\tint update"+dtoClazzName+"One("+dtoClazzName +" "+ DevHelperUtil.toCamelCase(dtoClazzName)+");\n";
		mapperCode += "\tint delete"+dtoClazzName+"One("+dtoClazzName +" "+ DevHelperUtil.toCamelCase(dtoClazzName)+");\n";
		mapperCode += "\t"+dtoClazzName+" select"+dtoClazzName+"One("+dtoClazzName +" "+ DevHelperUtil.toCamelCase(dtoClazzName)+");\n";
		mapperCode += "\tList<"+dtoClazzName+"> select"+dtoClazzName+"List("+dtoClazzName +" "+ DevHelperUtil.toCamelCase(dtoClazzName)+");\n";
		mapperCode += "}";
		
		String text = MessageFormat.format(
			template,
			packageName,
			dtoImportPath,
			mapperCode
		);
		
		DevHelperUtil.createFile(mapperName, text);
	}
	
//	public void createDaoFile(String tableName, String daoName) {
//		String fileName = daoName.substring(daoName.lastIndexOf("/")+1);
//		String clazzName = fileName.split("\\.")[0];
//		String packageName = "";
//		String template = BasicTemplate.getBasicDaoTemplate();
//		
//		List<TargetTableInfo> list = odb.getTableInfo(tableName);
//		
//		String voCode = odb.createJavaFiled(list, fileName);
//		
//		String text = MessageFormat.format(
//			template,
//			packageName,
//			fileName,
//			voCode
//		);
//		
//		DevHelperUtil.createFile(daoName, text);
//	}
	
	public void createServiceFile(String tableName, String serviceName, String dtoImportPath, String dtoClazzName) {
		String fileName = serviceName.substring(serviceName.lastIndexOf("/")+1);
		String clazzName = fileName.split("\\.")[0];
		String template = BasicTemplate.getBasicServiceTemplate();
		String packageName = DevHelperUtil.getPackageName(serviceName);
		
		String serviceCode = "";
		serviceCode += clazzName + " {\n";
		serviceCode += "\tint insert"+dtoClazzName+"One("+dtoClazzName +" "+ DevHelperUtil.toCamelCase(dtoClazzName)+");\n";
		serviceCode += "\tint update"+dtoClazzName+"One("+dtoClazzName +" "+ DevHelperUtil.toCamelCase(dtoClazzName)+");\n";
		serviceCode += "\tint delete"+dtoClazzName+"One("+dtoClazzName +" "+ DevHelperUtil.toCamelCase(dtoClazzName)+");\n";
		serviceCode += "\t"+dtoClazzName+" select"+dtoClazzName+"One("+dtoClazzName +" "+ DevHelperUtil.toCamelCase(dtoClazzName)+");\n";
		serviceCode += "\tList<"+dtoClazzName+"> select"+dtoClazzName+"List("+dtoClazzName +" "+ DevHelperUtil.toCamelCase(dtoClazzName)+");\n";
		serviceCode += "}";
		
		String text = MessageFormat.format(
			template,
			packageName,
			dtoImportPath,
			serviceCode
		);
		
		DevHelperUtil.createFile(serviceName, text);
	}
	
	public void createServiceImplFile(String tableName, String serviceImplName, String dtoImportPath, String dtoClazzName, String serviceImportPath, String serviceClazzName, String mapperImportPath, String mapperClazzName) {
		String fileName = serviceImplName.substring(serviceImplName.lastIndexOf("/")+1);
		String clazzName = fileName.split("\\.")[0];
		String template = BasicTemplate.getBasicServiceImplTemplate();
		String packageName = DevHelperUtil.getPackageName(serviceImplName);
		
		String serviceImpleCode = "";
		serviceImpleCode += clazzName + " implements "+serviceClazzName+"{\n";
		serviceImpleCode += "\t@Autowired\n"; 
		serviceImpleCode += "\tpublic "+mapperClazzName + " " + DevHelperUtil.toCamelCase(mapperClazzName) +";\n\n"; 
		
		serviceImpleCode += "\tpublic int insert"+dtoClazzName+"One("+dtoClazzName +" "+ DevHelperUtil.toCamelCase(dtoClazzName)+"){";
		serviceImpleCode += "\n\t\treturn "+DevHelperUtil.toCamelCase(mapperClazzName) + ".insert"+dtoClazzName+"One("+DevHelperUtil.toCamelCase(dtoClazzName)+");\n\t}\n\n";
		
		serviceImpleCode += "\tpublic int update"+dtoClazzName+"One("+dtoClazzName +" "+ DevHelperUtil.toCamelCase(dtoClazzName)+"){";
		serviceImpleCode += "\n\t\treturn "+DevHelperUtil.toCamelCase(mapperClazzName) + ".update"+dtoClazzName+"One("+DevHelperUtil.toCamelCase(dtoClazzName)+");\n\t}\n\n";
		
		serviceImpleCode += "\tpublic int delete"+dtoClazzName+"One("+dtoClazzName +" "+ DevHelperUtil.toCamelCase(dtoClazzName)+"){";
		serviceImpleCode += "\n\t\treturn "+DevHelperUtil.toCamelCase(mapperClazzName) + ".delete"+dtoClazzName+"One("+DevHelperUtil.toCamelCase(dtoClazzName)+");\n\t}\n\n";
		
		serviceImpleCode += "\tpublic "+dtoClazzName+" select"+dtoClazzName+"One("+dtoClazzName +" "+ DevHelperUtil.toCamelCase(dtoClazzName)+"){";
		serviceImpleCode += "\n\t\treturn "+DevHelperUtil.toCamelCase(mapperClazzName) + ".select"+dtoClazzName+"One("+DevHelperUtil.toCamelCase(dtoClazzName)+");\n\t}\n\n";
		
		serviceImpleCode += "\tpublic List<"+dtoClazzName+"> select"+dtoClazzName+"List("+dtoClazzName +" "+ DevHelperUtil.toCamelCase(dtoClazzName)+"){";
		serviceImpleCode += "\n\t\treturn "+DevHelperUtil.toCamelCase(mapperClazzName) + ".select"+dtoClazzName+"List("+DevHelperUtil.toCamelCase(dtoClazzName)+");\n\t}\n\n";
		
		serviceImpleCode += "}";
		
		String text = MessageFormat.format(
			template,
			packageName,
			dtoImportPath,
			serviceImportPath,
			mapperImportPath,
			serviceImpleCode
		);
		
		DevHelperUtil.createFile(serviceImplName, text);
	}
	
	public void createControllerFile(String tableName, String controllerName, String serviceImplName, String dtoImportPath, String dtoClazzName, String serviceImportPath, String serviceClazzName, String mapperImportPath, String mapperClazzName) {
		String fileName = controllerName.substring(controllerName.lastIndexOf("/")+1);
		String clazzName = fileName.split("\\.")[0];
		String template = BasicTemplate.getBasicControllerTemplate();
		String packageName = DevHelperUtil.getPackageName(controllerName);
		String[] packageNameList = packageName.split("\\.");
		int lastIdx = packageNameList.length - 1;
		System.out.println(Arrays.toString(packageNameList));
		
		String serviceImpleCode = "";
		serviceImpleCode += clazzName + "{\n";
		serviceImpleCode += "\t@Autowired\n"; 
		serviceImpleCode += "\tpublic "+serviceClazzName + " " + DevHelperUtil.toCamelCase(serviceClazzName) +";\n\n"; 
		
		serviceImpleCode += "\t@RequestMapping(\""+packageNameList[lastIdx]+"\")\n";
		serviceImpleCode += "\tpublic String regist"+dtoClazzName+"One("+dtoClazzName +" "+ DevHelperUtil.toCamelCase(dtoClazzName)+"){";
		serviceImpleCode += "\n\t\treturn \""+packageNameList[lastIdx]+"/"+"\";\n\t}\n\n";
		
		serviceImpleCode += "\t@RequestMapping(\""+packageNameList[lastIdx]+"\")\n";
		serviceImpleCode += "\tpublic String edit"+dtoClazzName+"One("+dtoClazzName +" "+ DevHelperUtil.toCamelCase(dtoClazzName)+"){";
		serviceImpleCode += "\n\t\treturn \""+packageNameList[lastIdx]+"/"+"\";\n\t}\n\n";
		
		serviceImpleCode += "\t@RequestMapping(\""+packageNameList[lastIdx]+"\")\n";
		serviceImpleCode += "\tpublic String remove"+dtoClazzName+"One("+dtoClazzName +" "+ DevHelperUtil.toCamelCase(dtoClazzName)+"){";
		serviceImpleCode += "\n\t\treturn \""+packageNameList[lastIdx]+"/"+"\";\n\t}\n\n";
		
		serviceImpleCode += "\t@RequestMapping(\""+packageNameList[lastIdx]+"\")\n";
		serviceImpleCode += "\tpublic String show"+dtoClazzName+"One("+dtoClazzName +" "+ DevHelperUtil.toCamelCase(dtoClazzName)+"){";
		serviceImpleCode += "\n\t\treturn \""+packageNameList[lastIdx]+"/"+"\";\n\t}\n\n";
		
		serviceImpleCode += "\t@RequestMapping(\""+packageNameList[lastIdx]+"\")\n";
		serviceImpleCode += "\tpublic String show"+dtoClazzName+"List("+dtoClazzName +" "+ DevHelperUtil.toCamelCase(dtoClazzName)+"){";
		serviceImpleCode += "\n\t\treturn \""+packageNameList[lastIdx]+"/"+"\";\n\t}\n\n";
		
		serviceImpleCode += "}";
		
		String text = MessageFormat.format(
			template,
			packageName,
			dtoImportPath,
			serviceImportPath,
			mapperImportPath,
			serviceImpleCode
		);
		
		DevHelperUtil.createFile(controllerName, text);
	}
	
}
