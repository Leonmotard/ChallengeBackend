package com.mundo.disney.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Podremos ver la documentacion de la api e interactuar con ella en: http://localhost:8081/swagger-ui/
 * @author Leandro Silva
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	public static final String PERSONAJES	 = "Personajes";
	public static final String GENERO	 = "Genero";
	public static final String PELICULAS = "Peliculas";
	
	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.mundo.disney.controllers"))
				.paths(PathSelectors.any())
				.build()
				.tags(new Tag(PERSONAJES, "Recurso Personajes."))
				.tags(new Tag(GENERO, "Recurso Genero."))
				.tags(new Tag(PELICULAS, "Recurso Peliculas"))	
				.apiInfo(getApiInfo())
				;
	}
	
	private ApiInfo getApiInfo() {
		return new ApiInfo(
				"Demo API",
				"Api challenge Alkemy",
				"1.1",
				"...",
				new Contact("Leandro Silva", "3434718362", "leandro.jj.silva@gmail.com"),
				"LICENSE",
				"LICENSE URL",
				Collections.emptyList()
				);
	}
}