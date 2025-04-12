package com.example.shopservice;

import com.example.feign_api.clients.CustomerClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(clients = {CustomerClients.class})

public class ShopserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopserviceApplication.class, args);
    }

}
