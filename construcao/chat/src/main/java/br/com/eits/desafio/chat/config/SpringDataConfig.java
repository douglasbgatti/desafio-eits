package br.com.eits.desafio.chat.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.GlobalFilter;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.extend.Configurator;
import org.directwebremoting.spring.DwrClassPathBeanDefinitionScanner;
import org.directwebremoting.spring.DwrController;
import org.directwebremoting.spring.DwrHandlerMapping;
import org.directwebremoting.spring.SpringConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;

//@Configuration
//@EnableJpaRepositories("br.com.eits.desafio.chat.domain.repository")
//@EnableTransactionManagement
//@PropertySource(value="classpath:application.properties")
public class SpringDataConfig {
	
	@Autowired
	private Environment env;
	
	/**
	 * 
	 * @param factory
	 * @return manager
	 * Bean que irá controlar as transacoes
	 */
	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory factory){
		JpaTransactionManager manager = new JpaTransactionManager();
		manager.setEntityManagerFactory(factory);
		manager.setJpaDialect(new HibernateJpaDialect());
		return manager;
	}
	
	/**
	 * Setando o hibernate como framework ORM
	 * @return
	 */
	@Bean
	public HibernateJpaVendorAdapter jpaVendorAdapter(){
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setShowSql(env.getProperty("hibernate.show_sql", Boolean.class));	//mostrando as sql geradas
		adapter.setGenerateDdl(env.getProperty("hibernate.ddl", Boolean.class)); // gerar tabelas
		return adapter;
	}
	
	/**
	 * Criando a factory de entityManager controlada pelo Spring
	 * @return
	 */
	@Bean
	public EntityManagerFactory entityManagerFactory(){
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(jpaVendorAdapter());
		factory.setPackagesToScan(
				env.getProperty("hibernate.package.scan"),
				env.getProperty("java.time.jpa.converter"));
		factory.setDataSource(dataSource());
		factory.afterPropertiesSet();
		return factory.getObject();
	}
	
	/**
	 * Datasource para o pool de connections por isso utilzar o BasicDataSource
	 * @return
	 */
	@Bean(name="dataSource")
	public DataSource dataSource(){
		BasicDataSource dataSource = new BasicDataSource(); //do apache.dbcp2
		dataSource.setUsername(env.getProperty("jdbc.username"));
		dataSource.setPassword(env.getProperty("jdbc.password"));
		dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
		dataSource.setUrl(env.getProperty("jdbc.url"));
		return dataSource;		
	}
	

		

}
