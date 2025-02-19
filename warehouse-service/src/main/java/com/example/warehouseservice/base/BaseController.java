package com.example.warehouseservice.base;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public class BaseController<T, ID> {

    private BaseService<T, ID> baseService;

    protected void setService(BaseService<T, ID> service) {
        this.baseService = service;
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable ID id) {
        return baseService.getDetail(id);
    }

    @GetMapping("/paging")
    public ResponseEntity<?> getPaging(T entity, Pageable pageable) {
        return baseService.getAllPagination(entity, pageable);
    }
}
