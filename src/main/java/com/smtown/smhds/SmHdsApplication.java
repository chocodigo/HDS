package com.smtown.smhds;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
 
@SpringBootApplication
@MapperScan(basePackages= {"com.smtown.smhds.*"})
@EnableScheduling
public class SmHdsApplication {

	private static final Logger log = LoggerFactory.getLogger(SmHdsApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(SmHdsApplication.class, args);
	}

	/*
	* DB 연결 설정
	* application.properties에 datasource 설정 후 런타임시 주입받음
	* */
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception{
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		
		sessionFactory.setDataSource(dataSource);
		return sessionFactory.getObject();
	}
}
