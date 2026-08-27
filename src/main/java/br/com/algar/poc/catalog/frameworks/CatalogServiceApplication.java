package br.com.algar.poc.catalog.frameworks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** Anel "frameworks" (o mais externo) — bootstrap da aplicação. */
@SpringBootApplication(scanBasePackages = "br.com.algar.poc.catalog")
public class CatalogServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CatalogServiceApplication.class, args);
    }
}
