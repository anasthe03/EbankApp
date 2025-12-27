package ma.ebank.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String login;

    @Column(nullable = false)
    private String password;

    private boolean enabled = true;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    // getters & setters
}