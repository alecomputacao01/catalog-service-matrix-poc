package br.com.algar.poc.catalog.entities;

import java.util.Objects;
import java.util.UUID;

/** Value Object — identidade da entidade {@link Product}. Anel "entities" (o mais interno). */
public final class ProductId {

    private final UUID value;

    private ProductId(UUID value) {
        this.value = value;
    }

    public static ProductId newId() {
        return new ProductId(UUID.randomUUID());
    }

    public static ProductId of(UUID value) {
        Objects.requireNonNull(value, "id do produto não pode ser nulo");
        return new ProductId(value);
    }

    public static ProductId of(String value) {
        return of(UUID.fromString(value));
    }

    public UUID value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductId other)) return false;
        return value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
