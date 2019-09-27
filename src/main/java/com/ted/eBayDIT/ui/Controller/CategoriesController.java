package com.ted.eBayDIT.ui.Controller;


import com.ted.eBayDIT.entity.CategoryEntity;
import com.ted.eBayDIT.repository.CategoryRepository;
import com.ted.eBayDIT.ui.model.response.CategoriesResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping
public class CategoriesController {


    @Autowired
    CategoryRepository categRepo;


    @GetMapping("/categories/children/{parentId}")
    public ResponseEntity<Object> getSubCategories(@PathVariable Integer parentId){ //get the sub-categories

        List<CategoriesResponse> returnCategList =new ArrayList<>();


        List<CategoryEntity> categories = categRepo.findChildrenCategories(parentId);
        ModelMapper modelMapper = new ModelMapper();modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        for (CategoryEntity category : categories) {
            CategoriesResponse categResp = modelMapper.map(category, CategoriesResponse.class);
            returnCategList.add(categResp);
        }

        return new ResponseEntity<>(returnCategList, HttpStatus.OK);

    }


    @GetMapping("/categories/level/{level}")
    public ResponseEntity<Object> getAllByLevelCategories(@PathVariable Integer level){

        List<CategoriesResponse> returnCategList =new ArrayList<>();
        List<CategoryEntity> categories = categRepo.findByLevel(level);
        ModelMapper modelMapper = new ModelMapper();modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        for (CategoryEntity category : categories) {
            CategoriesResponse categResp = modelMapper.map(category, CategoriesResponse.class);
            returnCategList.add(categResp);
        }

        return new ResponseEntity<>(returnCategList, HttpStatus.OK);

    }


    @GetMapping("/categories/root")
    public ResponseEntity<Object> getAllRootCategories(){

        List<CategoriesResponse> returnCategList =new ArrayList<>();
        List<CategoryEntity> categories = categRepo.findRootCategories();
        ModelMapper modelMapper = new ModelMapper();modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        for (CategoryEntity category : categories) {
            CategoriesResponse categResp = modelMapper.map(category, CategoriesResponse.class);
            returnCategList.add(categResp);
        }

        return new ResponseEntity<>(returnCategList, HttpStatus.OK);

    }

    @GetMapping("/categories/all")
    public ResponseEntity<Object> getAllCategories(){

        List<CategoriesResponse> returnCategList =new ArrayList<>();
        List<CategoryEntity> categories = categRepo.findAll();
        ModelMapper modelMapper = new ModelMapper();modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        for (CategoryEntity category : categories) {
            CategoriesResponse categResp = modelMapper.map(category, CategoriesResponse.class);
            returnCategList.add(categResp);
        }

        return new ResponseEntity<>(returnCategList, HttpStatus.OK);

    }

}
