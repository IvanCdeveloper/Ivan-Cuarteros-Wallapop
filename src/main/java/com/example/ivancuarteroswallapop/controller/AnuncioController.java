package com.example.ivancuarteroswallapop.controller;


import com.example.ivancuarteroswallapop.entity.Anuncio;
import com.example.ivancuarteroswallapop.entity.Usuario;
import com.example.ivancuarteroswallapop.repository.AnuncioRepository;
import com.example.ivancuarteroswallapop.repository.CategoriaRepository;
import com.example.ivancuarteroswallapop.repository.UsuarioRepository;
import com.example.ivancuarteroswallapop.service.AnuncioService;
import com.example.ivancuarteroswallapop.service.FotoAnuncioService;
import com.example.ivancuarteroswallapop.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
public class AnuncioController {

    @Autowired
    AnuncioRepository anuncioRepository;

    @Autowired
    UsuarioController usuarioController;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private FotoAnuncioService fotoAnuncioService;

    @Autowired
    private AnuncioService anuncioService;
    @Autowired
    private CategoriaRepository categoriaRepository;


    @GetMapping("/")
    public String Anuncios(@RequestParam(defaultValue = "0") int page, Model model) {
        Pageable pageable = PageRequest.of(page, 6);
        Page<Anuncio> anunciosPage = anuncioRepository.findAll(pageable);
        long totalAnuncios = anunciosPage.getTotalElements();
        model.addAttribute("anuncios", anunciosPage.getContent());
        model.addAttribute("totalPaginas", anunciosPage.getTotalPages());
        model.addAttribute("paginaActual", page);
        model.addAttribute("totalAnuncios", totalAnuncios);

        return "anuncio-list";
    }

    @GetMapping("/anuncio-list")
    public String listarAnuncios(@RequestParam(defaultValue = "0") int page, Model model) {
        Pageable pageable = PageRequest.of(page, 6);
        Page<Anuncio> anunciosPage = anuncioRepository.findAll(pageable);

        long totalAnuncios = anunciosPage.getTotalElements();
        model.addAttribute("anuncios", anunciosPage.getContent());
        model.addAttribute("totalPaginas", anunciosPage.getTotalPages());
        model.addAttribute("paginaActual", page);
        model.addAttribute("totalAnuncios", totalAnuncios);


        return "anuncio-list";
    }


    @GetMapping("/anuncios/crear")
    public String crear(Model model) {
        model.addAttribute("anuncio", new Anuncio());
        model.addAttribute("categoria", categoriaRepository.findAll());
        return "anuncio-new";
    }

    @GetMapping("/del/{id}")
    public String delete(@PathVariable Long id) {
        anuncioService.deleteAnuncioById(id);
        return "redirect:/mis-anuncios";
    }

    @PostMapping("/anuncios/crear")
    public String crear(@ModelAttribute("anuncio") Anuncio anuncio, @AuthenticationPrincipal Usuario usuario , BindingResult bindingResult,
                        @RequestParam(value = "archivosFotos", required = false) List<MultipartFile> fotos, Model model) {
        anuncio.setUsuario(usuario);
        anuncioRepository.save(anuncio);


        //Guardar fotos
        try {
            fotoAnuncioService.guardarFotos(fotos, anuncio);
        }catch (IllegalArgumentException ex) {

            model.addAttribute("mensaje", ex.getMessage());
            return "anuncio-new";
        }

        usuario.getAnuncios().add(anuncio);


        return "redirect:/";
    }

    @GetMapping("/anuncios/ver/{id}")
    public String ver(@PathVariable Long id, Model model) {
        Optional<Anuncio> anuncio = anuncioRepository.findById(id);
        if(anuncio.isPresent()) {
            model.addAttribute("anuncio", anuncio.get());

            return "anuncio-view";
        }
        return "redirect:/";

    }

    @GetMapping("/edit/{id}")
    public String editProducto(@PathVariable Long id, Model model) {
        Optional<Anuncio> anuncio = anuncioRepository.findById(id);
        if (anuncio.isPresent()) {
            model.addAttribute("categorias", anuncioService.findAllCategoriasSorted());
            model.addAttribute("anuncio", anuncio.get());
            return "anuncio-edit";
        }
        return "redirect:/";
    }

    @PostMapping("/edit/{id}")
    public String editProductoUpdate(@PathVariable Long id, Anuncio anuncio) {
        anuncio.setId(id);
        anuncioRepository.save(anuncio);
        return "redirect:/";
    }


}

