package dev.vivekanand.springbootrestapistudy.apis;

import dev.vivekanand.springbootrestapistudy.dtos.ProductCreateDto;
import dev.vivekanand.springbootrestapistudy.dtos.ProductDto;
import dev.vivekanand.springbootrestapistudy.dtos.ProductUpdateDto;
import dev.vivekanand.springbootrestapistudy.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductApiTest {
    @Mock
    private ProductService productService;
    private ProductApi productApi;

    @BeforeEach
    void setUp() {
        productApi = new ProductApi(productService);
    }

    @Test
    void getAllReturnsProductsWithOkStatus() {
        // Verify that the collection endpoint delegates and returns an HTTP 200 response.
        List<ProductDto> products = List.of(new ProductDto());
        when(productService.getAll()).thenReturn(products);

        ResponseEntity<List<ProductDto>> response = productApi.getAll();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isSameAs(products);
        verify(productService).getAll();
    }

    @Test
    void getByIdReturnsProductWithOkStatus() {
        // Verify that the item endpoint delegates the requested identifier and returns HTTP 200.
        ProductDto product = new ProductDto();
        when(productService.getById(7L)).thenReturn(product);

        ResponseEntity<ProductDto> response = productApi.getById(7L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isSameAs(product);
        verify(productService).getById(7L);
    }

    @Test
    void createReturnsCreatedProductWithOkStatus() {
        // Verify that the create endpoint passes the request DTO to the service.
        ProductCreateDto request = new ProductCreateDto();
        ProductDto product = new ProductDto();
        when(productService.create(request)).thenReturn(product);

        ResponseEntity<ProductDto> response = productApi.create(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isSameAs(product);
        verify(productService).create(request);
    }

    @Test
    void updateReturnsUpdatedProductWithOkStatus() {
        // Verify that the update endpoint delegates both the identifier and update DTO.
        ProductUpdateDto request = new ProductUpdateDto();
        ProductDto product = new ProductDto();
        when(productService.update(8L, request)).thenReturn(product);

        ResponseEntity<ProductDto> response = productApi.update(8L, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isSameAs(product);
        verify(productService).update(8L, request);
    }

    @Test
    void deleteReturnsNoContentAndDelegatesIdentifier() {
        // Verify that deletion delegates once and returns an HTTP 204 response without a body.
        ResponseEntity<Void> response = productApi.deleteById(9L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response.getBody()).isNull();
        verify(productService).deleteById(9L);
    }
}
