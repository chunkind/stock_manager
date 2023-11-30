package com.dev.ck.ackwd.config.webapp;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;

import com.dev.ck.ackwd.interceptor.AccessLogInterceptor;

import net.sf.json.spring.web.servlet.view.JsonView;

//@formatter:off
//@Configuration
//@Import({
//	/*WebApplicationConfig.class,*/ Scheduler.class
//})
@ComponentScan(
	basePackages = { 
		"com.dev.ck"
	} 
)
@EnableScheduling
@EnableWebMvc //  이 어노테이션은  WebMvcConfigurerAdapter 를 상속받는 곳에 선언되어야 한다.
//@formatter:off
public class CkWebAppConfig extends WebMvcConfigurerAdapter{
	
	/*인터셉터 설정*/
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new AccessLogInterceptor());
	}
	
	
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.jsp("/WEB-INF/jsp/", ".jsp");
//		TilesViewResolver viewResolver = new TilesViewResolver();
//		registry.viewResolver(viewResolver);
	} 
	
	/*타일즈 설정*/
	@Bean
	public UrlBasedViewResolver tilesViewResolver() {
		UrlBasedViewResolver bean = new UrlBasedViewResolver();
		bean.setViewClass(org.springframework.web.servlet.view.tiles3.TilesView.class);
		bean.setOrder(1);
		return bean;
	}
	
	@Bean
	public TilesConfigurer tilesConfigurer() {
		TilesConfigurer bean = new TilesConfigurer();
		bean.setDefinitions("classpath:/tiles/tiles-layout.xml");
		return bean;
	}
	
	@Bean
	public JsonView jsonView() {
		return new JsonView();
	}

	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver bean = new InternalResourceViewResolver();
		bean.setPrefix("/WEB-INF/jsp/");
		bean.setSuffix(".jsp");
		bean.setOrder(2);
		return bean;
	}
	
	/**
	 * @Date: 2023. 11. 22.
	 * @Auth: K. J. S.
	 * 스케쥴러 등록
	 */
	@Bean
	public TaskScheduler taskScheduler() {
		return new ConcurrentTaskScheduler();
	}

	// Of course , you can define the Executor too
	@Bean
	public Executor taskExecutor() {
		return new SimpleAsyncTaskExecutor();
	}
	
	/*@Bean
	public Scheduler scheduler() {
		return new Scheduler();
	}*/
}
