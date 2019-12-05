package com.manage.qinggong;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.manage.qinggong")
public class QinggongApplication {

	public static void main(String[] args) {
		SpringApplication.run(QinggongApplication.class, args);
	}

}
