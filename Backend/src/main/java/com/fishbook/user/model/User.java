package com.fishbook.user.model;

import com.fishbook.location.model.Address;
import com.fishbook.registration.model.VerificationCode;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "Users")
@Where(clause = "is_deleted = false")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
    @JoinColumn(name = "addressId")
    private Address address;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "roleId")
    private Role role;

    @Column(nullable = false)
    private Boolean isEnabled;

    @Column(nullable = false)
    private Boolean isDeleted;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Set<VerificationCode> verificationCodes;
    
    private String profileImage;

    @Column
    private Integer points;

    @Column
    private Integer penalties;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "subscribed_entity_users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "entity_id"))
    private Set<com.fishbook.entity.model.Entity> subscribedEntities;

    public User() {}

    public User(String firstName, String lastName, String email, String password, String phoneNumber, Address address, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.role = role;
        this.isEnabled = true;
        this.isDeleted = false;
        this.verificationCodes = new HashSet<>();
        if(!role.equals("ADMIN")){
            this.points = 0;
        }
        if(role.equals("CLIENT")){
            this.penalties = 0;
        }
    }

    public User(String firstName, String lastName, String email, String password, String phoneNumber, Address address, Role role, Boolean isEnabled) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.role = role;
        this.isEnabled = isEnabled;
        this.isDeleted = false;
        this.verificationCodes = new HashSet<>();
        if(!role.equals("ADMIN")){
            this.points = 0;
        }
        if(role.equals("CLIENT")){
            this.penalties = 0;
        }
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public Role getRole() {
        return role;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Set<VerificationCode> getVerificationCodes() {
        return verificationCodes;
    }

    public void setVerificationCodes(Set<VerificationCode> verificationCodes) {
        this.verificationCodes = verificationCodes;
    }

    public void addVerificationCode(VerificationCode verificationCode){
        this.verificationCodes.add(verificationCode);
        verificationCode.setUser(this);
    }

    public void removeVerificationCode(VerificationCode verificationCode){
        this.verificationCodes.remove(verificationCode);
        verificationCode.setUser(null);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Role> authorities = new ArrayList<>();
        authorities.add(role);
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isDeleted;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public Integer getPoints() { return points; }

    public void setPoints(Integer points) { this.points = points; }

    public Integer getPenalties() { return penalties; }

    public void setPenalties(Integer penalties) { this.penalties = penalties; }

    public Set<com.fishbook.entity.model.Entity> getSubscribedEntities() { return subscribedEntities; }

    public void setSubscribedEntities(Set<com.fishbook.entity.model.Entity> subscribedEntities) { this.subscribedEntities = subscribedEntities; }

}
