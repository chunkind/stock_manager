package com.dev.ck.ackwd.config.webapp;

import org.springframework.context.annotation.Import;

import com.dev.ck.ackwd.config.app.profiles.AppConfigCommon;
import com.dev.ck.ackwd.config.app.profiles.AppConfigLocal;
import com.dev.ck.ackwd.config.app.profiles.AppConfigProduction;

//@Configuration
@Import({
	AppConfigCommon.class,
	AppConfigLocal.class, AppConfigProduction.class
})
public class WebApplicationConfig {

	
}
