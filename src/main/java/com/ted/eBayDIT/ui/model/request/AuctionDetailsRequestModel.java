package com.ted.eBayDIT.ui.model.request;


import com.ted.eBayDIT.dto.CategoryDto;
import com.ted.eBayDIT.dto.ItemLocationDto;
import com.ted.eBayDIT.dto.PhotoDto;
import com.ted.eBayDIT.dto.SellerDto;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public class AuctionDetailsRequestModel {


    @NotNull(message = "Item Name cannot be missing")
    private String name;

    private BigDecimal buyPrice;

    @NotNull(message = "FirstBid Price cannot be missing")
    private BigDecimal firstBid;

    private String country;

    @NotNull(message = "Ends Date cannot be missing")
    private String ends;

    private String description;

    private List<CategoryDto> categories;

    private ItemLocationDto location;

    private List<PhotoDto> photos;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

    public BigDecimal getFirstBid() {
        return firstBid;
    }

    public void setFirstBid(BigDecimal firstBid) {
        this.firstBid = firstBid;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    public String getEnds() {
        return ends;
    }

    public void setEnds(String ends) {
        this.ends = ends;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<CategoryDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDto> categories) {
        this.categories = categories;
    }


    public ItemLocationDto getLocation() {
        return location;
    }

    public void setLocation(ItemLocationDto location) {
        this.location = location;
    }

    public List<PhotoDto> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoDto> photos) {
        this.photos = photos;
    }
}
