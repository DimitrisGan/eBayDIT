package com.ted.eBayDIT.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * {@code} Entity representing a RoleEntity
 * contains an user role
 *
 */
@Entity
@Table(name = "role")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private int id;

    @Column(name = "role_name")
    private String userRole;


    @OneToMany(mappedBy="role")
    private List<UserEntity> userEntities = new ArrayList<>();


    public RoleEntity() {
    }
    

    public RoleEntity(String userRole) {
        this.userRole = userRole;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public List<UserEntity> getUserEntities() {
        return userEntities;
    }

    public void setUserEntities(List<UserEntity> userEntities) {
        this.userEntities = userEntities;
    }
}


//other fields, constructor, getters/setters omitted for brevity
