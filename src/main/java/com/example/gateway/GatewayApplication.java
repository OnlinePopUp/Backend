package com.example.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {

    @Value("${gateway.itemUrl}")
    private String itemUrl;

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator ecomRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // 개별 라우트 등록
                // 서비스별 URL 별칭이 1개인 경우, n개인 경우도 존재
                .route("auth",
                        r -> r.path("/auth/**").uri("lb://auth"))
                .route("auth",
                        r -> r.path("/user/**").uri("lb://auth"))
                .route("auth",
                        r -> r.path("/admin/**").uri("lb://auth"))
                .route("auth",
                        r -> r.path("/chat/**").uri("lb://auth"))
                /*.route("auth",
                        r -> r.path("/ws/**").uri("lb://auth"))
                .route("auth",
                        r -> r.path("/pub/**").uri("lb://auth"))
                .route("auth",
                        r -> r.path("/sub/**").uri("lb://auth"))*/

                .route("post",
                        r -> r.path("/post/**").uri("lb://post"))
                .route("post",
                        r -> r.path("/comment/**").uri("lb://post"))

                .route("msa-sb-item",
                        r -> r.path("/item/**").uri(itemUrl)) // 다른 리전이라 퍼블릭 ip로 라우팅
                .route("msa-sb-item",
                        r -> r.path("/cart/**").uri(itemUrl))
                .route("msa-sb-item",
                        r -> r.path("/order/**").uri(itemUrl))
                .build();
    }

}
