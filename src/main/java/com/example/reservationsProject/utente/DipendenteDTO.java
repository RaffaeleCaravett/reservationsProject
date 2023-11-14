package com.example.reservationsProject.utente;

import com.example.reservationsProject.role.Role;

import javax.validation.constraints.*;
public record DipendenteDTO(
        @NotEmpty(message = "Lo username è un campo obbligatorio!")
        @Size(min = 3, max=30, message = "Il nome deve essere compreso tra 3 e 30 caratteri")
        String username,
        @NotEmpty(message = "Il nome è un campo obbligatorio!")
        @Size(min = 3, max=30, message = "Il nome deve essere compreso tra 3 e 30 caratteri")
        String nome,
        @NotEmpty(message = "Il cognome è un campo obbligatorio!")
        String cognome,
        @NotEmpty(message = "L'email è un campo obbligatorio!")
        @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "L'email inserita non è valida")
        String email,
        @NotEmpty(message = "La password è un campo obbligatorio!")
        String password,
        @NotEmpty(message = "Il ruolo è un campo obbligatorio!")
                Role role
) {
}
