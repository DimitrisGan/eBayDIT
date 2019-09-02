package com.ted.eBayDIT.entity;

import javax.persistence.*;

@Entity
@Table(name = "messages")
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String message;



//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "sender")
//    private UserEntity sender;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "receiver")
//    private UserEntity receiver;
//

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