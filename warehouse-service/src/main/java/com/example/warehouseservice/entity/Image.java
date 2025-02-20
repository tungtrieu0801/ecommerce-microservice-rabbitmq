package com.example.warehouseservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "image")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image {

    @Id
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "size")
    private String size;

    @Column(name = "type")
    private String type;

    @OneToOne(mappedBy = "avatar", cascade = CascadeType.ALL, orphanRemoval = true)
    private Brand brand;
}

