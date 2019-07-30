package com.ted.eBayDIT.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * {@code} Entity representing a Role
 * contains an user role
 *
 */
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int roleId;

    @Column(name = "name")
    private String userRole;


//    @OneToMany(mappedBy="role")
//    private List<UserEntity> userEntities = new ArrayList<>();


    public Role() { }

    public Role(String role) {
        setRole(role);
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRole() {
        return userRole;
    }

    public void setRole(String role) {
        userRole = role;
    }
}


//other fields, constructor, getters/setters omitted for brevity
