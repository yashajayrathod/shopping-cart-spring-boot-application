package com.ecom.service.impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ecom.model.Product;
import com.ecom.repository.ProductRepository;
import com.ecom.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	public ProductRepository productRepository;

	@Override
	public Product saveProduct(Product product) {
		return productRepository.save(product);
	}

//	@Override
//	public Boolean deleteProduct(Integer id) {
//		// TODO Auto-generated method stub
//		Product product= productRepository.findById(id).orElse(null);
//		if(!ObjectUtils.isEmpty(product)) {
//			productRepository.delete(product);
//			return true;
//		}
//		return false;
//	}
	
	@Override
	public Boolean deleteProduct(Integer id) {
		Product product = productRepository.findById(id).orElse(null);

		if (!ObjectUtils.isEmpty(product)) {
			productRepository.delete(product);
			return true;
		}
		return false;
	}

	@Override
	public Product updateProduct(Product product, MultipartFile image) {
		// TODO Auto-generated method stub
		Product dbProduct =getProductById(product.getId());
		String imageName= image.isEmpty()? dbProduct.getImage():image.getOriginalFilename();
		dbProduct.setTitle(product.getTitle());
		dbProduct.setDescription(product.getDescription());
		dbProduct.setCategory(product.getCategory());
		dbProduct.setPrice(product.getPrice());
		dbProduct.setStock(product.getStock());
		dbProduct.setImage(imageName);
		dbProduct.setIsActive(product.getIsActive());
		
		dbProduct.setDiscount(product.getDiscount());
		Double discount1=product.getPrice()*(product.getDiscount()/100.0);
		Double discountPrice=product.getPrice()-discount1;
		System.out.println(discountPrice);
		dbProduct.setDiscountPrice(discountPrice);
		
		Product updateProduct = productRepository.save(dbProduct);

		if (!ObjectUtils.isEmpty(updateProduct)) {

			if (!image.isEmpty()) {

				try {
					File saveFile = new ClassPathResource("static/img").getFile();

					Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "product_img" + File.separator
							+ image.getOriginalFilename());
					Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			return product;
		}

		return null;
	}

	@Override
	public Product getProductById(Integer id) {
		// TODO Auto-generated method stub
		Product product=productRepository.findById(id).orElse(null);
		return product;
	}

	@Override
	public List<Product> getAllProducts() {
		// TODO Auto-generated method stub
		
		return productRepository.findAll();
	}

	@Override
	public List<Product> getAllActiveProducts(String category) {
		// TODO Auto-generated method stub
		List<Product> products=null;
		
		if(ObjectUtils.isEmpty(category)) {
			products=productRepository.findByIsActiveTrue();
			
		}else {
			products=productRepository.findByCategory(category);
		}
		return products;
	}
	


}
