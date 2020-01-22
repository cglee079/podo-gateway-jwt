package com.cglee079.podo.structure;


import com.cglee079.podo.structure.gateway.filter.AuthFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PodoGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(PodoGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator loginRoute(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r
                        .order(-1)
                        .path("/login/**")
                        .uri("http://localhost:7070")
                        .id("login")
                )
                .build();
    }

    @Bean
    public RouteLocator serverRoute(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/**")
                        .filters(f -> f.filter(new AuthFilter()))
                        .uri("http://localhost:8080")
                        .id("podo-server")
                )
                .build();
    }

}
