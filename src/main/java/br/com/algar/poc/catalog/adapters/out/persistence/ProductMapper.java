package br.com.algar.poc.catalog.adapters.out.persistence;

import br.com.algar.poc.catalog.entities.Product;
import br.com.algar.poc.catalog.entities.ProductId;
import br.com.algar.poc.catalog.entities.Sku;

final class ProductMapper {

    private ProductMapper() {
    }

    static ProductJpaEntity toEntity(Product product) {
        return new ProductJpaEntity(
                product.id().value(),
                product.sku().value(),
                product.name(),
                product.price());
    }

    static Product toDomain(ProductJpaEntity entity) {
        return Product.register(
                ProductId.of(entity.getId()),
                Sku.of(entity.getSku()),
                entity.getName(),
                entity.getPrice());
    }
}
