package ru.kazakova.snacky.model.security;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "app_user")
public class User {

    protected User() {

    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        this.accountNonLocked = true;
    }

    public User(String login, String password, Role role) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.accountNonLocked = true;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    @Column(name="login", nullable=false)
    private String login;
    @Column(name="password", nullable=false)
    private String password;
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    @ManyToOne
    private Role role;
    @Column(name="account_non_locked", nullable=false)
    private boolean accountNonLocked;

    public void setRole(Role role) {
        this.role = role;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }
}
