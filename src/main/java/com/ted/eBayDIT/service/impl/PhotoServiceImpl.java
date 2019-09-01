package com.ted.eBayDIT.service.impl;


import com.ted.eBayDIT.dto.PhotoDto;
import com.ted.eBayDIT.entity.DefaultPhotosConstants;
import com.ted.eBayDIT.entity.PhotoEntity;
import com.ted.eBayDIT.repository.PhotoRepository;
import com.ted.eBayDIT.service.PhotoService;
import org.apache.commons.io.IOUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@Service
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private PhotoRepository photoRepository;


    @Value("${server.address}")
    private String serverAddress;

    @Value("${server.port}")
    private String serverPort;


//    @LocalServerPort
//    private int port;

    //initialize db with 2 admins
    @PostConstruct
    private void initPhotosInDB()  {



        Path currentPath = Paths.get(".");
        Path absolutePath = currentPath.toAbsolutePath();


//        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/downloadFile/")
//                .path(DefaultPhotosConstants.ITEM_PHOTO)
//                .toUriString();



        if (photoRepository.findByFileName(DefaultPhotosConstants.ITEM_PHOTO) == null) {
            PhotoEntity itemPhoto = new PhotoEntity(); //set filename

            UriComponents uriComponents = UriComponentsBuilder.newInstance()
                    .scheme("http").host(serverAddress).port(Integer.parseInt(serverPort)).path("/api/downloadFile/").path(DefaultPhotosConstants.ITEM_PHOTO).build();



//            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                    .path("/downloadFile/")
//                    .path(DefaultPhotosConstants.ITEM_PHOTO)
//                    .toUriString();


//            FileInputStream input = null;
//            try {
//                input = new FileInputStream(DefaultPhotosConstants.ITEM_PHOTO);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//
//            }
//            try {
//                MultipartFile multipartFile = new MockMultipartFile("fileItem",
//                        DefaultPhotosConstants.ITEM_PHOTO, "image/png", IOUtils.toByteArray(input));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }



            // TODO AUTO NA XWNW MESA STH VASH http://localhost:8080/api/downloadFile/item_default.jpg

            itemPhoto.setFileDownloadUri(uriComponents.toUriString());

            itemPhoto.setFileName(DefaultPhotosConstants.ITEM_PHOTO);
            itemPhoto.setPath(absolutePath + "/src/main/resources/static/photos/");

            photoRepository.save(itemPhoto);
        }

        if (photoRepository.findByFileName(DefaultPhotosConstants.AUCTION_PHOTO) == null) {

            UriComponents uriComponents = UriComponentsBuilder.newInstance()
                    .scheme("http").host(serverAddress).port(Integer.parseInt(serverPort)).path("/api/downloadFile/").path(DefaultPhotosConstants.AUCTION_PHOTO).build();


            PhotoEntity auctionPhoto = new PhotoEntity();
            auctionPhoto.setFileName(DefaultPhotosConstants.AUCTION_PHOTO); //set filename

            auctionPhoto.setPath(absolutePath + "/src/main/resources/static/photos/");

            auctionPhoto.setFileDownloadUri(uriComponents.toUriString());
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

    @Override
    public PhotoDto loadDefaultItemImage() {

        PhotoEntity photoEntity = this.photoRepository.findByFileName(DefaultPhotosConstants.ITEM_PHOTO);
        ModelMapper modelMapper = new ModelMapper();


        return modelMapper.map(photoEntity,PhotoDto.class);
    }

    @Override
    public PhotoDto loadDefaultNkuaImage() {
        PhotoEntity photoEntity = this.photoRepository.findByFileName(DefaultPhotosConstants.NKUA_PHOTO);
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(photoEntity,PhotoDto.class);
    }

    @Override
    public PhotoDto getDefaultAUctionImage(){

        PhotoEntity photoEntity = this.photoRepository.findByFileName(DefaultPhotosConstants.AUCTION_PHOTO);
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(photoEntity,PhotoDto.class);

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
