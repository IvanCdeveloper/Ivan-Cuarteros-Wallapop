package com.example.ivancuarteroswallapop.service;
import com.example.ivancuarteroswallapop.entity.Anuncio;
import com.example.ivancuarteroswallapop.entity.Categoria;
import com.example.ivancuarteroswallapop.repository.AnuncioRepository;
import com.example.ivancuarteroswallapop.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class AnuncioService {


    @Autowired
    private AnuncioRepository anuncioRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Page<Anuncio> obtenerAnunciosPaginados(Pageable pageable) {
        return anuncioRepository.findAll(pageable);  // Esto devuelve una página de anuncios.
    }

    public List<Categoria> findAllCategoriasSorted() {

        return categoriaRepository.findAll(Sort.by("nombre").ascending());
    }

    public void deleteAnuncioById(Long id) {
        anuncioRepository.deleteById(id);
    }


}
