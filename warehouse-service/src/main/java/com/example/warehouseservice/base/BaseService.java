package com.example.warehouseservice.base;

import com.example.warehouseservice.constants.ErrorMessage;
import com.example.warehouseservice.constants.SuccessMessage;
import com.example.warehouseservice.exception.MessageException;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseService <T, ID> {

    private JpaRepository<T, ID> repository;

    protected void setRepository(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    public ResponseEntity<?> getDetail(ID id) {
        try {
            T entity = repository.findById(id).orElseThrow(() -> new MessageException(ErrorMessage.ERROR, HttpStatus.NOT_FOUND));
            return ResponseEntity.status(HttpStatus.OK).body(entity);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(BaseResponse.builder().message(ErrorMessage.NOT_FOUND).build());
        }
    }

    public ResponseEntity<?> getAllPagination(T entity, Pageable pageable) {
        try {
            if (pageable == null) {
                pageable = PageRequest.of(0, 10, Sort.by("name"));
            }

            Page<T> listResult = repository.findAll(Example.of(entity), pageable);

            BasePagResponse basePagResponse = new BasePagResponse();
            basePagResponse.setTotalPages(listResult.getTotalPages());
            basePagResponse.setTotalItems(listResult.getTotalElements());
            basePagResponse.setCurrentPage(listResult.getNumber());
            basePagResponse.setData(listResult.getContent());
            basePagResponse.setMessage(SuccessMessage.SUCCESS);

            return ResponseEntity.status(HttpStatus.OK).body(basePagResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BaseResponse.builder().message(ErrorMessage.ERROR).build());
        }
    }
}
