package dev.vivekanand.massmutualspringday2.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ProductUpdateDto {
    @NotBlank
    @Size(max = 200)
    private String description;

    @NotBlank
    @Size(min = 10, max = 250)
    private String storeInformation;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStoreInformation() {
        return storeInformation;
    }

    public void setStoreInformation(String storeInformation) {
        this.storeInformation = storeInformation;
    }
}
