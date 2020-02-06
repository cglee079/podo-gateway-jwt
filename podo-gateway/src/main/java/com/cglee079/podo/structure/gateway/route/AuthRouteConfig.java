package com.cglee079.podo.structure.gateway.route;

import com.cglee079.podo.structure.gateway.route.filter.BlackListSaveFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AuthRouteConfig {

    private final BlackListSaveFilter blackListSaveFilter;

    @Bean
    public RouteLocator refreshRoute(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r
                        .order(-1)
                        .path("/refresh")
                        .uri("http://localhost:7070")
                        .filter(blackListSaveFilter)
                        .id("refresh")
                )
                .build();
    }

    @Bean
    public RouteLocator loginRoute(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r
                        .order(-1)
                        .path("/login")
                        .uri("http://localhost:7070")
                        .id("login")
                )
                .build();
    }

    @Bean
    public RouteLocator logoutRoute(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r
                        .order(-1)
                        .path("/logout")
                        .uri("http://localhost:7070")
                        .filter(blackListSaveFilter)
                        .id("logout")
                )
                .build();
    }
}
