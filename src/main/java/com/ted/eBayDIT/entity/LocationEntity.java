package com.ted.eBayDIT.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="Location")
public class LocationEntity {

    private String Latitude;

    private String Longitude;

    private String content;

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
