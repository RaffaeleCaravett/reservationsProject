package com.example.reservationsProject.utente;

import com.example.reservationsProject.auth.AuthService;
import com.example.reservationsProject.exceptions.BadRequestExceptions;
import com.example.reservationsProject.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    @Autowired
    private AuthService authService;

    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
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
                return authService.save(body);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @GetMapping("/me")
    public UserDetails getProfile(@AuthenticationPrincipal UserDetails currentUser){
        return currentUser;
    };

    @PutMapping("/me")
    public UserDetails getProfile(@AuthenticationPrincipal Dipendente currentUser, @RequestBody Dipendente body){
        return dipendenteService.findByIdAndUpdate(currentUser.getId(), body);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT) // <-- 204 NO CONTENT
    public void getProfile(@AuthenticationPrincipal Dipendente currentUser){
        dipendenteService.findByIdAndDelete(currentUser.getId());
    };
    @GetMapping(value = "/{id}")
    public Dipendente findById(@PathVariable int id)  {
        return dipendenteService.findById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Dipendente findByIdAndUpdate(@PathVariable int id, @RequestBody Dipendente body) throws NotFoundException {
        return dipendenteService.findByIdAndUpdate(id, body);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT) // <-- 204 NO CONTENT
    public void findByIdAndDelete(@PathVariable int id) throws NotFoundException {
        dipendenteService.findByIdAndDelete(id);
    }


    @PostMapping("/upload/{id}")
    public String uploadExample(@PathVariable long id,@RequestParam("immagine_profilo") MultipartFile body) throws IOException {
        System.out.println(body.getSize());
        System.out.println(body.getContentType());
        return dipendenteService.uploadPicture(id,body);
    }
}