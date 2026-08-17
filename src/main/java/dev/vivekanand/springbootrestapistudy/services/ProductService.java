package dev.vivekanand.springbootrestapistudy.services;

import dev.vivekanand.springbootrestapistudy.dtos.ProductCreateDto;
import dev.vivekanand.springbootrestapistudy.dtos.ProductDto;
import dev.vivekanand.springbootrestapistudy.dtos.ProductUpdateDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> getAll();
    ProductDto getById(long id);
    ProductDto create(ProductCreateDto dto);
    ProductDto update(long id, ProductUpdateDto dto);
    void deleteById(long id);
}
