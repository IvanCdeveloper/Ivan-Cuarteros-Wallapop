package com.example.ivancuarteroswallapop.service;

import com.example.ivancuarteroswallapop.entity.Usuario;
import com.example.ivancuarteroswallapop.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class RegisterService {

    private final UsuarioRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterService(UsuarioRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario registerUser(Usuario usuario) {
        if (usuario.getRoles() == null) {
            usuario.setRoles(new ArrayList<>()); // Inicializa la lista si es null
        }
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.getRoles().add("ROLE_USER");
        return userRepository.save(usuario);
    }

    public Optional<Usuario> findByUsername(String username) {
        return userRepository.findByEmail(username);
    }
}
