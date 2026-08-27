package br.com.algar.poc.catalog.usecases;

import br.com.algar.poc.catalog.entities.Product;
import br.com.algar.poc.catalog.entities.ProductId;
import br.com.algar.poc.catalog.entities.Sku;

import java.math.BigDecimal;

/**
 * Implementa o caso de uso, orquestrando a entidade e o boundary de repositório — não depende de
 * nenhum adapter/framework concreto (fiscalizado por arch.CleanArchitectureTest).
 */
public class RegisterProductService implements RegisterProductUseCase {

    private final ProductRepository repository;

    public RegisterProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product register(String skuValue, String name, BigDecimal price) {
        var sku = Sku.of(skuValue);
        if (repository.existsBySku(sku)) {
            throw new IllegalStateException("SKU já cadastrado: " + skuValue);
        }
        var product = Product.register(ProductId.newId(), sku, name, price);
        return repository.save(product);
    }
}
