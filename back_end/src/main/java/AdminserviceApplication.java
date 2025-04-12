package com.example.adminservice;

import com.example.feign_api.clients.CustomerClients;
import com.example.feign_api.config.DefaultFeignConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(clients = CustomerClients.class,defaultConfiguration = DefaultFeignConfiguration.class)
//@EnableFeignClients(basePackages = "com.example.feign_api.clients")

public class AdminserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminserviceApplication.class, args);
    }

}
