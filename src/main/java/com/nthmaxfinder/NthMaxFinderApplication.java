package com.nthmaxfinder;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(title = "NthMaxFinder API", version = "1.0"))
@SpringBootApplication
public class NthMaxFinderApplication {
    public static void main(String[] args) {
        SpringApplication.run(NthMaxFinderApplication.class, args);
    }
}
