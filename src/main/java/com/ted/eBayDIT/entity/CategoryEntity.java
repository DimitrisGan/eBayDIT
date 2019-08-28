package com.ted.eBayDIT.entity;


import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="category")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private  int id;

    @Column(name="name" ,nullable=false)
    private String name;


    @ManyToMany(cascade = CascadeType.ALL)/*(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})*/
    @JoinTable(
            name = "item_category",
            joinColumns = @JoinColumn(name = "category_id"), //refers to "category_id" column in "item_category" join  table
            inverseJoinColumns = @JoinColumn(name = "item_id") //refers to "item_id" column in "item_category" join  table
    )
    private List<ItemEntity> itemDetails;

    @Column(name="parent_id")
    private int parent_id;

    @Column(name="level"/*todo ,nullable=false*/)
    private int level;

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ItemEntity> getItemDetails() {
        return itemDetails;
    }

    public void setItemDetails(List<ItemEntity> itemDetails) {
        this.itemDetails = itemDetails;
    }
}
