package com.ted.eBayDIT.entity;


import javax.persistence.*;


@Entity
@Table(name="photos")
public class PhotoEntity {

    @Id
    @GeneratedValue
    @Column(name="photo_id")
    private int photoId;

    private String path;

    private String fileDownloadUri;

    private long size;

    private String type;

    @Column(name="filename")
    private String fileName;


    @ManyToOne
    @JoinColumn(name="item_id")
    private ItemEntity item;




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

    public ItemEntity getItem() {
        return item;
    }

    public void setItem(ItemEntity item) {
        this.item = item;
    }

    public String getFileDownloadUri() {
        return fileDownloadUri;
    }

    public void setFileDownloadUri(String fileDownloadUri) {
        this.fileDownloadUri = fileDownloadUri;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
