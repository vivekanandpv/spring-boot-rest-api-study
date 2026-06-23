package dev.vivekanand.massmutualspringday2.mappers;

import dev.vivekanand.massmutualspringday2.dtos.ProductCreateDto;
import dev.vivekanand.massmutualspringday2.dtos.ProductDto;
import dev.vivekanand.massmutualspringday2.dtos.ProductUpdateDto;
import dev.vivekanand.massmutualspringday2.entities.Product;
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
