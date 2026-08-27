package br.com.algar.poc.catalog.adapters.in.web;

import br.com.algar.poc.catalog.adapters.in.web.dto.ProductRequest;
import br.com.algar.poc.catalog.adapters.in.web.dto.ProductResponse;
import br.com.algar.poc.catalog.usecases.RegisterProductUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Interface adapter (anel "adapters") — só orquestra, chama o caso de uso via sua interface.
 */
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final RegisterProductUseCase registerProductUseCase;

    public ProductController(RegisterProductUseCase registerProductUseCase) {
        this.registerProductUseCase = registerProductUseCase;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> register(@Valid @RequestBody ProductRequest request) {
        var product = registerProductUseCase.register(request.sku(), request.name(), request.price());
        return ResponseEntity.status(HttpStatus.CREATED).body(ProductResponse.from(product));
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public void handleConflict() {
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleBadRequest() {
    }
}
