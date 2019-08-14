package com.ted.eBayDIT.entity;


import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Items")
public class ItemsEntity {

    private List<ItemEntity> Item = new ArrayList<>();

    public List<ItemEntity> getItem() {
        return Item;
    }

    public void setItem(List<ItemEntity> item) {
        Item = item;
    }
}
