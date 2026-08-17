package dev.vivekanand.springbootrestapistudy.services;

import dev.vivekanand.springbootrestapistudy.dtos.ProductCreateDto;
import dev.vivekanand.springbootrestapistudy.dtos.ProductDto;
import dev.vivekanand.springbootrestapistudy.dtos.ProductUpdateDto;
import dev.vivekanand.springbootrestapistudy.entities.Product;
import dev.vivekanand.springbootrestapistudy.exceptions.ResourceNotFoundException;
import dev.vivekanand.springbootrestapistudy.mappers.ProductMapper;
import dev.vivekanand.springbootrestapistudy.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplementationTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductMapper productMapper;
    private ProductServiceImplementation service;

    @BeforeEach
    void setUp() {
        service = new ProductServiceImplementation(productRepository, productMapper);
    }

    @Test
    void getAllMapsRepositoryResults() {
        // Verify that all persisted entities are fetched and mapped to DTOs.
        List<Product> entities = List.of(new Product());
        List<ProductDto> dtos = List.of(new ProductDto());
        when(productRepository.findAll()).thenReturn(entities);
        when(productMapper.toDtoList(entities)).thenReturn(dtos);

        assertThat(service.getAll()).isSameAs(dtos);
        verify(productRepository).findAll();
        verify(productMapper).toDtoList(entities);
    }

    @Test
    void getByIdMapsExistingEntity() {
        // Verify that an existing entity is found and converted to its DTO.
        Product entity = new Product();
        ProductDto dto = new ProductDto();
        when(productRepository.findById(3L)).thenReturn(Optional.of(entity));
        when(productMapper.toDto(entity)).thenReturn(dto);

        assertThat(service.getById(3L)).isSameAs(dto);
        verify(productMapper).toDto(entity);
    }

    @Test
    void getByIdThrowsWhenEntityDoesNotExist() {
        // Verify that an absent entity produces a descriptive not-found exception.
        when(productRepository.findById(404L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(404L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Product not found: 404");
        verifyNoInteractions(productMapper);
    }

    @Test
    void createMapsSavesAndReturnsProduct() {
        // Verify that creation maps the request, flushes it, and maps the saved entity.
        ProductCreateDto request = new ProductCreateDto();
        Product entity = new Product();
        Product saved = new Product();
        ProductDto dto = new ProductDto();
        when(productMapper.toEntity(request)).thenReturn(entity);
        when(productRepository.saveAndFlush(entity)).thenReturn(saved);
        when(productMapper.toDto(saved)).thenReturn(dto);

        assertThat(service.create(request)).isSameAs(dto);
        verify(productMapper).toEntity(request);
        verify(productRepository).saveAndFlush(entity);
        verify(productMapper).toDto(saved);
    }

    @Test
    void updateMutatesSavesAndReturnsExistingProduct() {
        // Verify that an existing entity is updated, flushed, and converted to a DTO.
        ProductUpdateDto request = new ProductUpdateDto();
        Product entity = new Product();
        ProductDto dto = new ProductDto();
        when(productRepository.findById(5L)).thenReturn(Optional.of(entity));
        when(productRepository.saveAndFlush(entity)).thenReturn(entity);
        when(productMapper.toDto(entity)).thenReturn(dto);

        assertThat(service.update(5L, request)).isSameAs(dto);
        verify(productMapper).updateEntity(request, entity);
        verify(productRepository).saveAndFlush(entity);
    }

    @Test
    void updateThrowsWhenEntityDoesNotExist() {
        // Verify that updates cannot proceed when the requested entity is absent.
        when(productRepository.findById(6L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(6L, new ProductUpdateDto()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Product not found: 6");
        verify(productRepository).findById(6L);
        verifyNoMoreInteractions(productRepository);
        verifyNoInteractions(productMapper);
    }

    @Test
    void deleteRemovesExistingEntity() {
        // Verify that deletion resolves the entity first and passes that entity to the repository.
        Product entity = new Product();
        when(productRepository.findById(10L)).thenReturn(Optional.of(entity));

        service.deleteById(10L);

        verify(productRepository).findById(10L);
        verify(productRepository).delete(entity);
    }

    @Test
    void deleteThrowsWhenEntityDoesNotExist() {
        // Verify that deletion does not call the repository delete operation for a missing entity.
        when(productRepository.findById(11L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.deleteById(11L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Product not found: 11");
        verify(productRepository).findById(11L);
        verifyNoMoreInteractions(productRepository);
    }
}
