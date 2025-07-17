package com.ecom.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecom.model.Cart;
import com.ecom.model.Category;
import com.ecom.model.UserDtls;
import com.ecom.service.CartService;
import com.ecom.service.CategoryService;
import com.ecom.service.ProductService;
import com.ecom.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ProductService productService;
	@Autowired
	private UserService userService;
	@Autowired
	private CartService cartService;

	
	@GetMapping({"","/"})
	public String home() {
		System.out.println("home1");
		return "user/home";
	}
	
	@ModelAttribute
	public void getUserDetails(Principal p,Model m) {
		if(p!=null) {
			
			String email=p.getName();
			UserDtls userDtls =userService.getUserByEmail(email);
			m.addAttribute("user",userDtls);
		}
		
		List<Category> AllActiveCategory=categoryService.getAllActiveCategory();
		m.addAttribute("categories",AllActiveCategory);
		
	}
//	@GetMapping("/addcart")
//	public String addToCart(@RequestParam Integer pid,@RequestParam Integer uid,HttpSession session) {
//		Cart saveCart=cartService.saveCart(pid, uid);
//		if(ObjectUtils.isEmpty(saveCart)) {
//			session.setAttribute("errorMsg", "Product add to cart failed");
//			
//		}else {
//			session.setAttribute("succMsg", "Product added to cart");
//		}
//		return "redirect:/product/"+pid;
//	}
	
	@GetMapping("/addCart")
	public String addToCart(@RequestParam Integer pid, @RequestParam Integer uid,HttpSession session) {
		Cart saveCart = cartService.saveCart(pid, uid);
		
		if (ObjectUtils.isEmpty(saveCart)) {
			session.setAttribute("errorMsg", "Product add to cart failed");
		}else {
			session.setAttribute("succMsg", "Product added to cart");
		}
		return "redirect:/product/" + pid;
	}

}
