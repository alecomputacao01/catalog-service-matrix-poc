package br.com.algar.poc.catalog.adapters.out.persistence;

import br.com.algar.poc.catalog.entities.Product;
import br.com.algar.poc.catalog.entities.ProductId;
import br.com.algar.poc.catalog.entities.Sku;
import br.com.algar.poc.catalog.usecases.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

/** Gateway (interface adapter) que implementa o boundary definido em usecases.ProductRepository. */
@Component
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository jpaRepository;

    public ProductRepositoryImpl(ProductJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Product save(Product product) {
        var saved = jpaRepository.save(ProductMapper.toEntity(product));
        return ProductMapper.toDomain(saved);
    }

    @Override
    public Optional<Product> findById(ProductId id) {
        return jpaRepository.findById(id.value()).map(ProductMapper::toDomain);
    }

    @Override
    public boolean existsBySku(Sku sku) {
        return jpaRepository.existsBySku(sku.value());
    }
}
