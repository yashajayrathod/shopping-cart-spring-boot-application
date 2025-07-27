package com.ecom.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecom.model.Cart;
import com.ecom.model.Category;
import com.ecom.model.OrderRequest;
import com.ecom.model.UserDtls;
import com.ecom.service.CartService;
import com.ecom.service.CategoryService;
import com.ecom.service.OrderService;
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
	
	@Autowired
	private OrderService orderService;

	
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
			Integer countCart =cartService.getCountCart(userDtls.getId());
			m.addAttribute("countCart",countCart);
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
	
	public String getMethodName(@RequestParam String param) {
		return new String();
	}
	@GetMapping("/cart")
	public String loadCartPage(Principal p,Model m) {
		
		UserDtls user= getLoggedInUserDetails(p);
		List<Cart> carts = cartService.getCartByUser(user.getId());
		m.addAttribute("carts",carts);
		if(carts.size()>0) {
		Double totalOrderPrice=carts.get(carts.size()-1).getTotalOrderPrice();
		m.addAttribute("totalOrderPrice",totalOrderPrice);
		}
		return "/user/cart";
	}
	@GetMapping("/cartQuantityUpdate")
	public String updateCartQuantity(@RequestParam String sy,@RequestParam Integer cid) {
		cartService.updateQuantity(sy,cid);
		return "redirect:/user/cart";
	}

	private UserDtls getLoggedInUserDetails(Principal p) {
		String email=p.getName();
		UserDtls userDtls= userService.getUserByEmail(email);
		return userDtls;
	}
//	@GetMapping("/order")
//	public String orderPage() {
//		return "/user/order";
//	}
	
	@GetMapping("/order")
	public String orderPage(Principal p, Model m) {
		UserDtls user = getLoggedInUserDetails(p);
		List<Cart> carts = cartService.getCartByUser(user.getId());
		m.addAttribute("carts", carts);
		if (carts.size() > 0) {
			Double orderPrice = carts.get(carts.size() - 1).getTotalOrderPrice();
			Double totalOrderPrice = carts.get(carts.size() - 1).getTotalOrderPrice() + 250 + 100;
			m.addAttribute("orderPrice", orderPrice);
			m.addAttribute("totalOrderPrice", totalOrderPrice);
		}
		return "/user/order";
	}

	
	@PostMapping("/save-order")
	public String saveOrder(@ModelAttribute OrderRequest request,Principal p) {
		UserDtls user =getLoggedInUserDetails(p);
		orderService.saveOrder(user.getId(), request);
		return "redirect:/user/success";
	}
	
	@GetMapping("/success")
	public String loadSuccess() {
		return "/user/success";
	}
	
	@GetMapping("/payment")
	public String payment() {
		return "/user/payment";
	}

}
