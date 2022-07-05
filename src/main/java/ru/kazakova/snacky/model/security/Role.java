package ru.kazakova.snacky.model.security;

import javax.persistence.*;

@Entity
@Table(name = "app_role")
public class Role {

    protected Role() {

    }

    public Role(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="name", nullable=false)
    private String name;

    public String getName() {
        return name;
    }
}
