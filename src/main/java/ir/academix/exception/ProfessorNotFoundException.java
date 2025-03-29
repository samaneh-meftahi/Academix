package ir.academix.exception;

import jakarta.persistence.EntityNotFoundException;

public class ProfessorNotFoundException extends EntityNotFoundException {
    public ProfessorNotFoundException(Long id) {
        super("Professor not found with ID: " + id);
    }
}
