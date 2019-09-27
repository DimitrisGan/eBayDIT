package com.ted.eBayDIT.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name="category")
public class CategoryEntity implements Serializable {

    private static final long serialVersionUID = 1568092388938648026L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private  int id;

    @Column(name="name" ,nullable=false)
    private String name;


    @ManyToMany
    @JoinTable(
            name = "item_category",
            joinColumns = @JoinColumn(name = "category_id"), //refers to "category_id" column in "item_category" join  table
            inverseJoinColumns = @JoinColumn(name = "item_id") //refers to "item_id" column in "item_category" join  table
    )
    private List<ItemEntity> itemDetails;

    @Column(name="parent_id")
    private int parentId;

    @Column(name="level")
    private int level;

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
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
