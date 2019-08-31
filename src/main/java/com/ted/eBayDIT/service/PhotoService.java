package com.ted.eBayDIT.service;


import com.ted.eBayDIT.dto.PhotoDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface PhotoService {

    void savePhotoImage(PhotoDto photoDtO, MultipartFile imageFile) throws Exception;

    void save(PhotoDto photoDtO);

    Resource loadFileAsResource(String fileName);
}
