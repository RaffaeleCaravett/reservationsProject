package com.example.reservationsProject.utente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DipendenteRepository extends JpaRepository<Dipendente,Long> {
    Optional<Dipendente> findByEmail(String ics);
    @Query(value = "SELECT * FROM dipendenti ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Dipendente findRandomAutore();
}
