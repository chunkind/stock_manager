package com.dev.ck.ackwd.utils.helper.generators;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.springframework.jdbc.support.JdbcUtils;


public class CamelGenerator {
	public static void main(String[] args) throws Exception {
		
		createObject("a");
		
	}
	
	public static void createObject(String className) throws Exception{
		File file = new File("sample.txt");
		FileReader fileReader = new FileReader(file);
		BufferedReader bufReader = new BufferedReader(fileReader);
		
		String line = "";
		while((line = bufReader.readLine()) != null){
			System.out.println("private String "+JdbcUtils.convertUnderscoreNameToPropertyName(line));
		}
		bufReader.close();
	}
}
