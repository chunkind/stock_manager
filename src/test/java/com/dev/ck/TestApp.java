package com.dev.ck;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;

import com.dev.ck.ackwd.config.webapp.TestAppConfig;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {TestAppConfig.class, TestApp.class})
@Slf4j
public class TestApp{
	public static final String LONG_WORD = "안녕하세요. 긴글 입니다. 많이 긴글 입니다. 아주 많이 많이 많이 많이...... 많이 긴글... 얼마나 길어야 할까요? ㅎㅎㅎㅎㅎ 길이이이이이이일다~~~~~~~~~~";
	public static final String SHORT_WORD = "짧은 글.";
	public static final String AUTH = "stock";

	static {
//		Logger log = Logger.getRootLogger();
//		log.setLevel(Level.DEBUG);
//		log.addAppender(new ConsoleAppender(
//			new PatternLayout("%-6r [%p] %c - %m%n")));
	}

	protected MockHttpServletRequest request = new MockHttpServletRequest();
	protected MockHttpServletResponse response = new MockHttpServletResponse();
	protected MockHttpSession session = new MockHttpSession();
	
	public void resetMock() {
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		session = new MockHttpSession();
	}
	
	/**
	 * @User : K. J. S.
	 * @date : 2020. 3. 27.
	 * 멀티파일을 목으로 만든다.
	 */
	public MultipartFile createTestMultipartFile(String imgNm, String orgNm, String tfPath) {
		File targetFile = null;
		MultipartFile mfile = null;
		try {
			targetFile = new File(tfPath);
			mfile = new MockMultipartFile(imgNm, orgNm, null, new FileInputStream(targetFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mfile;
	}
}