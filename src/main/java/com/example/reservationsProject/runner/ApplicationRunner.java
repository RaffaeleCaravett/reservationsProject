package com.example.reservationsProject.runner;

import com.example.reservationsProject.dispositivo.Dispositivo;
import com.example.reservationsProject.dispositivo.DispositivoDTO;
import com.example.reservationsProject.dispositivo.DispositivoService;
import com.example.reservationsProject.enums.StatoDispositivo;
import com.example.reservationsProject.enums.TipoDispositivo;
import com.example.reservationsProject.utente.Dipendente;
import com.example.reservationsProject.utente.DipendenteDTO;
import com.example.reservationsProject.utente.DipendenteService;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@Slf4j
public class ApplicationRunner implements CommandLineRunner {

    @Autowired
    DipendenteService dipendenteService;
    @Autowired
    DispositivoService dispositivoService;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker(Locale.ITALY);
       for(int i =0;i<=1000;i++){
           // DipendenteDTO genericDipendente = new DipendenteDTO(faker.name().username(),faker.name().firstName(),faker.name().lastName(),faker.internet().emailAddress());
           // dipendenteService.save(genericDipendente);
       /*    DispositivoDTO genericDispositivo;
           if(i<350){
               genericDispositivo = new DispositivoDTO(TipoDispositivo.SMARTPHONE, StatoDispositivo.ASSEGNATO, dipendenteService.getRandomDipendente().getId());
           }else if(i>750 &&i<=850){
               genericDispositivo = new DispositivoDTO(TipoDispositivo.TABLET, StatoDispositivo.DISMESSO, null);
           }else {
               genericDispositivo = new DispositivoDTO(TipoDispositivo.TABLET, StatoDispositivo.DISPONIBILE, null);
           }
           dispositivoService.save(genericDispositivo);*/
        }
    }
}
