package com.dev.ck.stmg.batch;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Scheduler {
	
	//초 분 시 일 월 요일
	@Scheduled(cron = "0 0 0 * * *")
	public void test01() {
		log.info("=test01 start====================================================");
		log.info("=test01 end====================================================");
	}
	
	//1초마다 실행
//	@Scheduled(fixedDelay = 10000)
	//초 분 시 일 월 요일
	@Scheduled(cron = "0 0 1,7,11 * * *")
	public void test02() {
		log.info("=test02 start====================================================");
		log.info("=test02 end====================================================");
	}
}