package com.ted.eBayDIT.dto;

import java.io.Serializable;

public class PhotoDto implements Serializable {

    private static final long serialVersionUID = 3103903543008675615L;


    private int photoId;
    private String path;
    private String fileName;


    private ItemDto item;


    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public ItemDto getItem() {
        return item;
    }

    public void setItem(ItemDto item) {
        this.item = item;
    }
}
