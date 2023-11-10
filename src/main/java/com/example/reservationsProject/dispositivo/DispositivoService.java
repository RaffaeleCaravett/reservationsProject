package com.example.reservationsProject.dispositivo;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.reservationsProject.enums.StatoDispositivo;
import com.example.reservationsProject.exceptions.BadRequestExceptions;
import com.example.reservationsProject.exceptions.NotFoundException;
import com.example.reservationsProject.utente.Dipendente;
import com.example.reservationsProject.utente.DipendenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

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
        if (body.statoDispositivo() == StatoDispositivo.DISMESSO || body.statoDispositivo() == StatoDispositivo.DISPONIBILE) {
            if(body.dipendente_id() != null){
                throw new BadRequestExceptions("Se lo stato del dispositivo è "+body.statoDispositivo() +" , l'id del dipendente non può essere presente");
            }else{
                newDispositivo.setDipendente(null);
            }
        }else{
            if (body.dipendente_id() != null) {
                Dipendente dipendente = dipendenteRepository.findById(body.dipendente_id())
                        .orElseThrow(() -> new BadRequestExceptions("Id non presente"));
                newDispositivo.setDipendente(dipendente);
            } else {
                if (body.statoDispositivo() == StatoDispositivo.ASSEGNATO) {
                    throw new BadRequestExceptions("Se lo stato del dispositivo è 'assegnato', l'id del dipendente non può essere null");
                }
            }

        }
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

    public DispositivoDTO findByIdAndUpdate(int id, DispositivoDTO body) throws NotFoundException {
        Dispositivo found = this.findById(id);

        found.setStatoDispositivo(body.statoDispositivo());
        found.setTipoDispositivo(body.tipoDispositivo());
        if (body.statoDispositivo() == StatoDispositivo.DISMESSO || body.statoDispositivo() == StatoDispositivo.DISPONIBILE) {
            if(body.dipendente_id() != null){
                throw new BadRequestExceptions("Se lo stato del dispositivo è "+body.statoDispositivo() +" , l'id del dipendente non può essere presente");
            }else{
                found.setDipendente(null);
            }
        }else{
            if (body.dipendente_id() != null) {
                Dipendente dipendente = dipendenteRepository.findById(body.dipendente_id())
                        .orElseThrow(() -> new BadRequestExceptions("Id non presente"));
                found.setDipendente(dipendente);
            } else {
                 if (body.statoDispositivo() == StatoDispositivo.ASSEGNATO) {
                    throw new BadRequestExceptions("Se lo stato del dispositivo è 'assegnato', l'id del dipendente non può essere null");
                }
        }

        }

        dispositivoRepository.save(found);
        return body;
    }


}
