package com.dev.ck.ackwd.utils.helper.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.jdbc.support.JdbcUtils;


public class DevHelperUtil {
	private static boolean ENABLE_ENTER = true;
	
	public static String getPackageName(String filePath){
		String fileName = filePath.substring(filePath.lastIndexOf("/")+1);
		filePath = filePath.replaceAll(fileName, "");
		String path = filePath.replaceAll("/", ".").replace("\\", ".").trim();
		System.out.println(path);
		String[] paths = path.split("\\.");
		String[] rootPaht = {"src","main","java"};
		String packageName = "";
		
		int cnt=0;
		mainLoop : for(int i=0; i<paths.length; i++) {
			if(paths[i].equals(rootPaht[0])) {
				int j=0;
				for(; j<rootPaht.length; j++) {
					if(!paths[i+j].equals(rootPaht[j])) {
						continue mainLoop;
					}
				}
				cnt += i + rootPaht.length;
				break mainLoop;
			}
		}
		for(int i = cnt; i<paths.length; i++) {
			if(i == paths.length - 1) {
				packageName += paths[i];
			}else {
				packageName += paths[i] + ".";
			}
		}
		return packageName;
	}
	
	public static String toCamelCase(String target){
		return JdbcUtils.convertUnderscoreNameToPropertyName(target);
	}
	
	public static String toPascalCase(String target){
		String camel = JdbcUtils.convertUnderscoreNameToPropertyName(target);
		return camel.substring(0, 1).toUpperCase() + camel.substring(1);
	}
	
	public static void createDirAndFile(String fullPath){
		File paramPath = new File(fullPath);
		String filePath = paramPath.getAbsolutePath();
		String dirPath = filePath.substring(0, filePath.lastIndexOf(File.separator));
		File targetDir = new File(dirPath);
		File targetFile = new File(filePath);
		
		try {
			//디랙토리 생성.
			if(!targetDir.exists()){
				targetDir.mkdirs();
			}
			
			//파일 생성.
			if(!targetFile.exists()){
				if(targetFile.createNewFile()){
					System.out.println(targetFile.getAbsolutePath() + " 파일 생성 완료 !");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void createFile(String filePath, String text){
		File file = new File("");
		String path = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf(File.separator));
		String fullPath = path + filePath;
		
		DevHelperUtil.createDirAndFile(fullPath);
		
		File paramPath = new File(fullPath);
		filePath = paramPath.getAbsolutePath();
		String dirPath = filePath.substring(0, filePath.lastIndexOf(File.separator));
		File targetDir = new File(dirPath);
		File targetFile = new File(filePath);
		try {
			FileWriter fw = new FileWriter(targetFile);
			BufferedWriter writer = new BufferedWriter(fw);
			writer.write(text);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static StringBuilder appendEnter(StringBuilder sb){
		if(ENABLE_ENTER){
			sb.append("\n");
		}else{
			sb.append(" ");
		}
		return sb;
	}
}
