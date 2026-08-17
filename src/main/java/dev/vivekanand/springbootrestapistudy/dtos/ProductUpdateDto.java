package dev.vivekanand.springbootrestapistudy.dtos;

public class ProductUpdateDto {
    private String description;
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
