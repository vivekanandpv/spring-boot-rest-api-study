package dev.vivekanand.massmutualspringday2.apis;

import dev.vivekanand.massmutualspringday2.dtos.ProductCreateDto;
import dev.vivekanand.massmutualspringday2.dtos.ProductDto;
import dev.vivekanand.massmutualspringday2.dtos.ProductUpdateDto;
import dev.vivekanand.massmutualspringday2.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/products")
public class ProductApi {
    private final ProductService productService;

    public ProductApi(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAll() {

        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductDto> getById(@PathVariable long id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    @PostMapping
    public ResponseEntity<ProductDto> create(@Valid @RequestBody ProductCreateDto dto) {
        return ResponseEntity.ok(productService.create(dto));
    }

    @PutMapping("{id}")
    public ResponseEntity<ProductDto> update(@PathVariable long id, @Valid @RequestBody ProductUpdateDto dto) {
        return ResponseEntity.ok(productService.update(id, dto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
        //return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//        return ResponseEntity.ok(
//          Map.of("message", "deleted product id: " + id)
//        );
    }
}
