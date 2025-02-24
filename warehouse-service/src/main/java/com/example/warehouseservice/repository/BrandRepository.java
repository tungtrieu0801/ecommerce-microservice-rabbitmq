package com.example.warehouseservice.repository;

import com.example.warehouseservice.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface BrandRepository extends JpaRepository<Brand, UUID> {

//    @Query("SELECT new com.example.warehouseservice.dto.BrandDTO(b.id, b.name, b.description, " +
//            "new com.example.warehouseservice.dto.ImageDTO(b.avatar.id, b.avatar.name, b.avatar.size, b.avatar.type)) " +
//            "FROM Brand b LEFT JOIN b.avatar WHERE b.id = :brandId")
//    BrandDTO getInformationBrand(@Param("brandId") UUID brandId);

}
