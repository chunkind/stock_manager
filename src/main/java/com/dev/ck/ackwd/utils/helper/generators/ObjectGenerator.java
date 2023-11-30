package com.dev.ck.ackwd.utils.helper.generators;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


public class ObjectGenerator {
	public static void main(String[] args) throws Exception {
		createObject("a");
	}
	
	public static void createObject(String className) throws Exception{
		File file = new File("sample.txt");
		FileReader fileReader = new FileReader(file);
		BufferedReader bufReader = new BufferedReader(fileReader);
		
		String line = "";
		String preFix = "private";
		String postFix = ";";
		
		System.out.println("import lombok.Data;");
		System.out.println("import ehimart.app.domain.base.mapper.ProjectAuditEntity;");
		System.out.println();
		System.out.println("@Data");
		System.out.println("public class "+className+" extends ProjectAuditEntity {");
		while((line = bufReader.readLine()) != null){
			String[] tempLineArr = line.split("\t");
			String[] lineArr = new String[3];
			int cnt = 0;
			for (String element : tempLineArr) {
				String temp = element.replace("\t", "");
				temp = temp.replace(" ", "");
				if(!"".equals(temp)){
					lineArr[cnt] = temp;
					cnt++;
				}
			}
			
			if(lineArr[2].equals("int")){
				System.out.println("\t"+preFix + " int " + lineArr[0] + postFix + " /*" + lineArr[1] +"*/");
			}else if(lineArr[2].equals("double")){
				System.out.println("\t"+preFix + " double " + lineArr[0] + postFix + " /*" + lineArr[1] +"*/");
			}else{
				System.out.println("\t"+preFix + " String " + lineArr[0] + postFix + " /*" + lineArr[1] +"*/");
			}
		}
		System.out.println("}");
		bufReader.close();
	}
}
