package com.cglee079.podo.structure.gateway.route;

import com.cglee079.podo.structure.gateway.filter.AuthFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
public class RouteConfig {

    @Bean
    public RouteLocator joinRoute(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r
                        .order(-1)
                        .method(HttpMethod.POST)
                        .and()
                        .path("/join/**")
                        .uri("http://localhost:6061")
                        .id("join")
                )
                .build();
    }


    @Bean
    public RouteLocator loginRoute(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r
                        .order(-1)
                        .method(HttpMethod.POST)
                        .and()
                        .path("/login/**")
                        .uri("http://localhost:7070")
                        .id("auth")
                )
                .build();
    }

    @Bean
    public RouteLocator serverRoute(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/**")
                        .uri("http://localhost:8080")
                        .id("podo-server")
                )
                .build();
    }
}
