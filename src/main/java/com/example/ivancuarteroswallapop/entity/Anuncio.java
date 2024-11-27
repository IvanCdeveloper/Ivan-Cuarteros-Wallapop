package com.example.ivancuarteroswallapop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.repository.cdi.Eager;

import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@ToString(exclude = "categoria")

@Table(name ="anuncios")
public class Anuncio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private Double precio;
    private String descripcion;

    @OneToMany(targetEntity = FotoAnuncio.class, cascade = CascadeType.ALL,
            mappedBy = "anuncio", fetch = FetchType.EAGER)
    private List<FotoAnuncio> fotos = new ArrayList<>();

    @ManyToOne(targetEntity = Usuario.class)
    private Usuario usuario;

    @ManyToOne(targetEntity = Categoria.class)
    @JoinColumn(name = "id_categoria", nullable = true)
    private Categoria categoria;



}
