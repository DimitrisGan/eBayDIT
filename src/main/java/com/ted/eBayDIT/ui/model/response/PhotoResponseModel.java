package com.ted.eBayDIT.ui.model.response;


public class PhotoResponseModel {


//    private int photoId;
    private String fileName;
    private String fileDownloadUri;
    private String type;
    private long size;

//    public PhotoResponseModel(String fileName, String fileDownloadUri, String type, long size) {
//        this.fileName = fileName;
//        this.fileDownloadUri = fileDownloadUri;
//        this.type = type;
//        this.size = size;
//    }


    //    private String path;

//    public int getPhotoId() {
//        return photoId;
//    }
//
//    public void setPhotoId(int photoId) {
//        this.photoId = photoId;
//    }

//    public String getPath() {
//        return path;
//    }
//
//    public void setPath(String path) {
//        this.path = path;
//    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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
