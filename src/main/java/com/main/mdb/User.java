package com.main.mdb;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "user_seq")
    @Column(unique=true, nullable=false, name = "id")
    private Long id;

    @Column(unique=true)
    private String email;
    private String password;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private Set<UserSave> userSaves;

    public User() {}

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format(
                "user[id=%d, email='%s', password='%s']",
                id, email, password);
    }

    public Long getId() {
        return id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<UserSave> getUserSaves() {
        return userSaves;
    }

    public void setUserSaves(Set<UserSave> userSaves) {
        this.userSaves = userSaves;
    }
}