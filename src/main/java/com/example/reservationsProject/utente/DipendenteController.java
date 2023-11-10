package com.example.reservationsProject.utente;

import com.example.reservationsProject.exceptions.BadRequestExceptions;
import com.example.reservationsProject.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/dipendente")
public class DipendenteController {
    @Autowired
    private DipendenteService dipendenteService;

    @GetMapping("")
    public Page<Dipendente> getUser(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size,
                              @RequestParam(defaultValue = "id") String orderBy){
        return dipendenteService.getDipendenti(page, size, orderBy);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED) // <-- 201
    public Dipendente saveDipendente(@RequestBody @Validated DipendenteDTO body, BindingResult validation){
        if(validation.hasErrors()){
            throw new BadRequestExceptions(validation.getAllErrors());
        } else {
            try {
                return dipendenteService.save(body);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @GetMapping(value = "/{id}")
    public Dipendente findById(@PathVariable int id)  {
        return dipendenteService.findById(id);
    }

    @PutMapping("/{id}")
    public Dipendente findByIdAndUpdate(@PathVariable int id, @RequestBody Dipendente body) throws NotFoundException {
        return dipendenteService.findByIdAndUpdate(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // <-- 204 NO CONTENT
    public void findByIdAndDelete(@PathVariable int id) throws NotFoundException {
        dipendenteService.findByIdAndDelete(id);
    }


    @PostMapping("/upload/{id}")
    public String uploadExample(@PathVariable long id,@RequestParam("avatar") MultipartFile body) throws IOException {
        System.out.println(body.getSize());
        System.out.println(body.getContentType());
        return dipendenteService.uploadPicture(id,body);
    }
}