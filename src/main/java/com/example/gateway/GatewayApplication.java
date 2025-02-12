package com.example.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator ecomRouteLocator(RouteLocatorBuilder builder) {
        System.out.println("게이트웨이에서 개별 서비스 URL 등록");
        return builder.routes()
                // 개별 라우트 등록
                // 서비스별 URL 별칭이 1개인 경우, n개인 경우도 존재
                .route("auth",
                        r -> r.path("/auth/**").uri("lb://auth"))
                .route("auth",
                        r -> r.path("/user/**").uri("lb://auth"))
                .route("auth",
                        r -> r.path("/admin/**").uri("lb://auth"))
                .route("post",
                        r -> r.path("/post/**").uri("lb://post"))
                .route("post",
                        r -> r.path("/comment/**").uri("lb://post"))
                .route("msa-sb-item",
                        r -> r.path("/item/**").uri("lb://msa-sb-item"))
                .route("msa-sb-item",
                        r -> r.path("/cart/**").uri("lb://msa-sb-item"))
                .route("msa-sb-item",
                        r -> r.path("/order/**").uri("lb://msa-sb-item"))
                .build();
    }

}
