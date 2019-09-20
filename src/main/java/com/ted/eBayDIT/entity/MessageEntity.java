package com.ted.eBayDIT.entity;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "messages")
public class MessageEntity implements Serializable {

    private static final long serialVersionUID = 8255615242366850373L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String subject;


    @Column
    private String message;


    @ManyToOne(/*fetch = FetchType.LAZY*/)
    @JoinColumn(name = "sender")
    private UserEntity sender;

    @ManyToOne(/*fetch = FetchType.LAZY*/)
    @JoinColumn(name = "receiver")
    private UserEntity receiver;

    @Column(name="read")
    private boolean read;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserEntity getSender() {
        return sender;
    }

    public void setSender(UserEntity sender) {
        this.sender = sender;
    }

    public UserEntity getReceiver() {
        return receiver;
    }

    public void setReceiver(UserEntity receiver) {
        this.receiver = receiver;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}






//    //since this is auto incremental id, to retrieve the message list we just sort by this in decreasing order
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "sender")
//    private UserEntity sender;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "receiver")
//    private UserEntity receiver;
//
//    @Column
//    private String message;
//