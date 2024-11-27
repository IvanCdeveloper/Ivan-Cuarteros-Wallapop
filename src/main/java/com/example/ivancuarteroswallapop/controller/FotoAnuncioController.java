package com.example.ivancuarteroswallapop.controller;

import ch.qos.logback.core.model.Model;
import com.example.ivancuarteroswallapop.entity.Anuncio;
import com.example.ivancuarteroswallapop.repository.AnuncioRepository;
import com.example.ivancuarteroswallapop.service.FotoAnuncioService;
import com.example.ivancuarteroswallapop.repository.FotoAnuncioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
@Controller
public class FotoAnuncioController {
    @Autowired
    private FotoAnuncioRepository fotoAnuncioRepository;
    @Autowired
    private AnuncioRepository anuncioRepository;
    @Autowired
    private FotoAnuncioService fotoAnuncioService;

    @GetMapping("anuncios/{id1}/fotos/{id2}/delete")
    public String deleteFoto(@PathVariable("id1") Long idAnuncio,
                             @PathVariable("id2") Long idFoto) {
        fotoAnuncioService.deleteFoto(idFoto);
        return "redirect:/anuncios/edit/" + idAnuncio ;
    }


    @PostMapping("/anuncios/edit/{id}/addfoto")
    public String addFoto(@PathVariable("id") Long idFoto, Model model,
                          @RequestParam(value = "archivoFoto") MultipartFile archivoFoto) {
        Optional<Anuncio> anuncioOptional = anuncioRepository.findById(idFoto);
        if(anuncioOptional.isPresent()){
            fotoAnuncioService.addFoto(archivoFoto, anuncioOptional.get());
        }
        return "redirect:/anuncios/edit/" + idFoto;
    }


}
