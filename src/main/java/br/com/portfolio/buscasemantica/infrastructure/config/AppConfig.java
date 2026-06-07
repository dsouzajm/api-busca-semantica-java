package br.com.portfolio.buscasemantica.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.Executors;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    /**
     * Configura executor com Virtual Threads para tarefas assíncronas explícitas.
     * O suporte principal a Virtual Threads no Tomcat é ativado via application.yml
     * com spring.threads.virtual.enabled=true
     */
    @Bean
    public java.util.concurrent.ExecutorService virtualThreadExecutor() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }
}
