package com.example.ivancuarteroswallapop.controller;

import com.example.ivancuarteroswallapop.entity.Anuncio;
import com.example.ivancuarteroswallapop.entity.Usuario;
import com.example.ivancuarteroswallapop.repository.AnuncioRepository;
import com.example.ivancuarteroswallapop.repository.FotoAnuncioRepository;
import com.example.ivancuarteroswallapop.repository.UsuarioRepository;
import com.example.ivancuarteroswallapop.service.UserDetailsServiceImpl;
import com.example.ivancuarteroswallapop.service.RegisterService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class UsuarioController {





    private final RegisterService registerService;
    private final UsuarioRepository usuarioRepository;
    private final AnuncioRepository anuncioRepository;



    public UsuarioController(RegisterService registerService, UsuarioRepository usuarioRepository, AnuncioRepository anuncioRepository) {

        this.registerService = registerService;
        this.usuarioRepository = usuarioRepository;
        this.anuncioRepository = anuncioRepository;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        try {
            registerService.registerUser(usuario);
            return "redirect:/login?registered";
        } catch (Exception e) {
            model.addAttribute("error", "Error al registrar el usuario: " + e.getMessage());
            return "register";
        }
    }

    @GetMapping("/mis-anuncios")
    public String obtenerMisAnuncios(@AuthenticationPrincipal Usuario usuario, Model model ) {
        List<Anuncio> anuncios = usuario.getAnuncios();
        model.addAttribute("anuncios", anuncios);
        return "user-view";
    }


}
