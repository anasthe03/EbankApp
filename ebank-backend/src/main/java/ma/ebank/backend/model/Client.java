package ma.ebank.backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(unique = true, nullable = false)
    private String numeroIdentite;

    @Column(nullable = false)
    private LocalDate dateNaissance;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String adresse;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    // getters & setters
}
