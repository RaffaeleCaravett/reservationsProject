package com.example.reservationsProject.utente;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.reservationsProject.exceptions.BadRequestExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import com.example.reservationsProject.exceptions.NotFoundException;

import java.io.IOException;
import java.util.Map;

@Service
public class DipendenteService {
    @Autowired
    private DipendenteRepository dipendenteRepository;


    // @Autowired
    //private EmailSender emailSender;
    @Autowired
    private Cloudinary cloudinary;

    public Dipendente save(DipendenteDTO body) throws IOException {
        System.out.println(body.nome());
        dipendenteRepository.findByEmail(body.email()).ifPresent(user -> {
            throw new BadRequestExceptions("L'email " + body.email() + " è già in uso!");
        });
        Dipendente dipendente = new Dipendente(body.username(), body.nome(), body.cognome(), body.email(), body.password());
        return dipendenteRepository.save(dipendente);
    }

    public Page<Dipendente> getDipendenti(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));

        return dipendenteRepository.findAll(pageable);
    }

    public Dipendente findById(long id) throws NotFoundException {
        return dipendenteRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void findByIdAndDelete(long id) throws NotFoundException {
        Dipendente found = this.findById(id);
        dipendenteRepository.delete(found);
    }

    public Dipendente findByIdAndUpdate(long id, Dipendente body) throws NotFoundException {
        Dipendente found = this.findById(id);
        found.setUsername(body.getUsername());
        found.setCognome(body.getCognome());
        found.setNome(body.getNome());
        found.setEmail(body.getEmail());
        return dipendenteRepository.save(found);
    }

    public Dipendente getRandomDipendente() throws NotFoundException {
        return dipendenteRepository.findRandomAutore();
    }

    public String uploadPicture(long id, MultipartFile file) throws IOException {
        try {
            Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = (String) uploadResult.get("url");

            Dipendente autore = dipendenteRepository.findById(id).orElse(null);
            if (autore != null) {
                autore.setImmagine_profilo(imageUrl);
                dipendenteRepository.save(autore);
            }

            return imageUrl;
        } catch (IOException e) {
            throw new RuntimeException("Impossibile caricare l'immagine", e);
        }
    }
    public Dipendente findByEmail(String email) throws Exception {
        return dipendenteRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("Utente con email "+ email + " non trovato"));
    }


}