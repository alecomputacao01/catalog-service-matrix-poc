package br.com.algar.poc.catalog.adapters.out.persistence;

import br.com.algar.poc.catalog.entities.Product;
import br.com.algar.poc.catalog.entities.ProductId;
import br.com.algar.poc.catalog.entities.Sku;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Teste de integração real contra PostgreSQL via Testcontainers. Não roda no sandbox local usado
 * para implementar esta PoC (limitação de versão de API do Docker documentada no
 * RELATORIO-EXECUCAO.md da raiz do projeto) — validado de verdade no GitHub Actions.
 */
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ProductRepositoryImplIT {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("catalog")
            .withUsername("catalog")
            .withPassword("catalog");

    @DynamicPropertySource
    static void datasourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private ProductRepositoryImpl repository;

    @Test
    void deveSalvarERecuperarProdutoPorId() {
        var product = Product.register(ProductId.newId(), Sku.of("ABC-1234"), "Notebook", new BigDecimal("100.00"));

        repository.save(product);

        var found = repository.findById(product.id());
        assertThat(found).isPresent();
        assertThat(found.get().sku()).isEqualTo(product.sku());
        assertThat(found.get().name()).isEqualTo("Notebook");
    }

    @Test
    void existsBySkuDeveRefletirOEstadoPersistido() {
        var sku = Sku.of("XYZ-9999");
        assertThat(repository.existsBySku(sku)).isFalse();

        repository.save(Product.register(ProductId.newId(), sku, "Mouse", new BigDecimal("50.00")));

        assertThat(repository.existsBySku(sku)).isTrue();
    }
}
