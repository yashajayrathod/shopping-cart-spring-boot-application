package com.ecom.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ecom.model.Product;

public interface ProductService {
	
	public Product saveProduct(Product product);
	public Boolean deleteProduct(Integer id);
	public Product updateProduct(Product product,MultipartFile file);
	public Product getProductById(Integer id);
	public List<Product> getAllProducts();
	List<Product> getAllActiveProducts(String category);
	

}
