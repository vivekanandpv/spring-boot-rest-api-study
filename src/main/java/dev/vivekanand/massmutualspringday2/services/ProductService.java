package dev.vivekanand.massmutualspringday2.services;

import dev.vivekanand.massmutualspringday2.dtos.ProductCreateDto;
import dev.vivekanand.massmutualspringday2.dtos.ProductDto;
import dev.vivekanand.massmutualspringday2.dtos.ProductUpdateDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> getAll();
    ProductDto getById(long id);
    ProductDto create(ProductCreateDto dto);
    ProductDto update(long id, ProductUpdateDto dto);
    void deleteById(long id);
}
