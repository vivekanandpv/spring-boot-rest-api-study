package dev.vivekanand.massmutualspringday2.services;

import dev.vivekanand.massmutualspringday2.dtos.ProductCreateDto;
import dev.vivekanand.massmutualspringday2.dtos.ProductDto;
import dev.vivekanand.massmutualspringday2.dtos.ProductUpdateDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImplementation implements ProductService {
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
