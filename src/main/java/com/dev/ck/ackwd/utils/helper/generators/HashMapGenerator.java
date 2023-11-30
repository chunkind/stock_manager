package com.dev.ck.ackwd.utils.helper.generators;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class HashMapGenerator {
	public static void main(String[] args) throws Exception {
		
		createObject("param");
		
	}
	
	public static void createObject(String objName) throws Exception{
		File file = new File("sample.txt");
		BufferedReader bufReader = new BufferedReader(new FileReader(file));
		String line = "";
		String preFix = "private";
		String postFix = ";";
		List<String> list = new ArrayList<String>();
		
		while((line = bufReader.readLine()) != null){
			list.add(line);
		}
		
		System.out.println("Map<String, String> " + objName + " = new HashMap<String, String>();");
		System.out.println();
		for(int i=0; i<list.size(); i++){
			String[] tempLineArr = list.get(i).split("\t");
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
				System.out.println("int " + lineArr[0]  + " = 0; /*" + lineArr[1] +"*/");
			}else if(lineArr[2].equals("double")){
				System.out.println("double " + lineArr[0] + " = 0.0; /*" + lineArr[1] +"*/");
			}else{
				System.out.println("String " + lineArr[0] + " = \"\"; /*" + lineArr[1] +"*/");
			}
		}
		System.out.println();
		for(int i=0; i<list.size(); i++){
			String[] lineArr = list.get(i).split("\t");
			System.out.println(objName + ".put(\"" +  lineArr[0] + "\","+lineArr[0]+");" + " /*" + lineArr[1] +"*/");
		}
		bufReader.close();
	}
}
