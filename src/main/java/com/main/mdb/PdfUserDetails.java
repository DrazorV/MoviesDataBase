package com.main.mdb;



import org.springframework.security.core.userdetails.UserDetails;

public abstract class PdfUserDetails implements UserDetails {
    private final User user;
    public PdfUserDetails(User user) {
        this.user = user;
    }

    public long getId() {
        return user.getId();
    }
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
    public User getUserDetails() {
        return user;
    }
}