package dev.vivekanand.springbootrestapistudy.services;

import dev.vivekanand.springbootrestapistudy.dtos.ProductCreateDto;
import dev.vivekanand.springbootrestapistudy.dtos.ProductDto;
import dev.vivekanand.springbootrestapistudy.dtos.ProductUpdateDto;
import dev.vivekanand.springbootrestapistudy.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImplementation implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImplementation(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDto> getAll() {
        return List.of();
    }

    @Override
    public ProductDto getById(long id) {
        return null;
    }

    @Override
    public ProductDto create(ProductCreateDto dto) {
        return null;
    }

    @Override
    public ProductDto update(long id, ProductUpdateDto dto) {
        return null;
    }

    @Override
    public void deleteById(long id) {

    }
}
