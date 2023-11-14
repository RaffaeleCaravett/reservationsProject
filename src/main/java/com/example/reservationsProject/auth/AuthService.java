package com.example.reservationsProject.auth;


import com.example.reservationsProject.exceptions.BadRequestExceptions;
import com.example.reservationsProject.exceptions.UnauthorizedException;
import com.example.reservationsProject.role.Role;
import com.example.reservationsProject.security.JWTTools;
import com.example.reservationsProject.utente.Dipendente;
import com.example.reservationsProject.utente.DipendenteDTO;
import com.example.reservationsProject.utente.DipendenteRepository;
import com.example.reservationsProject.utente.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AuthService {
    @Autowired
    private DipendenteService usersService;

    @Autowired
    private DipendenteRepository dipendenteRepository;

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private PasswordEncoder bcrypt;

    public String authenticateUser(UtenteLoginDTO body) throws Exception {
        // 1. Verifichiamo che l'email dell'utente sia nel db
        Dipendente user = usersService.findByEmail(body.email());

        // 2. In caso affermativo, verifichiamo se la password corrisponde a quella trovata nel db
        if(body.password().equals(user.getPassword())) {
            // 3. Se le credenziali sono OK --> Genero un JWT e lo restituisco
            return jwtTools.createToken(user);
        } else {
            // 4. Se le credenziali NON sono OK --> 401
            throw new UnauthorizedException("Credenziali non valide!");
        }
    }

    public Dipendente registerUser(DipendenteDTO body) throws IOException {

        // verifico se l'email è già utilizzata
        dipendenteRepository.findByEmail(body.email()).ifPresent( user -> {
            throw new BadRequestExceptions("L'email " + user.getEmail() + " è già utilizzata!");
        });
        Dipendente newUser = new Dipendente();
        newUser.setUsername(body.username());
        newUser.setNome(body.nome());
        newUser.setCognome(body.cognome());
        newUser.setPassword(bcrypt.encode(body.password())); // $2a$11$wQyZ17wrGu8AZeb2GCTcR.QOotbcVd9JwQnnCeqONWWP3wRi60tAO
        newUser.setEmail(body.email());
        newUser.setRole(Role.USER);
        return dipendenteRepository.save(newUser);
    }
    public Dipendente save(DipendenteDTO body) throws IOException {
        System.out.println(body.nome());
        dipendenteRepository.findByEmail(body.email()).ifPresent(user -> {
            throw new BadRequestExceptions("L'email " + body.email() + " è già in uso!");
        });
        Dipendente dipendente = new Dipendente(body.username(), body.nome(), body.cognome(), body.email(), body.password());
        return dipendenteRepository.save(dipendente);
    }

}
