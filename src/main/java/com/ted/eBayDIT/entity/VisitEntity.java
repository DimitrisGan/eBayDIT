package com.ted.eBayDIT.entity;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="visits")
public class VisitEntity implements Serializable {

    private static final long serialVersionUID = 3758103777054040369L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private  int id;


    @ManyToOne(cascade= CascadeType.ALL)/*(cascade={CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})*/
    @JoinColumn(name = "user_id")
    private UserEntity visitor;


    @ManyToOne(cascade= CascadeType.ALL)/*(cascade={CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})*/
    @JoinColumn(name = "item_id")
    private ItemEntity item;


    @Column(name="times")
    private int visitsTimes;


    public UserEntity getVisitor() {
        return visitor;
    }

    public void setVisitor(UserEntity visitor) {
        this.visitor = visitor;
    }

    public ItemEntity getItem() {
        return item;
    }

    public void setItem(ItemEntity item) {
        this.item = item;
    }

    public int getVisitsTimes() {
        return visitsTimes;
    }

    public void setVisitsTimes(int visitsTimes) {
        this.visitsTimes = visitsTimes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
