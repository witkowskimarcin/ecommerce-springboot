package com.example.controller;

import com.example.entity.User;
import com.example.model.*;
import com.example.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/panel")
public class AdminController
{
	private CategoryService categoryService;
	private SubcategoryService subcategoryService;
	private ProductService productService;
	private PromotedProductService promotedProductService;
	private OpportunityService opportunityService;
	private OrderService orderService;
	private UserService userService;

	public AdminController(CategoryService categoryService, SubcategoryService subcategoryService, ProductService productService, PromotedProductService promotedProductService, OpportunityService opportunityService, OrderService orderService, UserService userService) {
		this.categoryService = categoryService;
		this.subcategoryService = subcategoryService;
		this.productService = productService;
		this.promotedProductService = promotedProductService;
		this.opportunityService = opportunityService;
		this.orderService = orderService;
		this.userService = userService;
	}

	//--------------------------------
	// Users
	//--------------------------------

	@PostMapping("/admin/user/add")
	public ResponseEntity addUser(@Valid @RequestBody UserModel user) {
		userService.register(user);
		return new ResponseEntity(HttpStatus.OK);
	}

	@DeleteMapping("/admin/user/{id}/remove")
	public ResponseEntity removeUser(@PathVariable(value = "id", required=true) Long id) {
		userService.removeUserById(id);
		return new ResponseEntity(HttpStatus.OK);
	}

	//--------------------------------
	// !Users
	//--------------------------------

	//--------------------------------
	// Categories
	//--------------------------------

	@PostMapping("/admin/category/add")
	public ResponseEntity addCategory(@Valid @RequestBody CategoryModel category) {
		categoryService.addCategory(category);
		return new ResponseEntity(HttpStatus.OK);
	}

	@PutMapping("/admin/category/{cid}/edit")
	public ResponseEntity editCategory(@PathVariable(value = "cid", required=true) Long id, @Valid @RequestBody CategoryModel category) {
		categoryService.editCategory(id, category);
		return new ResponseEntity(HttpStatus.OK);
	}

	@DeleteMapping("/admin/category/{cid}/remove")
	public ResponseEntity removeCategory(@PathVariable(value = "cid", required=true) Long id) {
		categoryService.removeCategoryById(id);
		return new ResponseEntity(HttpStatus.OK);
	}

	//--------------------------------
	// !Categories
	//--------------------------------

	//--------------------------------
	// Subategories
	//--------------------------------

	@PostMapping("/admin/category/{cid}/subcategory/add")
	public ResponseEntity addSubcategory(@PathVariable(value = "cid", required=true) Long id, @Valid @RequestBody SubcategoryModel subcategory) {
		subcategoryService.addSubcategory(id, subcategory);
		return new ResponseEntity(HttpStatus.OK);
	}

	@PutMapping("/admin/subcategory/{sid}/edit")
	public ResponseEntity editSubcategory(@PathVariable(value = "sid", required=true) Long id, @Valid @RequestBody SubcategoryModel subcategory) {
		subcategoryService.editSubcategory(id, subcategory);
		return new ResponseEntity(HttpStatus.OK);
	}

	@DeleteMapping("/admin/subcategory/{sid}/remove")
	public ResponseEntity removeSubcategory(@PathVariable(value = "sid", required=true) Long id) {
		subcategoryService.removeSubcategory(id);
		return new ResponseEntity(HttpStatus.OK);
	}

	//--------------------------------
	// !Subategories
	//--------------------------------
	
	//--------------------------------
	// Products 
	//--------------------------------

	@PostMapping("/admin/subcategory/{sid}/product/add")
	public ResponseEntity addProduct(@PathVariable(value = "sid", required=true) Long id, @Valid @RequestBody ProductModel product) {
		productService.addProduct(id, product);
		return new ResponseEntity(HttpStatus.OK);
	}

	@PutMapping("/admin/product/{pid}/edit")
	public ResponseEntity editProduct(@PathVariable(value = "pid", required=true) Long id, @Valid @RequestBody ProductModel product) {
		productService.editProduct(product);
		return new ResponseEntity(HttpStatus.OK);
	}

	@DeleteMapping("/admin/product/{pid}/remove")
	public ResponseEntity removeProduct(@PathVariable(value = "pid", required=true) Long id) {
		productService.removeProduct(id);
		return new ResponseEntity(HttpStatus.OK);
	}

	//--------------------------------
	// !Products
	//--------------------------------

	//--------------------------------
	// PromotedProducts
	//--------------------------------

	@PostMapping("/admin/promotedproduct/product/{id}/add")
	public ResponseEntity promotedProductsAdd(@PathVariable(value = "id", required=true) Long id) {
		promotedProductService.addPromotedProduct(id);
		return new ResponseEntity(HttpStatus.OK);
	}

	@DeleteMapping("/admin/promotedproduct/{id}/remove")
	public ResponseEntity promotedProductsRemove(@PathVariable(value = "id", required=true) Long id) {
		promotedProductService.removePromotedProduct(id);
		return new ResponseEntity(HttpStatus.OK);
	}

	//--------------------------------
	// !PromotedProducts
	//--------------------------------

	//--------------------------------
	// MainPromotion
	//--------------------------------


	@PostMapping("/admin/opportunity/set")
	public ResponseEntity setOpportunity(@Valid @RequestBody OpportunityModel opportunity) {

		opportunityService.setOpportunity(opportunity);
		return new ResponseEntity(HttpStatus.OK);
	}

	@DeleteMapping("/admin/opportunity/unset")
	public ResponseEntity unsetOpportunity() {

		opportunityService.unsetOpportunity();
		return new ResponseEntity(HttpStatus.OK);
	}

	//--------------------------------
	// !MainPromotion
	//--------------------------------

	//--------------------------------
	// Order
	//--------------------------------

	@GetMapping("/admin/orders")
	public ResponseEntity orders() {

		return new ResponseEntity(orderService.getAll(), HttpStatus.OK);
	}

	@GetMapping("/admin/order/{id}/details")
	public ResponseEntity orderDetails(@PathVariable Long id) {

		return new ResponseEntity(orderService.getDetailsByOrderId(id), HttpStatus.OK);
	}

	@DeleteMapping("/admin/order/{id}/remove")
	public ResponseEntity removeOrder(@PathVariable Long id) {

		orderService.removeOrder(id);
		return new ResponseEntity(HttpStatus.OK);
	}

	//--------------------------------
	// !Order
	//--------------------------------
}
