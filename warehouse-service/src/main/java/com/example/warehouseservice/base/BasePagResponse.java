package com.example.warehouseservice.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BasePagResponse extends BaseResponse{

    private int currentPage;

    private Long totalItems;

    private int totalPages;

}
