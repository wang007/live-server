package org.live;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ServletComponentScan	//servlet容器类的扫描
@EnableScheduling	//开启定时任务
public class LiveServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LiveServerApplication.class, args);
	}
}
