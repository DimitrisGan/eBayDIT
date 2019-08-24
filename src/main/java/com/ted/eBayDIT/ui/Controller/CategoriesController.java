package com.ted.eBayDIT.ui.Controller;


import com.ted.eBayDIT.dto.CategoryDto;
import com.ted.eBayDIT.entity.CategoryEntity;
import com.ted.eBayDIT.repository.CategoryRepository;
import com.ted.eBayDIT.ui.model.response.CategoriesResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoriesController { //todo needs testing


    @Autowired
    CategoryRepository categRepo;

    @GetMapping(path ="/{id}") //add new bid for example
    public ResponseEntity<Object> getAllCategories(){

        List<CategoriesResponse> returnCategList =new ArrayList<>();
        List<CategoryEntity> categories = categRepo.findAll();
        ModelMapper modelMapper = new ModelMapper();

        for (CategoryEntity category : categories) {
            CategoriesResponse categResp = modelMapper.map(category, CategoriesResponse.class);
            returnCategList.add(categResp);
        }

        return new ResponseEntity<>(returnCategList, HttpStatus.OK);

    }

}
