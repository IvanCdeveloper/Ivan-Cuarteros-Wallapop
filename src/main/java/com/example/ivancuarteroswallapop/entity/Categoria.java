package com.example.ivancuarteroswallapop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name="categorias")

public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @Column(length = 1000)
    private String descripcion;
    private String foto;


    @OneToMany(targetEntity = Anuncio.class, cascade = CascadeType.ALL,
            mappedBy = "categoria")
    private List<Anuncio> anuncios= new ArrayList<>();

}


