package com.example.reservationsProject.dispositivo;

import com.example.reservationsProject.enums.StatoDispositivo;
import com.example.reservationsProject.enums.TipoDispositivo;
import com.example.reservationsProject.utente.Dipendente;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="dispositivi")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder(builderClassName = "DispositivoBuilder")
public class Dispositivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name="tipo_dispositivo")
    @Enumerated(EnumType.STRING)
    private TipoDispositivo tipoDispositivo;
    @Column(name="stato_dispositivo")
    @Enumerated(EnumType.STRING)
    private StatoDispositivo statoDispositivo;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "dipendente_id", nullable = true)
    private Dipendente dipendente;

    public Dispositivo(TipoDispositivo tipoDispositivo, StatoDispositivo statoDispositivo) {
        this.tipoDispositivo = tipoDispositivo;
        this.statoDispositivo = statoDispositivo;
    }
}
