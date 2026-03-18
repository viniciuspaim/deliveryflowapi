package dev.viniciuspaim.minimalapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class MinimalApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MinimalApiApplication.class, args);
    }

}
