package ir.academix.exception;

import jakarta.persistence.EntityNotFoundException;

public class CourseNotFoundException extends EntityNotFoundException {
    public CourseNotFoundException(Long id) {
        super("Course not found with ID: " + id);
    }
}
