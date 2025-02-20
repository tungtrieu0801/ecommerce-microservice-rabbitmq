package com.example.warehouseservice.payload.request;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandRequest {
    @JsonProperty("name") // Đảm bảo JSON được parse đúng
    private String name;

    @JsonProperty("description")
    private String description;
}
