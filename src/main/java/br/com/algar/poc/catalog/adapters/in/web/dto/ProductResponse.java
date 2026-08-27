package br.com.algar.poc.catalog.adapters.in.web.dto;

import br.com.algar.poc.catalog.entities.Product;

import java.math.BigDecimal;

public record ProductResponse(String id, String sku, String name, BigDecimal price) {

    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.id().toString(),
                product.sku().value(),
                product.name(),
                product.price());
    }
}
