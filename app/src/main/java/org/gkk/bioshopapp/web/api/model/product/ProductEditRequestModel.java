package org.gkk.bioshopapp.web.api.model.product;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

public class ProductEditRequestModel implements Serializable {
    private String name;

    private String description;

    private String imgUrl;

    private BigDecimal price;

    public ProductEditRequestModel() {
    }

    @NotNull
    @NotBlank
    @Size(min = 3, message = "Name size have to be min 3 characters.")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @NotBlank
    @Size(min = 10, message = "Description size have to be min 10 characters.")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull
    @NotBlank
    @Pattern(regexp = "(http(s?):)([/|.|\\w|\\s|-])*\\.(?:jpg|gif|png)")
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @NotNull
    @DecimalMin(value = "0.1", message = "Must be greater than or equal to 0.1.")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
