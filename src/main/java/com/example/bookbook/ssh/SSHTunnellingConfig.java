package com.example.bookbook.ssh;

import java.util.Random;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.RequiredArgsConstructor;


@Profile("ssh")
@Configuration
@RequiredArgsConstructor
public class SSHTunnellingConfig {
	
	private final ApplicationContext application;
	
	@Bean
	@ConfigurationProperties("spring.datasource.hikari")
	HikariConfig hikariConfig() {
		return new HikariConfig();
	}
	
	//HikariDataSource 빈 수동 생성
	@Primary
	@Bean
	DataSource dataSource(
			DataSourceProperties dataSourceProperties,
			SSHTunnellingProperties sshTunnellingProperties) throws JSchException {
		//System.out.println(">>>sshTunnellingProperties:"+sshTunnellingProperties);
		//ec2-tunnelling
		//Java로 작성된 SSH2 구현 라이브러리
		JSch jsch=new JSch();
		jsch.addIdentity(sshTunnellingProperties.getPrivateKey());
		
		Session session=jsch.getSession(
				sshTunnellingProperties.getUsername(),
				sshTunnellingProperties.getSshHost(),
				sshTunnellingProperties.getSshPort());
		//호스트 키 검사 비활성화
		session.setConfig("StrictHostKeyChecking", "no");
		session.connect();
		
		int lport=new Random().nextInt(999)+33001;
		
		int localPort=session.setPortForwardingL(
				lport, 
				sshTunnellingProperties.getRdsHost(), 
				sshTunnellingProperties.getRdsPort());
		
		System.out.println("localPort:"+localPort);
		//DataSource 정보
		HikariConfig config= hikariConfig();
		config.setJdbcUrl(dataSourceProperties.getUrl().replace("[LOCAL_PORT]", String.valueOf(localPort)));
		config.setDriverClassName(dataSourceProperties.getDriverClassName());
		config.setUsername(dataSourceProperties.getUsername());
		config.setPassword(dataSourceProperties.getPassword());
		return new HikariDataSource(config);
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