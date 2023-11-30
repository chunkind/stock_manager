package com.dev.ck.ackwd.config;

import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import lombok.extern.slf4j.Slf4j;
//import nframework.web.filter.jsoninjson.JsonInJsonpServletFilter;
//import nframework.web.mdccleaner.MDCCleanerServletFilter;
//import nframework.web.multipart.MultipartConditionFilter;
//import nframework.web.webClient.WebClientHolderFilter;

/**
 * Servlet 3.0 에서 도입된 자바 코드 기반의 초기화 모듈. servlet 2.5 까지의 web.xml 을 대체한다.
 * 
 * web.xml 중 일부 기능( session-timeout 등)은 대체가 않되면 여전히 web.xml 에서 설정한다.
 * 
 * @author narusas
 *
 */

@Slf4j
public abstract class CommonWebApplicationInitializer implements WebApplicationInitializer {
	static final String DISPATCHER_NAME = "appDispatcher";
	protected AnnotationConfigWebApplicationContext rootContext;
	protected AnnotationConfigWebApplicationContext dispatcherContext;
	protected String getServletName() {
		return DISPATCHER_NAME;
	}

	/**
	 * Dispatcher Servlet 을 매핑할 경로지정. 보통 /app 에 매핑한다.
	 * 
	 * @return 매핑할 경로
	 */
	abstract protected String[] getServletMappings();

	/**
	 * Root Application Context 를 초기화 할 Root Configuration 클래스 지정
	 * 
	 * @return
	 */
	abstract protected Class<?>[] getRootContextConfig();

	/**
	 * Dispatcher Servlet 에서 초기화 될 Application Context 에 대한 Configuration 클래스 지정
	 * 
	 * @return
	 */
	abstract protected Class<?>[] getServletContextConfig();

	protected boolean enableWebSecurity() {
		boolean defaultValue = true;
		return defaultValue;
	}

	/**
	 * 웹 어플리케이션의 초기화 시작 메소드. Servlet 3.0 에서는 onStartup 메소드에서 시작하게 된다.
	 */
	@Override
	public void onStartup(ServletContext container) throws ServletException {
		System.out.println("시작....");
		CodeSource src = getClass().getProtectionDomain().getCodeSource();
		if (src != null) {
			URL jar = src.getLocation();
			log.info("CommonWebApplicationInitializer.class file is in {}", jar);
		}

		// 로깅 프레임워크가 초기화 되기 전이므로 System.out 을 사용한다.
		log.info("CommonWebApplicationInitializer.onStartup start");
		initListeners(container);
		initServlets(container);
		initFilters(container);
		log.info("CommonWebApplicationInitializer.onStartup end");
	}

	private void initListeners(ServletContext container) {
		// 로그를 제일 먼저 초기화 해야 기동중의 메시지들을 정상적으로 로깅할수 있다.
		initLogback(container);
		rootContext = initRootContext(container);
		initHttpSessionEventPublisher(container);
		initCustomListener(container);
	}

	abstract protected void initCustomListener(ServletContext container);
	/**
	 * HttpSession 에서 발생하는 되는 이벤트를 받아 Application 에 전달하기 위한 리스너 등록
	 * 
	 * @param container
	 */
	private void initHttpSessionEventPublisher(ServletContext container) {
		log.debug("Add HttpSessionEventPublisher");
		container.addListener(new HttpSessionEventPublisher());
	}

	private void initServlets(ServletContext container) {
		initDispatcherServlet(container, rootContext);
	}

	private void initFilters(ServletContext container) {
		if (useWebXmlFilters()) {
			return;
		}
//		initSystemMonitorFilter(container);
		initCharacterEncodingFilter(container);
//		initJsonInJsonpFilter(container);
//		initXssFilter(container);
//		initMDCCleanerFilter(container);
//		initWebClientFilter(container);
//		initMultipartConditionFilter(container);
//		initRequestTransactionContextFilter(container);
		if (enableWebSecurity()) {
			initSpringSecurityFilterChain(container);
		}
//		initResourceLoadingFilter(container);
		initCustomeFilters(container);
	}

//	private void initJsonInJsonpFilter(ServletContext container) {
//		container.addFilter("JsonInJsonpFilter", new JsonInJsonpServletFilter()).addMappingForUrlPatterns(null, true, getServletMappings());
//
//	}

	private void initSystemMonitorFilter(ServletContext container) {
		container.addFilter("systemMonitorFilter", new DelegatingFilterProxy("systemMonitorFilter")).addMappingForUrlPatterns(null, true,
				getServletMappings());
	}

	protected abstract boolean useWebXmlFilters();

	private void initCharacterEncodingFilter(ServletContext container) {

		log.debug("Add CharacterEncodingFilter");
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		// @formatter:off
		try {
			String[] mappings = getServletMappings();
		container
			.addFilter("UTF_EncodingFilter", characterEncodingFilter)
				.addMappingForUrlPatterns(null, true, mappings);
		}
		catch(Exception ex){
			ex.printStackTrace();
			throw ex;
		}
	}

	private void initXssFilter(ServletContext container) {
		container.addFilter("xssServletFilter", new DelegatingFilterProxy("xssServletFilter")).addMappingForUrlPatterns(null, true,
				getServletMappings());
	}

//	private void initWebClientFilter(ServletContext container) {
//		container.addFilter("wenClientFilter", new WebClientHolderFilter()).addMappingForUrlPatterns(null, true, getServletMappings());
//
//	}

//	private void initMultipartConditionFilter(ServletContext container) {
//		container.addFilter("multipartConditionFilter", new MultipartConditionFilter()).addMappingForUrlPatterns(null, true,
//				getServletMappings());
//	}

	private void initRequestTransactionContextFilter(ServletContext container) {
		DelegatingFilterProxy requestTransactionContextFilter = new DelegatingFilterProxy("requestTransactionContextFilter");
		container.addFilter("requestTransactionContextFilter", requestTransactionContextFilter).addMappingForUrlPatterns(null, true,
				getServletMappings());
	}

//	private void initMDCCleanerFilter(ServletContext container) {
//		container.addFilter("mdcCleanerFilter", new MDCCleanerServletFilter()).addMappingForUrlPatterns(null, false, getServletMappings());
//	}

	abstract protected void initCustomeFilters(ServletContext container);

	private void initLogback(ServletContext container) {
		patchSlf4jBridge();
		addLogbackListener(container);

	}

	/**
	 * java.util.logger 를 사용하는 라이브러리들이 정상적으로 SLF4J를 통해 로그가 기록되게 하기 위한 설정
	 * 이 세팅을 JUL 의 설정 파일인 logging.properties 에서 수행해도 된다.
	 * <code>handlers = org.slf4j.bridge.SLF4JBridgeHandler</code>
	 * @see src/main/resource/logging.properties
	 */
	private void patchSlf4jBridge() {
		log.debug("Patch SLF4J Bridge");
		// Optionally remove existing handlers attached to j.u.l root logger
		SLF4JBridgeHandler.removeHandlersForRootLogger(); // (since SLF4J 1.6.5)

		// add SLF4JBridgeHandler to j.u.l's root logger, should be done once
		// during
		// the initialization phase of your application
		SLF4JBridgeHandler.install();
	}

	private void addLogbackListener(ServletContext container) {
		log.debug("Add Logback Listener to container");
		container.addListener(ch.qos.logback.ext.spring.web.LogbackConfigListener.class);
	}

	private AnnotationConfigWebApplicationContext initRootContext(ServletContext container) {

		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(getRootContextConfig());

		ContextLoaderListener contextLoaderListener = new ContextLoaderListener(rootContext);

		container.addListener(contextLoaderListener);
		return rootContext;
	}

	private void setupApplicationContextInitializer(ServletContext container) {
		List<String> contextInitializerClasses = new ArrayList<>();
		collectCustomApplicationContextInitializerClass(contextInitializerClasses);
		if (contextInitializerClasses.isEmpty()) {
			return;
		}
		container.setInitParameter(ContextLoader.CONTEXT_INITIALIZER_CLASSES_PARAM, StringUtils.join(contextInitializerClasses, ','));
	}

	protected void collectCustomApplicationContextInitializerClass(List<String> contextInitializerClasses) {
		contextInitializerClasses.add("nframework.core.propertysources.ProfiledCsvPropertyInitializer");
	}

	private void initDispatcherServlet(ServletContext container, AnnotationConfigWebApplicationContext rootContext) {
		log.debug("Init dipatcher servlet");
		dispatcherContext = new AnnotationConfigWebApplicationContext();
		//
		// Root application context 는 이 시점까지는 초기화 되지 않음.
		// ContextListner가 진행 되어야 초기화 됨. 여기에서 setParent 를 호출하면 property source
		// 에 대한 충돌이 발생하게 됨(원래는 Root context 의 property 들을 child 에서 읽을수 있어야 하는데
		// 못읽는 문제가 발생함). 따라서 setParent 를 호출하면 않됨.
		//
		// dispatcherContext.setParent(rootContext);
		dispatcherContext.register(getServletContextConfig());
		dispatcherContext.setServletContext(container);

		ServletRegistration.Dynamic dispatcher = container.addServlet(getServletName(), new DispatcherServlet(dispatcherContext));
		if (dispatcher == null) {
			return;
		}

		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping(getServletMappings());
	}

	private void initResourceLoadingFilter(ServletContext container) {
		log.debug("Add ResourceLoadingFilter");
		DelegatingFilterProxy resourceLoaderServletFilter = new DelegatingFilterProxy("resourceLoaderServletFilter");
		//@formatter:off
		container
			.addFilter("resourceLoaderServletFilter", resourceLoaderServletFilter)
				.addMappingForUrlPatterns(null, false, getResourceLoadingMapping());
		
		//@formatter:on
	}

	protected abstract String getResourceLoadingMapping();

	private void initSpringSecurityFilterChain(ServletContext container) {
		log.debug("Add Security Filter Chain");
		/*
		 * DelegatingFilterProxy 는 빈을 찾을 때 기본적으로 Root Context 를 찾는다. 하지만, Security 설정은 Front/BO 등 웹 어플리케이션 단위로 설정이 된다. DelegatingFilterProxy
		 * 에서 웹 어플리케이션 컨텍스트에서 관련 빈을 찾게 설정한다.
		 */
		DelegatingFilterProxy springSecurityFilterChain = new DelegatingFilterProxy("springSecurityFilterChain");
		springSecurityFilterChain.setContextAttribute("org.springframework.web.servlet.FrameworkServlet.CONTEXT." + getServletName());
		container.addFilter("springSecurityFilterChain", springSecurityFilterChain)
				.addMappingForUrlPatterns(null, true, getServletMappings());
	}
}