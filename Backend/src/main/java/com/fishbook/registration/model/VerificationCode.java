package com.fishbook.registration.model;

import com.fishbook.user.model.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "verification_code")
public class VerificationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String verificationCode;

    @Column(nullable = false)
    private Date issuingDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public VerificationCode() {}

    public VerificationCode(String verificationCode, Date issuingDate) {
        this.verificationCode = verificationCode;
        this.issuingDate = issuingDate;
    }

    public Long getId() {
        return id;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public Date getIssuingDate() {
        return issuingDate;
    }

    public void setIssuingDate(Date issuingDate) {
        this.issuingDate = issuingDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VerificationCode )) return false;
        return id != null && id.equals(((VerificationCode) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
