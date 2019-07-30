package com.ted.eBayDIT.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="user")
public class UserEntity implements Serializable {


    private static final long serialVersionUID = 4L;

    // define fields

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private  int id;

    @Column(name="public_id"/*,nullable = false*/,unique = true)
    private String userId;


    @Column(name="username" ,nullable=false ,unique = true)
    private String username;

    @Column(name="password")
    private String encryptedPassword;

    @Column(name="first_name",nullable=false)
    private String firstName;

    @Column(name="last_name",nullable=false)
    private String lastName;

    @Column(name="email",nullable=false)
    private String email;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "role_id")
//    private Role role;
//
//    //todo add it later
//    @Column(name="verified" ,nullable=false ,columnDefinition="booelan default false")
//    private Boolean verifiedByAdmin;



    // define constructors



    // define getter/setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public Role getRole() {
//        return role;
//    }
//
//    public void setRole(Role role) {
//        this.role = role;
//    }
//
//    public Boolean getVerifiedByAdmin() {
//        return verifiedByAdmin;
//    }
//
//    public void setVerifiedByAdmin(Boolean verifiedByAdmin) {
//        this.verifiedByAdmin = verifiedByAdmin;
//    }


    // define tostring



}
