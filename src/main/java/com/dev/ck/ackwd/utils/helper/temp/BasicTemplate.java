package com.dev.ck.ackwd.utils.helper.temp;

public class BasicTemplate {
	public static String getBasicXmlTemplate(){
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		sb.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \n");
		sb.append("\"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n");
		sb.append("<mapper namespace=\"{0}\">\n");
		sb.append("\t<insert id=\"{1}\" resultType=\"{2}\">\n");
		sb.append("\t/* {3} */\n");
		sb.append("{4}"); // insert
		sb.append("\t</insert>\n");
		sb.append("\t<select id=\"{5}\" resultType=\"{6}\">\n");
		sb.append("\t/* {7} */\n");
		sb.append("{8}"); // select
		sb.append("\t</select>\n");
		sb.append("\t<update id=\"{9}\" resultType=\"{10}\">\n");
		sb.append("\t/* {11} */\n");
		sb.append("{12}"); // update
		sb.append("\t</update>\n");
		sb.append("\t<delete id=\"{13}\" resultType=\"{14}\">\n");
		sb.append("\t/* {15} */\n");
		sb.append("{16}"); // delete
		sb.append("\t</delete>\n");
		sb.append("</mapper>");
		return sb.toString();
	}
	
	public static String getBasicDtoTemplate(){
		StringBuilder sb = new StringBuilder();
		sb.append("package {0};\n\n");
		sb.append("import lombok.Builder;\n");
		sb.append("import lombok.Data;\n");
		sb.append("import lombok.NoArgsConstructor;\n\n");
		sb.append("@Data\n");
		sb.append("@NoArgsConstructor\n");
		sb.append("public class {1}");
		return sb.toString();
	}
	
	public static String getBasicMapperTemplate(){
		StringBuilder sb = new StringBuilder();
		sb.append("package {0};\n\n");
		sb.append("import java.util.List;\n");
		sb.append("import org.apache.ibatis.annotations.Mapper;\n");
		sb.append("import {1};\n\n");
		sb.append("@Mapper\n");
		sb.append("public interface {2}");
		return sb.toString();
	}
	
	public static String getBasicServiceTemplate() {
		StringBuilder sb = new StringBuilder();
		sb.append("package {0};\n\n");
		sb.append("import java.util.List;\n");
		sb.append("import {1};\n\n");
		sb.append("public interface {2}");
		return sb.toString();
	}
	
	public static String getBasicServiceImplTemplate() {
		StringBuilder sb = new StringBuilder();
		sb.append("package {0};\n\n");
		sb.append("import java.util.List;\n\n");
		sb.append("import org.springframework.beans.factory.annotation.Autowired;\n");
		sb.append("import org.springframework.stereotype.Service;\n");
		sb.append("import {1};\n");
		sb.append("import {2};\n");
		sb.append("import {3};\n\n");
		sb.append("@Service\n");
		sb.append("public class {4}");
		return sb.toString();
	}

	
	public static String getBasicControllerTemplate(){
		StringBuilder sb = new StringBuilder();
		sb.append("package {0};\n\n");
		sb.append("import java.util.List;\n\n");
		sb.append("import org.springframework.beans.factory.annotation.Autowired;\n");
		sb.append("import org.springframework.stereotype.Controller;\n");
		sb.append("import org.springframework.web.bind.annotation.RestController;\n");
		sb.append("import org.springframework.web.bind.annotation.RequestMapping;\n\n");
		sb.append("import {1};\n");
		sb.append("import {2};\n");
		sb.append("import {3};\n\n");
		sb.append("@Controller\n");
		sb.append("public class {4}");
		return sb.toString();
	}
	
}
