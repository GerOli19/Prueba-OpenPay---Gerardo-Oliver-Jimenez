package com.search.marvel.api_rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @auto Gerardo Oliver Jiménez
 * */

@SpringBootApplication
@ComponentScan(basePackages = "com.search.marvel")
public class ClienteMarvelApplication {
    public static void main(String[] args) {

        SpringApplication.run(ClienteMarvelApplication.class, args);

    }
}