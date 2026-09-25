package mx.gob.imss.dsdir.altapff.batchaltas;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class DataSourceConfig {

	 @Autowired
	 private Environment env;

	
	  @Primary
	  @Bean(name ="springBatchDatasource")
	  public DataSource dataSource() {		  
		  return DataSourceBuilder.create()
				  .url(env.getProperty("spring.primary.datasource.url"))
				  .driverClassName(env.getProperty("spring.primary.datasource.driver-class-name"))
				  .username(env.getProperty("spring.primary.datasource.username"))
				  .password(env.getProperty("spring.primary.datasource.password"))
				  .build();		  
	  }
	   

	  @Bean(name ="secondaryDatasource")
	  public DataSource dataSource2() {
		  return DataSourceBuilder.create()
				  .url(env.getProperty("spring.secondary.datasource.url"))
				  .driverClassName(env.getProperty("spring.secondary.datasource.driver-class-name"))
				  .username(env.getProperty("spring.secondary.datasource.username"))
				  .password(env.getProperty("spring.secondary.datasource.password"))
				  .build();		  
	  }

	  
	  @Bean(name="primaryJdbcTemplateObject")
	  public JdbcTemplate primaryJdbcTemplate(@Qualifier("secondaryDatasource") DataSource secondaryDs){
	  	return new JdbcTemplate(secondaryDs);
	  }
	  
	  @Bean(name="secondaryJdbcTemplateObject")
	  public JdbcTemplate secondaryJdbcTemplate(@Qualifier("secondaryDatasource") DataSource secondaryDs){
	  	return new JdbcTemplate(secondaryDs);
	  }
	  
	  
	  @Bean
	  PlatformTransactionManager businessManager() {
	  		return new DataSourceTransactionManager(dataSource2());
	  }


	  @Bean
	  PlatformTransactionManager metaDataManager() {
	  		return new DataSourceTransactionManager(dataSource());
	  }
	  
	  
}
