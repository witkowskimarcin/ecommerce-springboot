package com.example.controller.rest;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.example.entity.*;
import com.example.model.*;
import com.example.repository.*;
import com.example.service.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*", allowCredentials = "true", maxAge = 3600)
public class MainRestController {

	@Autowired private ProductRepository productRepository;
	@Autowired private CategoryRepository categoryRepository;
	@Autowired private SubcategoryRepository subcategoryRepository;
	@Autowired private PromotedProductRepository promotedProductRepository;
	@Autowired private OpportunityRepository opportunityRepository;
	@Autowired private OrderRepository orderRepository;
	@Autowired private OrderDetailRepository orderDetailRepository;
	@Autowired private UserRepository userRepository;
	@Autowired private HttpSession session;

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

	public MainRestController(UserService userService, ImageService imageService, CategoryService categoryService, SubcategoryService subcategoryService, ProductService productService, PromotedProductService promotedProductService, OpportunityService opportunityService, OrderService orderService, OrderDetailService orderDetailService, CartService cartService)
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

	@GetMapping(value="/getsessionid")
	public ResponseEntity getSessionId(){

		Map<String,Object> map = new HashMap<>();
		map.put("JSESSIONID",session.getId());

		return new ResponseEntity(map, HttpStatus.OK);
	}

	@GetMapping(value="/categories")
	public ResponseEntity<List<CategoryModel>> categories(){
		return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
	}

	@GetMapping(value="/category/{id}")
	public ResponseEntity<CategoryModel> category(@PathVariable(value = "id") Long id){
		return new ResponseEntity<>(categoryService.getCategoryById(id), HttpStatus.OK);
	}

	@GetMapping(value="/category/{id}/subcategories")
	public ResponseEntity<List<SubcategoryModel>> subcategories(@PathVariable(value = "id") Long id){
		return new ResponseEntity<>(subcategoryService.getAllSubcategoriesByCategoryId(id), HttpStatus.OK);
	}

	@GetMapping(value="/opportunity")
	public ResponseEntity<OpportunityModel> opportunity(){
		return new ResponseEntity<>(opportunityService.getOpportunity(), HttpStatus.OK);
	}

	@GetMapping(value="/promotedproducts")
	public ResponseEntity<List<PromotedProductModel>> promotedproducts(){
		return new ResponseEntity<>(promotedProductService.getAll(), HttpStatus.OK);
	}

	@GetMapping(value="/logged")
	public ResponseEntity<SessionModel> logged(HttpSession session){

		SessionModel sessionModel = new SessionModel();
		UserModel currentUser = userService.getCurrentUser();
		CartModel cartModel = CartModel.getCartInSession(session);
		sessionModel.setUser(currentUser);
		sessionModel.setCartQuantity(cartModel.getQuantity());

		return new ResponseEntity<>(sessionModel, HttpStatus.OK);
	}

	// ====================== SUBKATEGORY

	@GetMapping("/subcategory/{sid}")
	public ResponseEntity getSubcategory(@PathVariable(value = "sid", required=true) Long sid) {

		return new ResponseEntity(subcategoryService.getSubcategoryById(sid), HttpStatus.OK);
	}

	// =======================

	// --------------------------------------
	// PRODUKTY
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
	public ResponseEntity<CategoryModel> getCatProductById(@PathVariable Long id) {
		return new ResponseEntity<>(productService.getCategoryOfProduct(id), HttpStatus.OK);
	}

	@GetMapping(value="/product/{id}/subcategory", produces = "application/json")
	public ResponseEntity<SubcategoryModel> getSubcategoryOfProduct(@PathVariable Long id) {
		return new ResponseEntity<>(productService.getSubcategoryOfProduct(id), HttpStatus.OK);
	}


	// --------------------------------------
	// !PRODUKTY
	// --------------------------------------

	// --------------------------------------
	// KOSZYK
	// --------------------------------------

	@GetMapping(value="/cart", produces = "application/json")
	public ResponseEntity getCart() {

		return new ResponseEntity(cartService.getCart(), HttpStatus.OK);
	}

	@PutMapping(value = "/cart/product/{id}/add", produces = "application/json")
	public ResponseEntity addProductToCart(@PathVariable(value = "id", required=true) Long pid) {

		cartService.addProductToCart(pid);
		return new ResponseEntity(HttpStatus.OK);
	}

	@PutMapping(value = { "/cart/product/{id}/plus" })
	public ResponseEntity cartPlus(@PathVariable(value = "id", required=true) Long id) {

		cartService.incrementAmountOfProduct(id);
		return new ResponseEntity(HttpStatus.OK);
	}

	@PutMapping(value = { "/cart/product/{id}/minus" })
	public ResponseEntity cartMinus(@PathVariable(value = "id", required=true) Long id) {

		cartService.decrementAmountOfProduct(id);
		return new ResponseEntity(HttpStatus.OK);
	}

	@DeleteMapping("/cart/clear")
	public ResponseEntity removeCart() {

		cartService.clearCart();
		return new ResponseEntity(HttpStatus.OK);
	}

	// --------------------------------------
	// !KOSZYK
	// --------------------------------------

	// --------------------------------------
	// ZAMOWIENIE
	// --------------------------------------

	@GetMapping(value = "/order", produces = MediaType.APPLICATION_JSON_VALUE)
	public String order() {
		JSONObject model = new JSONObject();

		// pobranie nazwy uzytkownika
		String username = "";
		String firstname = "";
		String lastname = "";
		String locality = "";
		String street = "";
		String zipCode = "";
		String phone = "";
		User user = null;

		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			username = userDetails.getUsername();
			user = userRepository.findByUsername(username).get();
			firstname = user.getFirstname();
			lastname = user.getLastname();
			locality = user.getLocality();
			street = user.getStreet();
			zipCode = user.getZipCode();
			phone = user.getPhone();
		} catch (ClassCastException e) {
			username = "anonymousUser";
		}
		boolean logged = !(username.equals("anonymousUser"));

		model.put("logged",logged);
		model.put("username",username);
		model.put("firstname",firstname);
		model.put("lastname",lastname);
		model.put("locality",locality);
		model.put("street",street);
		model.put("zipCode",zipCode);
		model.put("phone",phone);

		// wypis ID sesji
		System.out.println("ID sesji:" + session.getId());
		CartModel cartModel = CartModel.getCartInSession(session);

		model.put("cart", cartModel.getProductList());
		model.put("sum", cartModel.getSum());

		System.out.println("Produkty w koszyku:");
		cartModel.getProducts().forEach( (key, value)->System.out.println(key.getName() + ":" + value) );

		model.put("cartQuantity", cartModel.getQuantity());

		return model.toString();
	}

	@PostMapping(value = "/order", produces = MediaType.APPLICATION_JSON_VALUE)
	public String orderSummary(@Valid Order order) {

		JSONObject model = new JSONObject();

		// pobranie nazwy uzytkownika
		String username = "";
		String firstname = "";
		String lastname = "";
		String locality = "";
		String street = "";
		String zipCode = "";
		String phone = "";
		long user_id = -1;
		User user = null;

		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			username = userDetails.getUsername();
			user = userRepository.findByUsername(username).get();
			firstname = user.getFirstname();
			lastname = user.getLastname();
			locality = user.getLocality();
			street = user.getStreet();
			zipCode = user.getZipCode();
			phone = user.getPhone();
			user_id = user.getId();
		} catch (ClassCastException e) {
			username = "anonymousUser";
		}
		boolean logged = !(username.equals("anonymousUser"));

		model.put("logged",logged);
		model.put("username",username);

		// wypis ID sesji
		System.out.println("ID sesji:" + session.getId());
		CartModel cartModel = (CartModel) session.getAttribute("myCart");

		model.put("cart", cartModel.getProductList());
		model.put("sum", cartModel.getSum());

		if(cartModel !=null) {

			Date now = new Date();

			order.setDate(now);
			order.setCustomer(userRepository.findByUsername(username).get());

			List<OrderDetail> orderDetailList = new ArrayList<>();
			cartModel.getProducts().forEach((k, v)->{
				OrderDetail orderDetail = new OrderDetail();
				orderDetail.setProduct(k);
				orderDetail.setQuantity(v);
				orderDetailList.add(orderDetail);
			});

			order.setOrderDetails(orderDetailList);

			session.setAttribute("myOrder", order);

			model.put("hashMap", order.getHashMap());
			model.put("username",order.getCustomer().getEmail());
			model.put("firstname",order.getFirstname());
			model.put("lastname",order.getLastname());
			model.put("locality",order.getLocality());
			model.put("street",order.getStreet());
			model.put("zipCode",order.getZipCode());
			model.put("phone",order.getPhone());
			model.put("description",order.getDescription());

			String shipment = "";
			switch (order.getShipment()) {
				case 1:
					shipment="Przesyłka kurierska - 20 zł (Płatność z góry)";
					break;
				case 2:
					shipment="Paczkomaty Inpost - 10 zł (Płatność z góry)";
					break;
				case 3:
					shipment="List polecony piorytetowy - 10 zł (Płatność z góry)";
					break;
				case 4:
					shipment="Przesyłka kurierska pobraniowa - 20 zł (Płatność przy odbiorze)";
					break;
				case 5:
					shipment="Paczka pocztowa pobraniowa piorytetowa - 15 zł (Płatność przy odbiorze)";
					break;

				default:
					break;
			}

			model.put("shipment",shipment);
		}

		return model.toString();
	}

	@GetMapping(value = "/order/accept", produces = MediaType.APPLICATION_JSON_VALUE)
	public String makeOrder(HttpSession session, @RequestParam(value = "save", required=true) Boolean save) {

		System.out.println("SAVEDATA: " + save);

		ModelAndView model = new ModelAndView();
		model.setViewName("/orderAccept");

		// pobranie nazwy uzytkownika
		String username;
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			username = userDetails.getUsername();
		} catch (ClassCastException e) {
			username = "anonymousUser";
		}
		boolean logged = !(username.equals("anonymousUser"));

		model.addObject("logged",logged);
		model.addObject("username",username);

		// wypis ID sesji
		System.out.println("ID sesji:" + session.getId());
		CartModel cartModel = CartModel.getCartInSession(session);

		Order order = (Order) session.getAttribute("myOrder");

		boolean fine = true;

		if(order!=null) {
			for(OrderDetail od : order.getOrderDetails()){
				orderDetailRepository.save(od);
				Product p = od.getProduct();
				if(p.getQuantity()>=od.getQuantity()) {
					p.setQuantity(p.getQuantity() - od.getQuantity());
					productRepository.save(p);
				}
				else
				{
					fine = false;
					//return "redirect:/order/orderDenied";
					//model.setViewName("/orderDenied");
					model.addObject("fine",false);
				}
			}

			if(fine) {

				orderRepository.save(order);

				if (save) {
					User u = userRepository.findByUsername(username).get();
					u.setFirstname(order.getFirstname());
					u.setLastname(order.getLastname());
					u.setLocality(order.getLocality());
					u.setStreet(order.getStreet());
					u.setPhone(order.getPhone());
					u.setZipCode(order.getZipCode());
					userRepository.save(u);
					model.addObject("fine",true);
				}
			}
		}

		cartModel.removeCart();

		session.removeAttribute("myOrder");

		cartModel = CartModel.getCartInSession(session);

		model.addObject("cart", cartModel.getProducts());
		model.addObject("sum", cartModel.getSum());
		model.addObject("cartQuantity", cartModel.getQuantity());

		return new JSONArray().put(model.getModel()).toString();
	}

	// --------------------------------------
	// !ZAMOWIENIE
	// --------------------------------------

}
