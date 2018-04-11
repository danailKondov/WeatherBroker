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
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.jndi.JndiTemplate;
import org.springframework.web.client.RestTemplate;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.text.SimpleDateFormat;

@SpringBootApplication
@Configuration
@ComponentScan("com.bellintegrator.weatherbroker")
public class WeatherbrokerApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(WeatherbrokerApplication.class, args);
	}

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
}
