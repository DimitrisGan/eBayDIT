package com.ted.eBayDIT.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//@Data

@Entity
@Table(name="user")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = -7915207677438866300L;
    // define fields
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private  int id;

    @Column(name="public_id",unique = true)
    private String userId;

    @Column(name="username" ,nullable=false ,unique = true)
    private String username;

    @Column(name="password")
    private String encryptedPassword;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="email")
    private String email;

    @Column(name="phone_number")
    private String phoneNumber;

    @Column(name="country")
    private String country;

    @Column(name="location")
    private String location;

    @Column(name="afm")
    private String afm;

    @Column(name="verified")
    private boolean verified;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private RoleEntity role;


    /*
    Thanks to CascadeType.ALL, associated entities BidderDetailsEntity,SellerDetailsEntity will be saved at the same time with UserEntity
    without the need of calling its save function explicitly
    Source: https://hellokoding.com/jpa-one-to-one-shared-primary-key-relationship-mapping-example-with-spring-boot-maven-and-mysql/
    */
    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private BidderDetailsEntity bidder;

    @OneToOne(mappedBy = "user" ,cascade =CascadeType.ALL)
    private SellerDetailsEntity seller;


    @OneToMany(mappedBy="connectedUser1",
            cascade = CascadeType.ALL, fetch = FetchType.LAZY )
    private List<ConnectivityEntity> connections;

    @OneToMany(mappedBy = "connectedUser2" ,
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ConnectivityEntity> connectedTo;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
            mappedBy = "sender" /*, orphanRemoval = true*/)
    private List<MessageEntity> sentMessages;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
            mappedBy = "receiver" /*, orphanRemoval = true*/)
    private List<MessageEntity> receivedMessages;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
            mappedBy = "visitor" /*, orphanRemoval = true*/)
    private List<VisitEntity> itemsVisited;



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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAfm() {
        return afm;
    }

    public void setAfm(String afm) {
        this.afm = afm;
    }


    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

    public BidderDetailsEntity getBidder() {
        return bidder;
    }

    public void setBidder(BidderDetailsEntity bidder) {
        this.bidder = bidder;
    }

    public SellerDetailsEntity getSeller() {
        return seller;
    }

    public void setSeller(SellerDetailsEntity seller) {
        this.seller = seller;
    }


    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public List<MessageEntity> getSentMessages() {
        return sentMessages;
    }

    public void setSentMessages(List<MessageEntity> sentMessages) {
        this.sentMessages = sentMessages;
    }

    public List<MessageEntity> getReceivedMessages() {
        return receivedMessages;
    }

    public void setReceivedMessages(List<MessageEntity> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }

    public List<VisitEntity> getItemsVisited() {
        return itemsVisited;
    }

    public void setItemsVisited(List<VisitEntity> itemsVisited) {
        this.itemsVisited = itemsVisited;
    }

    public List<ConnectivityEntity> getConnections() {
        return connections;
    }

    public void setConnections(List<ConnectivityEntity> connections) {
        this.connections = connections;
    }

    public List<ConnectivityEntity> getConnectedTo() {
        return connectedTo;
    }

    public void setConnectedTo(List<ConnectivityEntity> connectedTo) {
        this.connectedTo = connectedTo;
    }
}
