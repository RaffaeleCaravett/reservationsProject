package com.example.reservationsProject.dispositivo;

import com.example.reservationsProject.enums.StatoDispositivo;
import com.example.reservationsProject.enums.TipoDispositivo;

import javax.validation.constraints.*;

public record DispositivoDTO(
        @NotBlank(message = "Il tipo dispositivo è un campo obbligatorio!")
        TipoDispositivo tipoDispositivo,
        @NotBlank(message = "Lo stato dispositivo è un campo obbligatorio!")
        StatoDispositivo statoDispositivo,
        @Min(value = 1,message = "Il valore minimo è 1")
        Long dipendente_id
) {}