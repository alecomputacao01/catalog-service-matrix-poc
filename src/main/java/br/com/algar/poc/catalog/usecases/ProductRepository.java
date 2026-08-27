package br.com.algar.poc.catalog.usecases;

import br.com.algar.poc.catalog.entities.Product;
import br.com.algar.poc.catalog.entities.ProductId;
import br.com.algar.poc.catalog.entities.Sku;

import java.util.Optional;

/**
 * "Boundary" do caso de uso — mora em "usecases", não em "entities" (inversão de dependência: a
 * implementação concreta em adapters/frameworks é que depende desta interface, nunca o contrário).
 */
public interface ProductRepository {

    Product save(Product product);

    Optional<Product> findById(ProductId id);

    boolean existsBySku(Sku sku);
}
