package com.bellintegrator.weatherbroker;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jndi.JndiTemplate;
import org.springframework.transaction.jta.JtaTransactionManager;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import javax.jms.ConnectionFactory;
import javax.jms.XATopicConnectionFactory;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication // @SpringBootApplication Equivalent to using @Configuration, @EnableAutoConfiguration and @ComponentScan with their default attributes
@EnableJms
@Configuration
@ComponentScan("com.bellintegrator.weatherbroker")
@PropertySource("classpath:application.properties")
public class WeatherbrokerApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(WeatherbrokerApplication.class, args);
	}

	@Autowired
	private Environment environment;

	@Bean
	RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		ObjectMapper mapper = new ObjectMapper();
//		mapper.setDateFormat(df);
		converter.setObjectMapper(mapper);
		restTemplate.getMessageConverters().add(converter);
		return restTemplate;
	}

	@Bean
	public ViewResolver jspViewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/");
		viewResolver.setSuffix(".jsp");
		viewResolver.setContentType("text/html");
//		viewResolver.setOrder(1000);
		return viewResolver;
	}

//	@Bean
//	public JndiTemplate jndiTemplate() {
//		JndiTemplate jndiTemplate = new JndiTemplate();
//		return jndiTemplate;
//	}

//	@Bean
//	public ActiveMQConnectionFactory connectionFactory() {
//		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
////		ConnectionFactory connectionFactory = (ConnectionFactory) jndiTemplate().lookup(environment.getProperty("spring.activemq.connectionfactory.jndi-name"));
//		connectionFactory.setBrokerURL(environment.getProperty("spring.activemq.broker-url"));
//		connectionFactory.setPassword(environment.getProperty("spring.activemq.password"));
//		connectionFactory.setUserName(environment.getProperty("spring.activemq.user"));
////		connectionFactory.setTrustedPackages(new ArrayList(Arrays.asList((
////				"com.bellintegrator.weatherbroker.model.ForecastForDay," +
////				"com.bellintegrator.weatherbroker.model.WeatherForecast," +
////				"com.bellintegrator.weatherbroker.model.WeatherCondition")
////				.split(","))));
//		connectionFactory.setTrustAllPackages(true);
//		// When true the consumer will check for duplicate messages and properly
//		// handle the message to make sure that it is not processed twice inadvertently.
//		connectionFactory.setCheckForDuplicates(true);
//		// The size of the message window that will be audited for duplicates and out of order messages.
//		connectionFactory.setAuditDepth(10000);
//		// Maximum number of producers that will be audited.
//		connectionFactory.setAuditMaximumProducerNumber(128);
//		return connectionFactory;
//	}


	// или просто ConnectionFactory?
//	@Bean
//	public XATopicConnectionFactory connectionFactory() throws NamingException {
//		XATopicConnectionFactory connectionFactory = (XATopicConnectionFactory) jndiTemplate()
//				.lookup(environment.getProperty("spring.artemis.connectionfactory.jndi-name"));
//		return connectionFactory;
//	}

//	@Bean
//	public JmsTemplate jmsTemplate() throws NamingException {
//		JmsTemplate template = new JmsTemplate();
////		template.setConnectionFactory(connectionFactory());
//		template.setPubSubDomain(true); // pub/sub
//		return template;
//	}

//	@Bean
//	public JtaTransactionManager transactionManager() {
//		return new JtaTransactionManager();
//	}

//	@Bean
//	public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() throws NamingException {
//		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
////		factory.setConnectionFactory(connectionFactory());
////		factory.setConcurrency("1-1");
//		factory.setPubSubDomain(true); // pub/sub
////		factory.setSessionTransacted(true); // Local resource transactions can simply be activated through the sessionTransacted flag
//		// To configure a message listener container for XA transaction participation, you'll want to configure a JtaTransactionManager
////		factory.setTransactionManager(set here!);
//		return factory;
//	}
}
