package dev.vivekanand.springbootrestapistudy.mappers;

import dev.vivekanand.springbootrestapistudy.dtos.ProductCreateDto;
import dev.vivekanand.springbootrestapistudy.dtos.ProductDto;
import dev.vivekanand.springbootrestapistudy.dtos.ProductUpdateDto;
import dev.vivekanand.springbootrestapistudy.entities.Product;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProductMapperTest {
    private final ProductMapper mapper = Mappers.getMapper(ProductMapper.class);

    @Test
    void mapsCreateDtoToEntity() {
        // Verify that all create DTO fields are copied to a new entity.
        ProductCreateDto dto = createDto();

        Product entity = mapper.toEntity(dto);

        assertThat(entity.getName()).isEqualTo("Keyboard");
        assertThat(entity.getDescription()).isEqualTo("Mechanical keyboard");
        assertThat(entity.getSku()).isEqualTo("ABCDE12345");
        assertThat(entity.getStoreInformation()).isEqualTo("Available online and in store");
    }

    @Test
    void returnsNullWhenCreateDtoIsNull() {
        // Verify that a null create DTO is handled without creating an entity.
        assertThat(mapper.toEntity(null)).isNull();
    }

    @Test
    void mapsEntityToDto() {
        // Verify that all entity fields, including the identifier, are copied to a DTO.
        Product entity = product();

        ProductDto dto = mapper.toDto(entity);

        assertThat(dto.getId()).isEqualTo(12L);
        assertThat(dto.getName()).isEqualTo(entity.getName());
        assertThat(dto.getDescription()).isEqualTo(entity.getDescription());
        assertThat(dto.getSku()).isEqualTo(entity.getSku());
        assertThat(dto.getStoreInformation()).isEqualTo(entity.getStoreInformation());
    }

    @Test
    void returnsNullWhenEntityIsNull() {
        // Verify that a null entity is handled without creating a DTO.
        assertThat(mapper.toDto(null)).isNull();
    }

    @Test
    void mapsEntityListToDtoList() {
        // Verify that list mapping preserves order and maps every entity.
        List<ProductDto> result = mapper.toDtoList(List.of(product(), product()));

        assertThat(result).hasSize(2);
        assertThat(result).allSatisfy(dto -> assertThat(dto.getId()).isEqualTo(12L));
    }

    @Test
    void returnsNullWhenEntityListIsNull() {
        // Verify that a null entity list is handled without creating a result list.
        assertThat(mapper.toDtoList(null)).isNull();
    }

    @Test
    void mapsNullEntityEntryToNullDto() {
        // Verify that a null entry in an entity list is preserved as a null DTO entry.
        List<Product> entities = new ArrayList<>();
        entities.add(null);

        assertThat(mapper.toDtoList(entities)).containsExactly((ProductDto) null);
    }

    @Test
    void updatesOnlyUpdateDtoFields() {
        // Verify that an update changes description and store information while retaining other fields.
        Product entity = product();
        ProductUpdateDto dto = new ProductUpdateDto();
        dto.setDescription("Updated description");
        dto.setStoreInformation("Updated store information");

        mapper.updateEntity(dto, entity);

        assertThat(entity.getId()).isEqualTo(12L);
        assertThat(entity.getName()).isEqualTo("Keyboard");
        assertThat(entity.getSku()).isEqualTo("ABCDE12345");
        assertThat(entity.getDescription()).isEqualTo("Updated description");
        assertThat(entity.getStoreInformation()).isEqualTo("Updated store information");
    }

    @Test
    void ignoresNullUpdateDto() {
        // Verify that a null update DTO leaves the target entity unchanged.
        Product entity = product();

        mapper.updateEntity(null, entity);

        assertThat(entity.getDescription()).isEqualTo("Mechanical keyboard");
        assertThat(entity.getStoreInformation()).isEqualTo("Available online and in store");
    }

    private static ProductCreateDto createDto() {
        ProductCreateDto dto = new ProductCreateDto();
        dto.setName("Keyboard");
        dto.setDescription("Mechanical keyboard");
        dto.setSku("ABCDE12345");
        dto.setStoreInformation("Available online and in store");
        return dto;
    }

    private static Product product() {
        Product product = new Product();
        product.setId(12L);
        product.setName("Keyboard");
        product.setDescription("Mechanical keyboard");
        product.setSku("ABCDE12345");
        product.setStoreInformation("Available online and in store");
        return product;
    }
}
