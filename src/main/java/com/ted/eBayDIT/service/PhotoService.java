package com.ted.eBayDIT.service;


import com.ted.eBayDIT.dto.ItemDto;
import com.ted.eBayDIT.dto.PhotoDto;
import com.ted.eBayDIT.ui.model.response.PhotoResponseModel;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface PhotoService {

    void savePhotoImage(PhotoDto photoDtO, MultipartFile imageFile) throws Exception;

    void save(PhotoDto photoDtO);

    Resource loadFileAsResource(String fileName);

    PhotoDto loadDefaultItemImage();

    PhotoDto getDefaultAUctionImage();

    PhotoDto loadDefaultNkuaImage();

    PhotoDto preparePhoto(MultipartFile imageFile, ItemDto newlyCreatedItemDto);

    void deletePhoto(int photoId);

    PhotoResponseModel addDefaultPhotoIfNoPhotosExist();
}
