package com.ted.eBayDIT.entity;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="connectivity")
public class ConnectivityEntity implements Serializable {

    private static final long serialVersionUID = -6444056272165270245L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(/*fetch = FetchType.LAZY*/)
    @JoinColumn(name = "user_1")
    private UserEntity connectedUser1;

    @ManyToOne(/*fetch = FetchType.LAZY*/)
    @JoinColumn(name = "user_2")
    private UserEntity connectedUser2;


    @Column
    private Boolean isPending;


    public Boolean getPending() {
        return isPending;
    }

    public void setPending(Boolean pending) {
        isPending = pending;
    }

    public UserEntity getConnectedUser1() {
        return connectedUser1;
    }

    public void setConnectedUser1(UserEntity connectedUser1) {
        this.connectedUser1 = connectedUser1;
    }

    public UserEntity getConnectedUser2() {
        return connectedUser2;
    }

    public void setConnectedUser2(UserEntity connectedUser2) {
        this.connectedUser2 = connectedUser2;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
