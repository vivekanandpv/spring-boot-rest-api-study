package dev.vivekanand.springbootrestapistudy.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ProductCreateDto {
    @NotBlank
    @Size(min = 3, max = 100)
    private String name;

    @NotBlank
    @Size(max = 200)
    private String description;

    @NotBlank
    @Pattern(regexp = "^[A-Z]{5}[0-9]{5}$")
    private String sku;

    @NotBlank
    @Size(min = 10, max = 250)
    private String storeInformation;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getStoreInformation() {
        return storeInformation;
    }

    public void setStoreInformation(String storeInformation) {
        this.storeInformation = storeInformation;
    }
}
