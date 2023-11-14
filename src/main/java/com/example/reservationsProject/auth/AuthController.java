package com.example.reservationsProject.auth;

import com.example.reservationsProject.exceptions.BadRequestExceptions;
import com.example.reservationsProject.utente.Dipendente;
import com.example.reservationsProject.utente.DipendenteDTO;
import com.example.reservationsProject.utente.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private DipendenteService dipendenteService;

    @PostMapping("/login")
    public UtenteLoginSuccessDTO login(@RequestBody UtenteLoginDTO body) throws Exception {

        return new UtenteLoginSuccessDTO(authService.authenticateUser(body));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED) // <-- 201
    public Dipendente saveUser(@RequestBody @Validated DipendenteDTO body, BindingResult validation){
        if(validation.hasErrors()){
            throw new BadRequestExceptions(validation.getAllErrors());
        } else {
            try {
                return authService.save(body);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
