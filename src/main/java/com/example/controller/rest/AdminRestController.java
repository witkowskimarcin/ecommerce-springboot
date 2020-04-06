package com.example.controller.rest;

import com.example.model.*;
import com.example.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/panel")
public class AdminRestController
{
	private ImageService imageService;
	private CategoryService categoryService;
	private SubcategoryService subcategoryService;
	private ProductService productService;
	private PromotedProductService promotedProductService;
	private OpportunityService opportunityService;
	private OrderService orderService;
	private OrderDetailService orderDetailService;

	public AdminRestController(ProductService productService, ImageService imageService, CategoryService categoryService, SubcategoryService subcategoryService, PromotedProductService promotedProductService, OpportunityService opportunityService, OrderService orderService, OrderDetailService orderDetailService)
	{
		this.productService = productService;
		this.imageService = imageService;
		this.categoryService = categoryService;
		this.subcategoryService = subcategoryService;
		this.promotedProductService = promotedProductService;
		this.opportunityService = opportunityService;
		this.orderService = orderService;
		this.orderDetailService = orderDetailService;
	}

	//--------------------------------
	// Categories
	//--------------------------------
	
	@GetMapping("/admin/categories")
	public ResponseEntity getAllCategories() {

		return new ResponseEntity(categoryService.getAllCategories(), HttpStatus.OK);
	}
	
	@GetMapping("/admin/category/{cid}")
	public ResponseEntity getCategory(@PathVariable(value = "cid", required=true) Long id) {
		return new ResponseEntity(categoryService.getCategoryById(id), HttpStatus.OK);
	}

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

	@GetMapping("/admin/category/{cid}/subcategories")
	public ResponseEntity getAllSubcategoriesByCategoryId(@PathVariable(value = "cid", required=true) Long id) {

		return new ResponseEntity(subcategoryService.getAllSubcategoriesByCategoryId(id), HttpStatus.OK);
	}

	@GetMapping("/admin/subcategory/{sid}")
	public ResponseEntity getSubcategory(@PathVariable(value = "sid", required=true) Long sid) {

		return new ResponseEntity(subcategoryService.getSubcategoryById(sid), HttpStatus.OK);
	}

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

	@GetMapping("/admin/subcategory/{sid}/products")
	public ResponseEntity getAllProductsBySubcategoryId(@PathVariable(value = "sid", required=true) Long id) {
		return new ResponseEntity(productService.getProductsBySubcategoryId(id), HttpStatus.OK);
	}

	@GetMapping("/admin/product/{pid}")
	public ResponseEntity getProduct(@PathVariable(value = "pid", required=true) Long id) {
		return new ResponseEntity(productService.getProductById(id), HttpStatus.OK);
	}

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

	@GetMapping("/admin/promotedproducts")
	public ResponseEntity promotedProducts() {
		return new ResponseEntity(promotedProductService.getAll(), HttpStatus.OK);
	}

	@PostMapping("/admin/promotedproduct/add")
	public ResponseEntity promotedProductsAdd(@Valid @RequestBody PromotedProductModel pp) {
		promotedProductService.addPromotedProduct(pp);
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

	@GetMapping("/admin/opportunity")
	public ResponseEntity getOpportunity() {

		return new ResponseEntity(opportunityService.getOpportunity(),HttpStatus.OK);
	}

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

		return new ResponseEntity(orderDetailService.getDetailsByOrderId(id), HttpStatus.OK);
	}

	//--------------------------------
	// !Order
	//--------------------------------
}
