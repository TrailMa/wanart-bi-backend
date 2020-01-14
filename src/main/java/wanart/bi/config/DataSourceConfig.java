package wanart.bi.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
    @Bean(name="dldlDataSource")
    @Qualifier("dldlDataSource")
    @ConfigurationProperties(prefix="spring.datasource.dldl")
    public DataSource dldlDataSource(){
       return DataSourceBuilder.create().build();
    }

    @Bean(name="mxzmDataSource")
    @Qualifier("mxzmDataSource")
    @ConfigurationProperties(prefix="spring.datasource.mxzm")
    public DataSource mxzmDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name="manageDataSource")
    @Qualifier("manageDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.manage")
    public DataSource manageDataSource(){return DataSourceBuilder.create().build();}

    @Bean(name="dldlJdbcTemplate")
    public JdbcTemplate dldlJdbcTemplate(@Qualifier("dldlDataSource") DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    @Bean(name="mxzmJdbcTemplate")
    public JdbcTemplate mxzmJdbcTemplate(@Qualifier("mxzmDataSource") DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    @Bean(name="manageJdbcTemplate")
    public JdbcTemplate manageJdbcTemplate(@Qualifier("manageDataSource") DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }
}
