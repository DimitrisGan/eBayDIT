package com.ted.eBayDIT.entity;


import javax.persistence.*;

@Entity
@Table(name="connectivity")
public class ConnectivityEntity {

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
}
