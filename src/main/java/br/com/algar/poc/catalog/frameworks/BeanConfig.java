package br.com.algar.poc.catalog.frameworks;

import br.com.algar.poc.catalog.usecases.ProductRepository;
import br.com.algar.poc.catalog.usecases.RegisterProductService;
import br.com.algar.poc.catalog.usecases.RegisterProductUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Composition root (anel "frameworks") — liga o caso de uso à sua implementação. RegisterProductService
 * (em usecases) permanece livre de anotações Spring, de propósito.
 */
@Configuration
public class BeanConfig {

    @Bean
    public RegisterProductUseCase registerProductUseCase(ProductRepository productRepository) {
        return new RegisterProductService(productRepository);
    }
}
