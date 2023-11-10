package com.example.reservationsProject.dispositivo;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.reservationsProject.exceptions.NotFoundException;
import com.example.reservationsProject.utente.DipendenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DispositivoService {

    @Autowired
    private DispositivoRepository dispositivoRepository;
    @Autowired
    private DipendenteRepository dipendenteRepository;



    @Autowired
    private Cloudinary cloudinary;
    public Dispositivo save(DispositivoDTO body) throws IOException {
        Dispositivo newDispositivo = new Dispositivo();
        newDispositivo.setStatoDispositivo(body.statoDispositivo());
        newDispositivo.setTipoDispositivo(body.tipoDispositivo());
        newDispositivo.setDipendente(dipendenteRepository.findById(body.dipendente_id()).get());
        return dispositivoRepository.save(newDispositivo);
    }

    public Page<Dispositivo> getDispositivi(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));

        return dispositivoRepository.findAll(pageable);
    }

    public Dispositivo findById(long id) throws NotFoundException {
        return dispositivoRepository.findById(id).orElseThrow( ()  -> new NotFoundException(id));
    }

    public void findByIdAndDelete(int id) throws NotFoundException{
        Dispositivo found = this.findById(id);
        dispositivoRepository.delete(found);
    }

    public Dispositivo findByIdAndUpdate(int id, Dispositivo body) throws NotFoundException{
        Dispositivo found = this.findById(id);
        found.setStatoDispositivo(body.getStatoDispositivo());
        found.setTipoDispositivo(body.getTipoDispositivo());
        found.setDipendente(dipendenteRepository.findById(body.getId()).get());
        return dispositivoRepository.save(found);
    }


}
