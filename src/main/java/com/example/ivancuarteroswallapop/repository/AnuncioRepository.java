package com.example.ivancuarteroswallapop.repository;


import com.example.ivancuarteroswallapop.entity.Anuncio;
import com.example.ivancuarteroswallapop.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnuncioRepository extends JpaRepository<Anuncio, Long> {
    Optional<Anuncio> findAllByUsuario(Usuario usuario);

    Page<Anuncio> findAll(Pageable pageable);

    Page<Anuncio> findByUsuario(Usuario usuario, Pageable pageable);



}
