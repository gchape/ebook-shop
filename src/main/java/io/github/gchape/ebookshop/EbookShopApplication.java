package io.github.gchape.ebookshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication(scanBasePackages = "io.github.gchape.ebookshop.services")
@ServletComponentScan(basePackages = "io.github.gchape.ebookshop.servlets")
@EntityScan(basePackages = "io.github.gchape.ebookshop.entities")
public class EbookShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbookShopApplication.class, args);
    }
}
