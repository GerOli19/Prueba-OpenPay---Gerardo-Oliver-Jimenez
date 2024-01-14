package com.search.marvel.api_rest.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

/**
 * @author MBL
 */

@EnableWebSecurity
@Configuration
class WebSecurityConfig implements SecurityConfigurer<DefaultSecurityFilterChain, HttpSecurity> {

	@Value(value = "${secret.Key.jwt}")
	private String secretKey;
	
	@Override
	public void init(HttpSecurity builder) throws Exception {
		// No initialization required
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		securityFilterChain(http);
	}	

	/**Método para configurar las políticas de seguridad HTTP para la aplicación.
	 *
	 * @param http Objeto HttpSecurity que permite configurar la seguridad web basada en Spring Security. 
	 * @throws Exception Si ocurre un error durante la configuración de las políticas de seguridad. 
	 *
	 * Este método se invoca automáticamente cuando se inicia la aplicación.
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.cors(cors -> cors.configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues()))
			.csrf(csrf -> csrf.disable())
			.addFilterAfter(new JWTAuthorizationFilter(secretKey), UsernamePasswordAuthenticationFilter.class)
			.authorizeRequests(authorizeRequests -> authorizeRequests
				.antMatchers(HttpMethod.POST, "/**/access**").permitAll()
				.antMatchers(HttpMethod.GET, "/**/getCharacters**").permitAll()
				.anyRequest().authenticated()
			);
			
		return http.build();

	}

}
