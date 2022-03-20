package com.fishbook.passwordRenewalMark.model;

import javax.persistence.*;

@Entity
public class PasswordRenewalMark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    public PasswordRenewalMark() {}

    public PasswordRenewalMark(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
