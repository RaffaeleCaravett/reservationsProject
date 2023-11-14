package com.example.reservationsProject.exceptions;

import java.util.Date;

public record ErrorsResponseDTO(String message, Date timestamp) {
}
