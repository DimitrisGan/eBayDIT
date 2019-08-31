package com.ted.eBayDIT.service.impl;


import com.ted.eBayDIT.dto.PhotoDto;
import com.ted.eBayDIT.entity.DefaultPhotosConstants;
import com.ted.eBayDIT.entity.PhotoEntity;
import com.ted.eBayDIT.repository.PhotoRepository;
import com.ted.eBayDIT.service.PhotoService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private PhotoRepository photoRepository;


    //initialize db with 2 admins
    @PostConstruct
    private void initPhotosInDB() {

        Path currentPath = Paths.get(".");
        Path absolutePath = currentPath.toAbsolutePath();

        if (photoRepository.findByFileName(DefaultPhotosConstants.ITEM_PHOTO) == null) {
            PhotoEntity itemPhoto = new PhotoEntity(); //set filename

            itemPhoto.setFileName(DefaultPhotosConstants.ITEM_PHOTO);
            itemPhoto.setPath(absolutePath + "/src/main/resources/static/photos/");

            photoRepository.save(itemPhoto);
        }

        if (photoRepository.findByFileName(DefaultPhotosConstants.AUCTION_PHOTO) == null) {
            PhotoEntity auctionPhoto = new PhotoEntity();
            auctionPhoto.setFileName(DefaultPhotosConstants.AUCTION_PHOTO); //set filename

            auctionPhoto.setPath(absolutePath + "/src/main/resources/static/photos/");

            photoRepository.save(auctionPhoto);
        }

    }


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
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        PhotoEntity photoEntity = modelMapper.map(photoDtO, PhotoEntity.class);

        photoRepository.save(photoEntity);

    }

    @Override
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = Paths.get("." + "/src/main/resources/static/photos/" ).resolve(fileName).normalize();

//            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
//                throw new MyFileNotFoundException("File not found " + fileName);
                throw new RuntimeException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
//            throw new MyFileNotFoundException("File not found " + fileName, ex);
            throw new RuntimeException("File not found " + fileName, ex);
        }
    }


    //=================================================================================================
    //=================================================================================================


//    private final Path fileStorageLocation;
//
//    @Autowired
//    public FileStorageService(FileStorageProperties fileStorageProperties) {
//        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
//                .toAbsolutePath().normalize();
//
//        try {
//            Files.createDirectories(this.fileStorageLocation);
//        } catch (Exception ex) {
//            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
//        }
//    }
//
//    public String storeFile(MultipartFile file) {
//        // Normalize file name
//        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//
//        try {
//            // Check if the file's name contains invalid characters
//            if(fileName.contains("..")) {
//                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
//            }
//
//            // Copy file to the target location (Replacing existing file with the same name)
//            Path targetLocation = this.fileStorageLocation.resolve(fileName);
//            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
//
//            return fileName;
//        } catch (IOException ex) {
//            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
//        }
//    }
//
//    public Resource loadFileAsResource(String fileName) {
//        try {
//            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
//            Resource resource = new UrlResource(filePath.toUri());
//            if(resource.exists()) {
//                return resource;
//            } else {
//                throw new MyFileNotFoundException("File not found " + fileName);
//            }
//        } catch (MalformedURLException ex) {
//            throw new MyFileNotFoundException("File not found " + fileName, ex);
//        }
//    }


}
