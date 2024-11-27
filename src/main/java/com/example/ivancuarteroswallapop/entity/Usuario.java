package com.example.ivancuarteroswallapop.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.aspectj.bridge.IMessage;
import org.aspectj.bridge.Message;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.thymeleaf.messageresolver.IMessageResolver;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Data
@Table(name = "usuarios")
@Entity
public class Usuario implements UserDetails{


    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @NotNull(message = "El email es obligatorio")
    @Email(message = "Debe ser un email válido")
    @Column(unique = true, nullable = false)
    private String email;
    @Column(length = 500)
    @NotNull(message = "Tienes que escribir una contraseña")
    @Size(min = 4, message = "La contraseña debe tener al menos 4 caracteres.")
    private String password;

    private String poblacion;

    private String telefono;

    @OneToMany(targetEntity = Anuncio.class, cascade = CascadeType.ALL,
            mappedBy = "usuario", fetch = FetchType.EAGER)

    private List<Anuncio> anuncios;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> (GrantedAuthority) () -> role).toList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    //    @Column(name = "is_enabled")
//    private boolean isEnabled;
//
//    @Column(name = "account_No_Expired")
//    private boolean accountNoExpired;
//
//    @Column(name = "account_No_Locked")
//    private boolean accountNoLocked;
//
//    @Column(name="credential_No_Expired")
//    private boolean credentianNoExpired;
}
