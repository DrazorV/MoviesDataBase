package com.main.mdb;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name="user_save", uniqueConstraints = @UniqueConstraint(columnNames={"USER_ID", "MOVIE_ID"}))
public class UserSave {

    @Id
    @Column(name="IDENTIFIER", unique=true, nullable=false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long IDENTIFIER;

    @Column(name="MOVIE_ID")
    private String movieId;

    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name = "user_id",referencedColumnName = "id",nullable = false)
    private User user;


    public UserSave() {}

    public UserSave(User user, String movieId) {
        this.user = user;
        this.movieId = movieId;
    }

    public User getUser() {
        return user;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return IDENTIFIER;
    }

    public void setId(Long IDENTIFIER) {
        this.IDENTIFIER = IDENTIFIER;
    }
}