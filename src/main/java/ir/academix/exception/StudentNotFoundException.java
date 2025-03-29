package ir.academix.exception;

import jakarta.persistence.EntityNotFoundException;

public class StudentNotFoundException extends EntityNotFoundException {
    public StudentNotFoundException(Long id) {
        super("Student not found with ID: " + id);
    }
}
