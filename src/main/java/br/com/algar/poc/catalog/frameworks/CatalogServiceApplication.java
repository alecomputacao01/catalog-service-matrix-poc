package br.com.algar.poc.catalog.frameworks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Anel "frameworks" (o mais externo) — bootstrap da aplicação.
 *
 * Achado de execução (só apareceu em CI real, com Testcontainers): {@code scanBasePackages} no
 * {@code @SpringBootApplication} controla o component scan (@Component/@Configuration/@RestController),
 * mas NÃO o scan de repositórios Spring Data JPA — {@code @EnableJpaRepositories} usa por padrão o
 * pacote da própria classe principal ({@code frameworks}), e nunca encontraria
 * {@code ProductJpaRepository} em {@code adapters.out.persistence} (pacote irmão, não descendente).
 * Precisa ser declarado explicitamente. Mesma coisa vale para {@code @EntityScan}
 * (ProductJpaEntity, também em adapters.out.persistence).
 */
@SpringBootApplication(scanBasePackages = "br.com.algar.poc.catalog")
@EnableJpaRepositories(basePackages = "br.com.algar.poc.catalog")
@EntityScan(basePackages = "br.com.algar.poc.catalog")
public class CatalogServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CatalogServiceApplication.class, args);
    }
}
