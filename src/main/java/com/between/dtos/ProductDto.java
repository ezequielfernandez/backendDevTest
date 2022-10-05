package com.between.dtos;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class ProductDto  implements Serializable {
    private String id;
    private String name;
    private double price;
    private boolean availability;

    @JsonGetter
    public String getPrice() {
        return String.format("%.2f",this.price);
    }
}
