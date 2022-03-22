package br.com.caelum.eats;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.client.HttpClientErrorException;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@EnableDiscoveryClient
@EnableResourceServer
@SpringBootApplication
public class EatsApplication {

	Boolean isAutenticado = null;
	
	@Bean
	public RequestInterceptor getInterceptorDeAutenticacao() {
		
		RequestInterceptor rI = new RequestInterceptor() {
			@Override		
			public void apply(RequestTemplate template) {
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				if (auth == null) {
					isAutenticado = false;
				}
				
				OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) auth.getDetails();
				details.getTokenValue();
				template.header("Authorization", "Bearer" + details.getTokenValue());
			}
		};
		
		if (isAutenticado) {
			return rI;
		} else {
			throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
		}
	}
	
	public static void main(String[] args) {
		SpringApplication.run(EatsApplication.class, args); 
	}

}
