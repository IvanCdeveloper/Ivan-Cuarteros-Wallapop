package com.example.ivancuarteroswallapop.repository;

import com.example.ivancuarteroswallapop.DTO.CategoriaCosteMedioDTO;
import com.example.ivancuarteroswallapop.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    @Query("SELECT new com.example.ivancuarteroswallapop.DTO.CategoriaCosteMedioDTO(c.id, c.nombre, AVG(p.precio), count(p), c.foto) " +
            "FROM Categoria c LEFT JOIN c.anuncios p GROUP BY c.id")
    List<CategoriaCosteMedioDTO> obtenerCategoriasConStats();
}
