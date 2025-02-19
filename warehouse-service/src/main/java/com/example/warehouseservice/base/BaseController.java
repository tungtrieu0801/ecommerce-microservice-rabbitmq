package com.example.warehouseservice.base;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public class BaseController<T, ID> {

    private BaseService<T, ID> baseService;

    protected void setService(BaseService<T, ID> service) {
        this.baseService = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable ID id) {
        return baseService.getDetail(id);
    }

    @GetMapping("/paging")
    public ResponseEntity<?> getPaging(T entity, Pageable pageable) {
        return baseService.getAllPagination(entity, pageable);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable ID id) {
        return baseService.delete(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable ID id, @RequestBody T entity) {
        return baseService.update(entity, id);
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody T entity) {
        return baseService.add(entity);
    }
}
