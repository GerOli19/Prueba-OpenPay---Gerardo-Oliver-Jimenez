package com.search.marvel.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class SearchMarvelConfig {
    @Value("${url.base.api}")
    private String baseUrl;

    /**
     * Configura y crea un objeto WebClient, que se puede usar para realizar solicitudes HTTP.
     * @return Un objeto WebClient configurado con una cabecera por defecto que acepta JSON y una URL base establecida.
     * @author Gerardo Oliver Jimenez
     */

    @Bean
    public WebClient webClient() {

        return WebClient.builder()
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .baseUrl(baseUrl)
                .build();

    }
}
