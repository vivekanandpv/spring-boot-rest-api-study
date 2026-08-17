package dev.vivekanand.springbootrestapistudy.model;

import dev.vivekanand.springbootrestapistudy.dtos.ProductCreateDto;
import dev.vivekanand.springbootrestapistudy.dtos.ProductDto;
import dev.vivekanand.springbootrestapistudy.dtos.ProductUpdateDto;
import dev.vivekanand.springbootrestapistudy.entities.Product;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DtoAndEntityAccessorTest {

    @Test
    void productCreateDtoStoresAllFields() {
        // Verify that ProductCreateDto setters and getters round-trip every field.
        ProductCreateDto dto = new ProductCreateDto();
        dto.setName("name");
        dto.setDescription("description");
        dto.setSku("sku");
        dto.setStoreInformation("store");

        assertThat(dto.getName()).isEqualTo("name");
        assertThat(dto.getDescription()).isEqualTo("description");
        assertThat(dto.getSku()).isEqualTo("sku");
        assertThat(dto.getStoreInformation()).isEqualTo("store");
    }

    @Test
    void productDtoStoresAllFields() {
        // Verify that ProductDto setters and getters round-trip every field.
        ProductDto dto = new ProductDto();
        dto.setId(1L);
        dto.setName("name");
        dto.setDescription("description");
        dto.setSku("sku");
        dto.setStoreInformation("store");

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("name");
        assertThat(dto.getDescription()).isEqualTo("description");
        assertThat(dto.getSku()).isEqualTo("sku");
        assertThat(dto.getStoreInformation()).isEqualTo("store");
    }

    @Test
    void productUpdateDtoStoresAllFields() {
        // Verify that ProductUpdateDto setters and getters round-trip every field.
        ProductUpdateDto dto = new ProductUpdateDto();
        dto.setDescription("description");
        dto.setStoreInformation("store");

        assertThat(dto.getDescription()).isEqualTo("description");
        assertThat(dto.getStoreInformation()).isEqualTo("store");
    }

    @Test
    void productStoresAllFields() {
        // Verify that Product setters and getters round-trip every persisted field.
        Product product = new Product();
        product.setId(1L);
        product.setName("name");
        product.setDescription("description");
        product.setSku("sku");
        product.setStoreInformation("store");

        assertThat(product.getId()).isEqualTo(1L);
        assertThat(product.getName()).isEqualTo("name");
        assertThat(product.getDescription()).isEqualTo("description");
        assertThat(product.getSku()).isEqualTo("sku");
        assertThat(product.getStoreInformation()).isEqualTo("store");
    }
}
