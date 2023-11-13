package com.example.reservationsProject.utente;

import com.example.reservationsProject.dispositivo.Dispositivo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import com.github.javafaker.Faker;

import javax.persistence.*;
import java.util.List;
import java.util.Locale;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="dipendenti")
@Builder(builderClassName = "DipendenteBuilder")
public class Dipendente {
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


}
