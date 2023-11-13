package com.example.reservationsProject.auth;


import com.example.reservationsProject.exceptions.UnauthorizedException;
import com.example.reservationsProject.security.JWTTools;
import com.example.reservationsProject.utente.Dipendente;
import com.example.reservationsProject.utente.DipendenteDTO;
import com.example.reservationsProject.utente.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private DipendenteService usersService;

    @Autowired
    private JWTTools jwtTools;

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
}
