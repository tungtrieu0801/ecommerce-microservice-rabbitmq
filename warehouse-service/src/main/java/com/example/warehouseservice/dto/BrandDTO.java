package com.example.warehouseservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BrandDTO {

    private UUID id;

    private String name;

    private String description;

    private ImageDTO image;
}
