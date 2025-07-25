package com.example.demo.model;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class IssuedBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Book book;

    private LocalDate issueDate;
    private LocalDate returnDate;

    // Getters and setters
}
