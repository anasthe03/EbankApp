package ma.ebank.backend.model;

import ma.ebank.backend.model.enums.RoleType;
import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private RoleType name;

    // getters & setters

    public Long getId() {
        return id;
    }

    public RoleType getName() {
        return name;
    }

}
