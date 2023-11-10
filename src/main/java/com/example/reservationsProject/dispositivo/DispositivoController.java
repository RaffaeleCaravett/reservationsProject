package com.example.reservationsProject.dispositivo;

import com.example.reservationsProject.exceptions.BadRequestExceptions;
import com.example.reservationsProject.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/dispositivi")
public class DispositivoController {

    @Autowired
    private DispositivoService dispositivoService;

    @GetMapping("")
    public Page<Dispositivo> getDispositivi(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size,
                                            @RequestParam(defaultValue = "id") String orderBy){
        return dispositivoService.getDispositivi(page, size, orderBy);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED) // <-- 201
    public Dispositivo saveDispositivo(@RequestBody @Validated DispositivoDTO body, BindingResult validation){
        if(validation.hasErrors()){
            throw new BadRequestExceptions(validation.getAllErrors());
        } else {
            try {
                return dispositivoService.save(body);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @GetMapping(value = "/{id}")
    public Dispositivo findById(@PathVariable int id) throws NotFoundException {
        return dispositivoService.findById(id);
    }

    @PutMapping("/{id}")
    public Dispositivo findByIdAndUpdate(@PathVariable int id, @RequestBody Dispositivo body) throws NotFoundException {
        return dispositivoService.findByIdAndUpdate(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable int id) throws NotFoundException {
        dispositivoService.findByIdAndDelete(id);
    }
}
