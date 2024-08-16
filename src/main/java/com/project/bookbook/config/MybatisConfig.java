package com.project.bookbook.config;

import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import com.jcraft.jsch.JSchException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@Profile("ec2") //이 클래스는 'prod' 프로파일에서만 활성화
public class MybatisConfig {
	
	private final ApplicationContext application;//생성자 DI
	
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.hikari")
	HikariConfig hikariConfig() {
		return new HikariConfig();
	}
	
	@Bean
	DataSource dataSource() throws JSchException {
		return new HikariDataSource(hikariConfig());
	}
	
	@Bean
	SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		
		SqlSessionFactoryBean factoryBean=new SqlSessionFactoryBean();
		//1.datasource
		factoryBean.setDataSource(dataSource);
		//2.Configuration
		factoryBean.setConfiguration(mybatisConfiguration());
		//3.mapper.xml-location patton
		String locationPattern="classpath*:mappers/**/*-mapper.xml";
		Resource[] resource=application.getResources(locationPattern); // ... 대신 여러개 집합인 배열로..
		factoryBean.setMapperLocations(resource);
		
		return factoryBean.getObject();
	}
	
	@Bean
	@ConfigurationProperties(prefix = "mybatis.configuration")
	org.apache.ibatis.session.Configuration mybatisConfiguration() {
		return new org.apache.ibatis.session.Configuration();
	}

	@Bean
	SqlSessionTemplate sqlSessionTemplate(DataSource dataSource) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory(dataSource));
	}


}