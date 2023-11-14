package com.example.reservationsProject.utente;

import com.example.reservationsProject.dispositivo.Dispositivo;
import com.example.reservationsProject.role.Role;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import com.github.javafaker.Faker;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="dipendenti")
@Builder(builderClassName = "DipendenteBuilder")
@JsonIgnoreProperties({"password", "authorities", "enabled", "credentialsNonExpired", "accountNonExpired", "accountNonLocked"})
public class Dipendente implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name="username")
    private String username;
    @Column(name="nome")
    private String nome;
    @Column(name="cognome")
    private String cognome;
    @Column(name="email")
    private String email;
    @Column(name="password")
    private String password;
    @Column(name="role")
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name="immagine_profilo")
    private String immagine_profilo;
    @JsonManagedReference
    @OneToMany(cascade=CascadeType.ALL,mappedBy = "dipendente", fetch = FetchType.EAGER)
    private List<Dispositivo> dispositivoList;

    public Dipendente(String username, String nome, String cognome, String email,String password) {
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
    }

    public static class DipendenteBuilder{
        private Faker faker = new Faker(Locale.ITALY);
        private String username=faker.name().username();
        private String nome=faker.name().firstName();
        private String cognome=faker.name().lastName();
        private String email=faker.internet().emailAddress();
    }

    @CreationTimestamp
    private Date createdAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
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
}
