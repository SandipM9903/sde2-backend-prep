package com.example.virtual_thread_demo.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThreadConfig {

    @Bean
    public ExecutorService normalExecutor() {

        return Executors.newFixedThreadPool(100);
    }

    @Bean
    public ExecutorService virtualExecutor() {

        return Executors.newVirtualThreadPerTaskExecutor();
    }
}