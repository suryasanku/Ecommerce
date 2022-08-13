package com.ecom.service.impl;

import com.ecom.entity.Category;
import com.ecom.entity.Order;
import com.ecom.entity.Product;
import com.ecom.exception.ResourceNotFoundException;
import com.ecom.payload.CategoryDto;
import com.ecom.payload.OrderDto;
import com.ecom.repository.CategoryRepo;
import com.ecom.service.CategoryService;
import com.ecom.service.FileService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private FileService fileService;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

//        dto -> entity

        Category category = this.modelMapper.map(categoryDto, Category.class);

        Category savedCategory = this.categoryRepo.save(category);

//        entity -> dto

        CategoryDto savedCategoryDto = this.modelMapper.map(savedCategory, CategoryDto.class);
        return savedCategoryDto;
    }

    @Override
    public List<CategoryDto> getCategories() {
        List<Category> categories = this.categoryRepo.findAll();
        List<CategoryDto> dtos = categories.stream().map(cat -> this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
        return dtos;
    }

    @Override
    public CategoryDto update(CategoryDto c, Integer cid) {
        Category category = this.categoryRepo.findById(cid).orElseThrow(() -> new ResourceNotFoundException("category", " category id ", cid + ""));
        category.setTitle(c.getTitle());
        category.setDesc(c.getDesc());
        category.setBannerName(c.getBannerName());
        Category updatedCat = this.categoryRepo.save(category);
        return this.modelMapper.map(updatedCat, CategoryDto.class);
    }

    @Override
    public void delete(Integer cid) {

    	Category category=this.categoryRepo.findById(cid).orElseThrow(()-> new ResourceNotFoundException("Category", "category id", cid + ""));
    	this.categoryRepo.delete(category);
    }

	@Override
	public CategoryDto getById(Integer categoryId) {
		// TODO Auto-generated method stub
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category", "category id ", categoryId + ""));

        return this.modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public void deleteCategoryImage(Integer categoryId, String categoryPath) throws IOException {
		// TODO Auto-generated method stub
		Category category=this.categoryRepo.findById(categoryId).orElseThrow(ResourceNotFoundException::new);
		String bannerName=category.getBannerName();
		 String fullPath=categoryPath+File.separator+bannerName;
		 this.fileService.deleteFileIfExists(fullPath);
		 category.setBannerName(null);
		 this.categoryRepo.save(category);
	}

	
}
