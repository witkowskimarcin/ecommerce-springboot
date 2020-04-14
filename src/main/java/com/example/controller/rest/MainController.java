package com.example.controller.rest;

import com.example.model.*;
import com.example.repository.*;
import com.example.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*", allowCredentials = "true", maxAge = 3600)
public class MainController
{
	private UserService userService;
	private ImageService imageService;
	private CategoryService categoryService;
	private SubcategoryService subcategoryService;
	private ProductService productService;
	private PromotedProductService promotedProductService;
	private OpportunityService opportunityService;
	private OrderService orderService;
	private OrderDetailService orderDetailService;
	private CartService cartService;

	public MainController(UserService userService, ImageService imageService, CategoryService categoryService, SubcategoryService subcategoryService, ProductService productService, PromotedProductService promotedProductService, OpportunityService opportunityService, OrderService orderService, OrderDetailService orderDetailService, CartService cartService)
	{
		this.userService = userService;
		this.imageService = imageService;
		this.categoryService = categoryService;
		this.subcategoryService = subcategoryService;
		this.productService = productService;
		this.promotedProductService = promotedProductService;
		this.opportunityService = opportunityService;
		this.orderService = orderService;
		this.orderDetailService = orderDetailService;
		this.cartService = cartService;
	}

	// --------------------------------------
	// USER
	// --------------------------------------

	@GetMapping(value="/getsessionid")
	public ResponseEntity getSessionId(){

		Map<String,Object> map = new HashMap<>();
		map.put("JSESSIONID",userService.getSessionId());
		return new ResponseEntity(map, HttpStatus.OK);
	}

	@GetMapping(value="/logged")
	public ResponseEntity<SessionModel> logged(HttpSession session){

		SessionModel s = userService.logged(session);
		return new ResponseEntity<>(s, HttpStatus.OK);
	}

	// --------------------------------------
	// !USER
	// --------------------------------------

	// --------------------------------------
	// CATEGORY
	// --------------------------------------

	@GetMapping(value="/categories")
	public ResponseEntity<List<CategoryModel>> categories(){
		return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
	}

	@GetMapping(value="/category/{id}")
	public ResponseEntity<CategoryModel> category(@PathVariable(value = "id") Long id){
		return new ResponseEntity<>(categoryService.getCategoryById(id), HttpStatus.OK);
	}

	// --------------------------------------
	// !CATEGORY
	// --------------------------------------

	// --------------------------------------
	// SUBCATEGORY
	// --------------------------------------

	@GetMapping("/subcategory/{sid}")
	public ResponseEntity getSubcategory(@PathVariable(value = "sid", required=true) Long sid) {

		return new ResponseEntity(subcategoryService.getSubcategoryById(sid), HttpStatus.OK);
	}

	@GetMapping(value="/category/{id}/subcategories")
	public ResponseEntity<List<SubcategoryModel>> subcategories(@PathVariable(value = "id") Long id){
		return new ResponseEntity<>(subcategoryService.getAllSubcategoriesByCategoryId(id), HttpStatus.OK);
	}

	// --------------------------------------
	// !SUBCATEGORY
	// --------------------------------------

	// --------------------------------------
	// PRODUCTS
	// --------------------------------------

	@GetMapping(value="/subcategory/{sid}/products", produces = "application/json")
	public ResponseEntity<List<ProductModel>> getProductsBySubcategoryId(@PathVariable(value = "sid") Long id) {
		return new ResponseEntity<>(productService.getProductsBySubcategoryId(id), HttpStatus.OK);
	}

	@GetMapping(value="/subcategory/{sid}/category", produces = "application/json")
	public ResponseEntity<CategoryModel> getCategoryBySubcategoryId(@PathVariable(value = "sid") Long id) {
		return new ResponseEntity<>(subcategoryService.getCategoryBySubcategoryId(id), HttpStatus.OK);
	}

	@GetMapping(value="/product/{id}", produces = "application/json")
	public ResponseEntity<ProductModel> getProductById(@PathVariable Long id) {
		return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
	}

	@GetMapping(value="/product/{id}/category", produces = "application/json")
	public ResponseEntity<CategoryModel> getCategoryOfProduct(@PathVariable Long id) {
		return new ResponseEntity<>(productService.getCategoryOfProduct(id), HttpStatus.OK);
	}

	@GetMapping(value="/product/{id}/subcategory", produces = "application/json")
	public ResponseEntity<SubcategoryModel> getSubcategoryOfProduct(@PathVariable Long id) {
		return new ResponseEntity<>(productService.getSubcategoryOfProduct(id), HttpStatus.OK);
	}

	@GetMapping(value="/promotedproducts")
	public ResponseEntity<List<PromotedProductModel>> promotedproducts(){
		return new ResponseEntity<>(promotedProductService.getAllPromotedProducts(), HttpStatus.OK);
	}

	@GetMapping(value="/opportunity")
	public ResponseEntity<OpportunityModel> opportunity(){
		return new ResponseEntity<>(opportunityService.getOpportunity(), HttpStatus.OK);
	}

	// --------------------------------------
	// !PRODUCTS
	// --------------------------------------

	// --------------------------------------
	// CART
	// --------------------------------------

//	@GetMapping(value="/cart", produces = "application/json")
//	public ResponseEntity getCart() {
//
//		return new ResponseEntity(cartService.getCart(), HttpStatus.OK);
//	}
//
//	@PutMapping(value = "/cart/product/{id}/add", produces = "application/json")
//	public ResponseEntity addProductToCart(@PathVariable(value = "id", required=true) Long pid) {
//
//		cartService.addProductToCart(pid);
//		return new ResponseEntity(HttpStatus.OK);
//	}
//
//	@PutMapping(value = { "/cart/product/{id}/plus" })
//	public ResponseEntity cartPlus(@PathVariable(value = "id", required=true) Long id) {
//
//		cartService.incrementAmountOfProduct(id);
//		return new ResponseEntity(HttpStatus.OK);
//	}
//
//	@PutMapping(value = { "/cart/product/{id}/minus" })
//	public ResponseEntity cartMinus(@PathVariable(value = "id", required=true) Long id) {
//
//		cartService.decrementAmountOfProduct(id);
//		return new ResponseEntity(HttpStatus.OK);
//	}
//
//	@DeleteMapping("/cart/clear")
//	public ResponseEntity removeCart() {
//
//		cartService.clearCart();
//		return new ResponseEntity(HttpStatus.OK);
//	}

	// --------------------------------------
	// !CART
	// --------------------------------------

	// --------------------------------------
	// ORDER
	// --------------------------------------

	//TODO

	// --------------------------------------
	// !ORDER
	// --------------------------------------
}
