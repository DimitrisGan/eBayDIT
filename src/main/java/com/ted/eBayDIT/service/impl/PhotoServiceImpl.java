package com.ted.eBayDIT.service.impl;


import com.ted.eBayDIT.dto.PhotoDto;
import com.ted.eBayDIT.entity.PhotoEntity;
import com.ted.eBayDIT.repository.PhotoRepository;
import com.ted.eBayDIT.service.PhotoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private PhotoRepository photoRepository;


    @Override
    public void savePhotoImage(PhotoDto photoDtO, MultipartFile imageFile) throws Exception {
        // this gets us to src/main/resources without knowing the full path (hardcoding)
        Path currentPath = Paths.get(".");
        Path absolutePath = currentPath.toAbsolutePath();
        photoDtO.setPath(absolutePath + "/src/main/resources/static/photos/");
        byte[] bytes = imageFile.getBytes();
        Path path = Paths.get(photoDtO.getPath() + imageFile.getOriginalFilename());
        Files.write(path, bytes);

    }




    @Override
    public void save(PhotoDto photoDtO) {

        ModelMapper modelMapper = new ModelMapper();
        PhotoEntity photoEntity = modelMapper.map(photoDtO, PhotoEntity.class);

        photoRepository.save(photoEntity);

    }
}
