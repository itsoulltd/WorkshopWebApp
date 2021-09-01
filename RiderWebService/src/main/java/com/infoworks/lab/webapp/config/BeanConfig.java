package com.infoworks.lab.webapp.config;

import com.infoworks.lab.domain.entities.Rider;
import com.it.soul.lab.data.simple.SimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean("HelloBean")
    public String getHello(){
        return "Hi Spring Hello";
    }

    @Bean("riderInMemDatasource")
    public SimpleDataSource<String, Rider> getRiderDatasource(){
        return new SimpleDataSource<>();
    }

}
