package br.com.algar.poc.catalog.usecases;

import br.com.algar.poc.catalog.entities.Product;

import java.math.BigDecimal;

/** Caso de uso (application business rule) — único ponto de entrada para registrar um produto. */
public interface RegisterProductUseCase {

    Product register(String sku, String name, BigDecimal price);
}
