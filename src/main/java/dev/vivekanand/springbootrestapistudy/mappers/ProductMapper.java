package dev.vivekanand.springbootrestapistudy.mappers;

import dev.vivekanand.springbootrestapistudy.dtos.ProductCreateDto;
import dev.vivekanand.springbootrestapistudy.dtos.ProductDto;
import dev.vivekanand.springbootrestapistudy.dtos.ProductUpdateDto;
import dev.vivekanand.springbootrestapistudy.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toEntity(ProductCreateDto dto);
    ProductDto toDto(Product entity);
    List<ProductDto> toDtoList(List<Product> products);
    void updateEntity(ProductUpdateDto dto, @MappingTarget Product entity);
}
