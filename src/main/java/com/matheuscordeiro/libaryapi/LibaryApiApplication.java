package com.matheuscordeiro.libaryapi;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class LibaryApiApplication {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Scheduled(cron = "0 0 16 1/1 * ?")
    public void taskScheduling() {
        System.out.println("Scheduling ok");
    }

	public static void main(String[] args) {
		SpringApplication.run(LibaryApiApplication.class, args);
	}

}
