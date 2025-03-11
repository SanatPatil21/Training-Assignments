package com.example.demo.config;




import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

import com.example.demo.models.*;

@Configuration
public class BeanConfigFile {
	@Bean("prog")
	@Scope(value = "prototype")
	public Programmer getProgrammer() {
		return new Programmer();
	}	
	
	@Bean("clerk")
	@Scope(value = "prototype")
    public Clerk clerk() {
        return new Clerk();
    }
	
    @Bean("manager")
    @Scope(value = "prototype")
    public Manager manager() {
        return new Manager();
    }

    @Bean("ceo")
    @Lazy
    public CEO ceo() {
        return CEO.registerCEO();
    }
    
    
    
    
}
