package com.ted.eBayDIT.service.impl;


import com.ted.eBayDIT.dto.ItemDto;
import com.ted.eBayDIT.dto.PhotoDto;
import com.ted.eBayDIT.entity.DefaultPhotosConstants;
import com.ted.eBayDIT.entity.PhotoEntity;
import com.ted.eBayDIT.repository.PhotoRepository;
import com.ted.eBayDIT.service.PhotoService;
import com.ted.eBayDIT.ui.model.response.PhotoResponseModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    @Value("${server.address}")
    private String serverAddress;

    @Value("${server.port}")
    private String serverPort;


    //initialize db with default photos
    @PostConstruct
    private void initPhotosInDB()  {

        Path currentPath = Paths.get(".");
        Path absolutePath = currentPath.toAbsolutePath();

        if (photoRepository.findByFileName(DefaultPhotosConstants.ITEM_PHOTO) == null) {
            PhotoEntity itemPhoto = new PhotoEntity(); //set filename

            UriComponents uriComponents = UriComponentsBuilder.newInstance()
                    .scheme("https").host(serverAddress).port(Integer.parseInt(serverPort)).path("/api/downloadFile/").path(DefaultPhotosConstants.ITEM_PHOTO).build();


            itemPhoto.setFileDownloadUri(uriComponents.toUriString());

            itemPhoto.setFileName(DefaultPhotosConstants.ITEM_PHOTO);
            itemPhoto.setPath(absolutePath + "/src/main/resources/static/photos/");

            photoRepository.save(itemPhoto);
        }

        if (photoRepository.findByFileName(DefaultPhotosConstants.AUCTION_PHOTO) == null) {

            UriComponents uriComponents = UriComponentsBuilder.newInstance()
                    .scheme("https").host(serverAddress).port(Integer.parseInt(serverPort)).path("/api/downloadFile/").path(DefaultPhotosConstants.AUCTION_PHOTO).build();


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

        ModelMapper modelMapper = new ModelMapper();modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        PhotoEntity photoEntity = modelMapper.map(photoDtO, PhotoEntity.class);

        photoRepository.save(photoEntity);

    }

    @Override
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = Paths.get("." + "/src/main/resources/static/photos/" ).resolve(fileName).normalize();

            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("File not found " + fileName, ex);
        }
    }

    @Override
    public PhotoDto loadDefaultItemImage() {

        PhotoEntity photoEntity = this.photoRepository.findByFileName(DefaultPhotosConstants.ITEM_PHOTO);
        ModelMapper modelMapper = new ModelMapper();modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        return modelMapper.map(photoEntity,PhotoDto.class);
    }

    @Override
    public PhotoDto loadDefaultNkuaImage() {
        PhotoEntity photoEntity = this.photoRepository.findByFileName(DefaultPhotosConstants.NKUA_PHOTO);
        ModelMapper modelMapper = new ModelMapper();modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        return modelMapper.map(photoEntity,PhotoDto.class);
    }

    @Override
    public PhotoDto preparePhoto(MultipartFile imageFile, ItemDto newlyCreatedItemDto) {

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(imageFile.getOriginalFilename())
                .toUriString();

        PhotoDto photoDto = new PhotoDto();
        photoDto.setFileName(imageFile.getOriginalFilename());
        photoDto.setFileDownloadUri(fileDownloadUri);
        photoDto.setItem(newlyCreatedItemDto);
        photoDto.setSize(imageFile.getSize());
        photoDto.setType(imageFile.getContentType());

        return photoDto;
    }

    @Override
    public void deletePhoto(int photoId) {
        PhotoEntity photo2delete = this.photoRepository.findByPhotoId(photoId);
        this.photoRepository.delete(photo2delete);
    }

    @Override
    public PhotoDto getDefaultAUctionImage(){

        PhotoEntity photoEntity = this.photoRepository.findByFileName(DefaultPhotosConstants.AUCTION_PHOTO);
        ModelMapper modelMapper = new ModelMapper();modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        return modelMapper.map(photoEntity,PhotoDto.class);

    }

    @Override
    public PhotoResponseModel addDefaultPhotoIfNoPhotosExist(){
        ModelMapper modelMapper = new ModelMapper();modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        PhotoDto defaultPhotoDto = loadDefaultItemImage();
        return modelMapper.map(defaultPhotoDto, PhotoResponseModel.class);
    }




}
