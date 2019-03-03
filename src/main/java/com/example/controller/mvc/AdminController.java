package com.example.controller.mvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import com.example.model.*;
import com.example.repository.*;
import com.example.message.request.ProductAddForm;
import com.example.security.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminController {
	
	@Autowired private ProductRepository productRepository;
	@Autowired private ImageRepository imageRepository;
	@Autowired private SubcategoryRepository subcategoryRepository;
	@Autowired private CategoryRepository categoryRepository;
	@Autowired private PromotedProductRepository promotedProductRepository;
	@Autowired private OpportunityRepository opportunityRepository;
	@Autowired private OrderRepository orderRepository;
	@Autowired private OrderDetailRepository orderDetailRepository;
	@Autowired private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	@Autowired private ProductService productService;

	@GetMapping("/admin")
	public String adminPanel() {
		return "/admin/admin";
	}

	//--------------------------------
	// Categories
	//--------------------------------
	
	@GetMapping("/admin/categories")
	public ModelAndView adminPanelShowCategories() {
		ModelAndView model = new ModelAndView();
		Iterable<Category> categories = categoryRepository.findAll();
		
//		categories.forEach((c)->{
//			System.out.println(c.getId());
//			System.out.println(c.getName());
//		});
//
//		for (Category c : categories) {
//			System.out.println(c.getId());
//			System.out.println(c.getName());
//		}
		
		model.addObject("categories", categories);
		model.setViewName("/admin/categories");
		return model;
	}
	
	@GetMapping("/admin/categories/edit")
	public ModelAndView adminEditCategory(@RequestParam(value = "id", required=true) long id) {
		ModelAndView model = new ModelAndView();
		model.setViewName("/admin/categoryEdit");
		Category category = categoryRepository.findById(id);
		model.addObject(category);
		return model;
	}
	
	@PostMapping("/admin/categories/edit")
	public String adminEditCategory(@RequestParam(value = "id", required=true) long id, @Valid Category category) {
		if(category!=null) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			String sql = "UPDATE category SET name = '"+category.getName()+"' WHERE id = "+id+";";
			namedParameterJdbcTemplate.update(sql, paramMap);
			System.out.println("Edytowano category pomyslnie");
		}
		return "redirect:/admin/categories";
	}
	
	@GetMapping("/admin/categories/add")
	public String adminAddCategoryForm() {
		return "/admin/categoryAdd";
	}
	
	@PostMapping("/admin/categories/add")
	public String adminAddCategory(@Valid Category category) {
		if(category!=null) {
			categoryRepository.save(category);
			System.out.println("Dodano category pomyslnie");
		}
		return "redirect:/admin/categories";
	}
	
	@GetMapping("/admin/categories/remove")
	public String adminRemoveCategory(@RequestParam(value = "id", required=true) long id) {
		
		categoryRepository.deleteById(id);
		
		return "redirect:/admin/categories";
	}

	//--------------------------------
	// !Categories
	//--------------------------------

	//--------------------------------
	// Subategories
	//--------------------------------
	
	@GetMapping("/admin/categories/subcategories")
	public ModelAndView adminManageCategory(@RequestParam(value = "id", required=true) long id) {
		ModelAndView model = new ModelAndView();
		model.setViewName("/admin/subcategories");
		Category category = categoryRepository.findById(id);
		model.addObject(category);
		
		Iterable<Subcategory> subcategories = subcategoryRepository.findAllByCategory(category);
		
		for (Subcategory s : subcategories) {
			System.out.println(s.getId());
			System.out.println(s.getName());
		}
		
		model.addObject("subcategories", subcategories);
		
		return model;
	}
	
	@GetMapping("/admin/categories/category/addSubCat")
	public ModelAndView adminAddSubCategoryForm(@RequestParam(value = "id", required=true) long id) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/admin/subcategoryAdd");
		modelAndView.addObject("catId",id);
		return modelAndView;
	}
	
	@PostMapping("/admin/categories/category/addSubCat")
	public String adminAddSubCategory(@Valid Subcategory subcategory, @RequestParam(value = "id", required=true) long id) {
		if(subcategory!=null) {
			Category category = categoryRepository.findById(id);
			subcategory.setCategory(category);
			category.addSubcategory(subcategory);
			subcategoryRepository.save(subcategory);
			System.out.println("Dodano subcategory pomyslnie");
		}
		return "redirect:/admin/categories/subcategories?id="+id;
	}
	
	@GetMapping("/admin/categories/category/editSubCat")
	public ModelAndView adminEditSubCategory(@RequestParam(value = "id", required=true) long id) {
		ModelAndView model = new ModelAndView();
		model.setViewName("/admin/subcategoryEdit");
		Subcategory subcategory = subcategoryRepository.findById(id);
		model.addObject(subcategory);
		model.addObject("catId",subcategory.getCategory().getId());
		return model;
	}
	
	@PostMapping("/admin/categories/category/editSubCat")
	public String adminEditSubCategory(
			@RequestParam(value = "id", required=true) long id,
			@Valid Subcategory subcategory) {
		if(subcategory!=null) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			String sql = "UPDATE subcategory SET name = '"+subcategory.getName()+"' WHERE id = "+id+";";
			namedParameterJdbcTemplate.update(sql, paramMap);
			System.out.println("Edytowano category pomyslnie");
		}
		return "redirect:/admin/categories/subcategories?id="+subcategoryRepository.findById(id).getCategory().getId();
	}
	
	@GetMapping("/admin/categories/category/removeSubCat")
	public String adminRemoveSubCategory(@RequestParam(value = "id", required=true) long id) {
		
		Subcategory s = subcategoryRepository.findById(id);
		subcategoryRepository.deleteById(id);
		
		return "redirect:/admin/categories/subcategories?id="+s.getCategory().getId();
	}

	//--------------------------------
	// !Subategories
	//--------------------------------
	
	//--------------------------------
	// Products 
	//--------------------------------
	
	@GetMapping("/admin/products")
	public ModelAndView adminPanelShowProducts(@RequestParam(value = "subCatId", required=true) long subCatId) {
		ModelAndView model = new ModelAndView();
		model.setViewName("/admin/products");
		Subcategory subcategory = subcategoryRepository.findById(subCatId);
		Iterable<Product> products = productRepository.findAllBySubcategory(subcategory);
		model.addObject("products", products);
		model.addObject("subCatId",subCatId);
		model.addObject("catId",subcategory.getCategory().getId());
		return model;
	}
	
	@GetMapping("/admin/products/add")
	public ModelAndView adminAddProductForm(@RequestParam(value = "subCatId", required=true) long subCatId) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/admin/productAdd");
		modelAndView.addObject("subCatId",subCatId);
		return modelAndView;
	}
	
	@PostMapping("/admin/products/add")
	public String adminAddProduct(@Valid ProductAddForm productAddForm, @RequestParam(value = "subCatId") long subCatId) throws IOException {

		if(productAddForm!=null) {
			Product product = new Product();
			product.setImages(new ArrayList<>());
			for(MultipartFile file : productAddForm.getFiles()){
				//MultipartFile file = productAddForm.getFiles();
				Image i = new Image();
				i.setImage(file.getBytes());
				imageRepository.save(i);
				product.getImages().add(i);
			}
			Subcategory s = subcategoryRepository.findById(subCatId);
			product.setSubcategory(s);
			product.setDescription(productAddForm.getDescription());
			product.setName(productAddForm.getName());
			product.setPrice(productAddForm.getPrice());
			product.setQuantity(productAddForm.getQuantity());
			productRepository.save(product);
			System.out.println("Dodano product pomyslnie");
		}
		return "redirect:/admin/products?subCatId="+subCatId;
	}
	
	@GetMapping("/admin/products/edit")
	public ModelAndView adminEditProduct(@RequestParam(value = "prodId", required=true) long product_id) {
		ModelAndView model = new ModelAndView();
		model.setViewName("/admin/productEdit");
		Product product = productRepository.findById(product_id);
		model.addObject("product", product);
		model.addObject("subCatId", product.getSubcategory().getId());
		return model;
	}
	
	@PostMapping("/admin/products/edit")
	public String adminEditProduct(@Valid Product product, @RequestParam(value = "prodId", required=true) long product_id) {
		Product p = productRepository.findById(product_id);
		if(product!=null) {

			p.setName(product.getName());
			p.setDescription(product.getDescription());
			p.setPrice(product.getPrice());
			p.setQuantity(product.getQuantity());
			productRepository.save(p);

			System.out.println("Edytowano product pomyslnie");
		}
		return "redirect:/admin/products?subCatId="+p.getSubcategory().getId();
	}
	
	@GetMapping("/admin/products/remove")
	public String adminRemoveProduct(@RequestParam(value = "prodId", required=true) long product_id) {
		long subCatId = productRepository.findById(product_id).getSubcategory().getId();
		productService.deleteById(product_id);
		return "redirect:/admin/products?subCatId="+subCatId;
	}

	//--------------------------------
	// !Products
	//--------------------------------

	//--------------------------------
	// PromotedProducts
	//--------------------------------

	@GetMapping("/admin/promotedProducts")
	public ModelAndView adminPanelPP() {
		ModelAndView model = new ModelAndView();
		Iterable<PromotedProduct> promotedProducts = promotedProductRepository.findAll();

		model.addObject("promotedProducts", promotedProducts);

		model.setViewName("/admin/promotedProducts");
		return model;
	}

	@GetMapping("/admin/promotedProducts/add")
	public String adminPanelPPAdd() {
		return "/admin/promotedProductAdd";
	}

	@PostMapping("/admin/promotedProducts/add")
	public String adminPanelPPAdd(long prodId) {

		if(prodId>0) {
			try {
				PromotedProduct pp = new PromotedProduct();
				Product p = productRepository.findById(prodId);
				if(p.getId()>0) {
					pp.setProduct(p);
					promotedProductRepository.save(pp);
					System.out.println("Dodano promotedProduct pomyslnie");
				}
			}
			catch (Exception e)
			{

			}
		}

		return "redirect:/admin/promotedProducts";
	}

	@GetMapping("/admin/promotedProducts/remove")
	public String adminPanelPPRemove(@RequestParam(value = "id", required=true) long id) {

		promotedProductRepository.deleteById(id);

		return "redirect:/admin/promotedProducts";
	}

	//--------------------------------
	// !PromotedProducts
	//--------------------------------

	//--------------------------------
	// MainPromotion
	//--------------------------------

	@GetMapping("/admin/opportunity")
	public ModelAndView adminPanelMP() {
		ModelAndView model = new ModelAndView();
		//Iterable<PromotedProduct> promotedProducts = promotedProductRepository.findAll();


		if(opportunityRepository.count()>0)
		{
			Opportunity o = opportunityRepository.findAll().iterator().next();
			model.addObject("exist",true);
			model.addObject("opportunity", o);

		}
		else {
			model.addObject("exist",false);
		}

		model.setViewName("/admin/opportunity");
		return model;
	}

	@GetMapping("/admin/opportunity/add")
	public String adminPanelMPAdd() {
		return "/admin/opportunityAdd";
	}

	@PostMapping("/admin/opportunity/add")
	public String adminPanelMPAdd(long prodId, String code) {


		if(opportunityRepository.count()==0) {
			if (prodId > 0) {
				try {
					Opportunity pp = new Opportunity();
					Product p = productRepository.findById(prodId);
					if (p.getId() > 0) {
						pp.setProduct(p);
						pp.setPromotionCode(code);
						opportunityRepository.save(pp);
						System.out.println("Dodano opportunity pomyslnie");
					}
				} catch (Exception e) {

				}
			}
		}

		return "redirect:/admin/opportunity";
	}

	@GetMapping("/admin/opportunity/remove")
	public String adminPanelMPRemove() {

		opportunityRepository.deleteAll();

		return "redirect:/admin/opportunity";
	}

	//--------------------------------
	// !MainPromotion
	//--------------------------------

	//--------------------------------
	// Order
	//--------------------------------

	@GetMapping("/admin/orders")
	public ModelAndView adminPanelOrder() {
		ModelAndView model = new ModelAndView();

		model.addObject("orders",orderRepository.findAll());

		model.setViewName("/admin/orders");
		return model;
	}

	@GetMapping("/admin/order/details")
	public ModelAndView adminPanelOrderDetails(@RequestParam(value = "oId", required=true) long id) {
		ModelAndView model = new ModelAndView();

		Order o = orderRepository.findById(id);
		model.addObject("order",o);
		model.addObject("orderDetails",o.getOrderDetails());

		model.setViewName("/admin/orderDetails");
		return model;
	}

	//--------------------------------
	// !Order
	//--------------------------------
}
