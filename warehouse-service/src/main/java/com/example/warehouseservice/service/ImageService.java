package com.example.warehouseservice.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.UnsupportedEncodingException;

public interface ImageService {

    ResponseEntity<Object> displayImage(@PathVariable(value = "seq") String seq) throws UnsupportedEncodingException;

    ResponseEntity<Object> downloadImg(@PathVariable(value = "seq") String seq) throws UnsupportedEncodingException;

}
