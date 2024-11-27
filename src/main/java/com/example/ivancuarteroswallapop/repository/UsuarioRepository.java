package com.example.ivancuarteroswallapop.repository;


import com.example.ivancuarteroswallapop.entity.Anuncio;
import com.example.ivancuarteroswallapop.entity.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional <Usuario> findByEmail(String email);


}
