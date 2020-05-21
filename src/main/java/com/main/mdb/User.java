package com.main.mdb;

import javax.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.TABLE)
    private Long id;

    @Column(unique=true)
    private String email;
    private String password;

    protected User() {}

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

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}