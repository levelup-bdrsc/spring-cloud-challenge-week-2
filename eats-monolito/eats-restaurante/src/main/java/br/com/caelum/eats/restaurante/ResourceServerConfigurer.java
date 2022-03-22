package br.com.caelum.eats.restaurante;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

public class ResourceServerConfigurer extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers(HttpMethod.POST, "/parceiros/restaurantes")
			.hasRole("RESTAURANTE")
			.antMatchers(HttpMethod.PUT, "/parceiros/restaurantes/{id}")
			.hasRole("RESTAURANTE");
			
			//.anyRequest()
			//.authenticated();
	}
	
}
