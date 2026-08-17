package dev.vivekanand.springbootrestapistudy.services;

import dev.vivekanand.springbootrestapistudy.dtos.ProductCreateDto;
import dev.vivekanand.springbootrestapistudy.dtos.ProductDto;
import dev.vivekanand.springbootrestapistudy.dtos.ProductUpdateDto;
import dev.vivekanand.springbootrestapistudy.entities.Product;
import dev.vivekanand.springbootrestapistudy.exceptions.ResourceNotFoundException;
import dev.vivekanand.springbootrestapistudy.mappers.ProductMapper;
import dev.vivekanand.springbootrestapistudy.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImplementation implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImplementation(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductDto> getAll() {
        return productMapper.toDtoList(
                productRepository.findAll() //  all products without filtration

//                productRepository.findAll()
//                        .stream().filter(p -> p.getName().startsWith("Laptop"))
//                        .toList()   // stream api approach

                // productRepository.findProductByNameStartingWith("Laptop")   //  automatic conventional query method
                // productRepository.foo("Laptop") // JPQL query
                // productRepository.bar("Laptop") // native SQL query
        );
    }

    @Override
    public ProductDto getById(long id) {
        return productMapper.toDto(getEntityById(id));
    }

    @Transactional
    @Override
    public ProductDto create(ProductCreateDto dto) {
        Product product = productMapper.toEntity(dto);
        Product productSaved = productRepository.saveAndFlush(product);
        return productMapper.toDto(productSaved);
        //  shorter version
        // return productMapper.toDto(productRepository.saveAndFlush(productMapper.toEntity(dto)));
    }

    @Transactional
    @Override
    public ProductDto update(long id, ProductUpdateDto dto) {
        Product productDb = getEntityById(id);
        productMapper.updateEntity(dto, productDb);
        Product productSaved = productRepository.saveAndFlush(productDb);
        return productMapper.toDto(productSaved);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        productRepository.delete(getEntityById(id));
    }

    private Product getEntityById(long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
    }
}
