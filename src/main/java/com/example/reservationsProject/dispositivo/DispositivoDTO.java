package com.example.reservationsProject.dispositivo;

import com.example.reservationsProject.enums.StatoDispositivo;
import com.example.reservationsProject.enums.TipoDispositivo;

import javax.validation.constraints.*;

public record DispositivoDTO(
        @NotNull(message = "Il tipo dispositivo è un campo obbligatorio!")
        TipoDispositivo tipoDispositivo,
        @NotNull(message = "Lo stato dispositivo è un campo obbligatorio!")
        StatoDispositivo statoDispositivo,
        Long dipendente_id
) {}
