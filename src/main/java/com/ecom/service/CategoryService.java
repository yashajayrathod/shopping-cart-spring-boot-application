package com.ecom.service;

import java.util.List;

import com.ecom.model.Category;
import com.ecom.repository.CategoryRepository;

public interface CategoryService {
	public Category saveCategory(Category category);
	public Boolean existCategory(String name);
	public List<Category> getAllCategory();
	public Boolean deleteCategory(int id);
	public Category getCategoryById(int id);
	public List<Category> getAllActiveCategory();
	
	
	
	

}
