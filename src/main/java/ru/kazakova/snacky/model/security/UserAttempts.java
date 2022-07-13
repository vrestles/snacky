package ru.kazakova.snacky.model.security;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "app_user_attempts")
public class UserAttempts {

    protected UserAttempts() {

    }

    public UserAttempts(User user, Integer attempts, LocalDateTime lastModified) {
        this.user = user;
        this.attempts = attempts;
        this.lastModified = lastModified;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @OneToOne
    private User user;
    @Column(name="attempts", nullable=false)
    private int attempts;
    @Column(name="last_modified", nullable=false)
    private LocalDateTime lastModified;

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public int getAttempts() {
        return attempts;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }
}
