package com.example.warehouseservice.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BaseResponse {

    private List<?> dataList;

    private Object data;

    private String message;
}
