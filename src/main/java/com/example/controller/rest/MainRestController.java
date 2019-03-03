package com.example.controller.rest;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.example.model.*;
import com.example.repository.*;
import com.example.security.services.PromotedProductService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@RestController
@RequestMapping("/rest")
@CrossOrigin(origins = "*", allowCredentials = "true", maxAge = 3600)
public class MainRestController {

	@Autowired private ProductRepository productRepository;
	@Autowired private CategoryRepository categoryRepository;
	@Autowired private SubcategoryRepository subcategoryRepository;
	@Autowired private PromotedProductRepository promotedProductRepository;
	@Autowired private PromotedProductService promotedProductService;
	@Autowired private OpportunityRepository opportunityRepository;
	@Autowired private OrderRepository orderRepository;
	@Autowired private OrderDetailRepository orderDetailRepository;
	@Autowired private UserRepository userRepository;
	@Autowired private HttpSession session;

	@GetMapping(value="/getsessionid")
	public Map<String,Object> getSessionId(){

		Map<String,Object> map = new HashMap<>();
		map.put("JSESSIONID",session.getId());

		return map;
	}

	@GetMapping(value="/categories")
	public List<Category> categories(){
		return categoryRepository.findAll();
	}

	@GetMapping(value="/subcategories")
	public List<Subcategory> subcategories(){
		return (List<Subcategory>) subcategoryRepository.findAll();
	}

	@GetMapping(value="/opportunity")
	public Opportunity opportunity(){
		if(opportunityRepository.findAll().iterator().hasNext())
			return opportunityRepository.findAll().iterator().next();
		return null;
	}

	@GetMapping(value="/promotedproducts")
	public List<PromotedProduct> promotedproducts(){
		return (List<PromotedProduct>) promotedProductRepository.findAll();
	}

	@GetMapping(value="/logged")
	public String logged(HttpSession session){
		JSONObject json = new JSONObject();

		String username;
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			username = userDetails.getUsername();
		} catch (ClassCastException e) {
			username = "anonymousUser";
		}
		boolean logged = !(username.equals("anonymousUser"));

		json.put("logged",logged);
		json.put("username",username);

		Cart cart = Cart.getCartInSession(session);
		json.put("cartQuantity",cart.getQuantity());

		return json.toString();
	}

	@GetMapping(value="/home", produces = "application/json")
	public String home(){

		JSONObject json = new JSONObject();

		json.put("categories",categoryRepository.findAll());

		if(opportunityRepository.count()>0) {
			JSONObject opportunity = new JSONObject(((ArrayList<Opportunity>) opportunityRepository.findAll()).get(0));
			opportunity.put("opportunityExist",true);
			json.put("opportunity",opportunity);

		}
		else
		{
			JSONObject opportunity = new JSONObject();
			opportunity.put("opportunityExist",false);
			json.put("opportunity",opportunity);
		}

		json.put("promotedproducts", promotedProductRepository.findAll());

		return json.toString();
	}

	// --------------------------------------
	// PRODUKTY
	// --------------------------------------

//    @GetMapping(value="/product/{prodId}", produces = "application/json")
//    public Product getProductById(@PathVariable Long prodId) {
//        return productRepository.findById(prodId).get();
//    }

	@GetMapping(value="/products", produces = "application/json")
	public List<Product> getProductsBySubcategoryId(@RequestParam(value = "subCatId", required = true) Long subCatId) {

		return productRepository.findAllBySubcategory(subcategoryRepository.findById(subCatId).get());
	}

	@GetMapping(value="/products/product", produces = "application/json")
	public Product getProductById(@RequestParam(value = "code", required = true) Long prodId) {
		return productRepository.findById(prodId).get();
	}

	// dodawanie produktu do koszyka
	@GetMapping(value = "/products/product/add", produces = "application/json")
	public String addProductToCart(@RequestParam(value = "code", required=true) long product_code) {

		Cart cart = Cart.getCartInSession(session);

		// dodawanie produktu do koszyka
		if (product_code!=0) {
			Product product = productRepository.findById(product_code);
			cart.addProduct(product);
			System.out.println("Dodano produkt o kodzie " + product_code);
			System.out.println("Produkty w koszyku: ");
			System.out.println("SESSIONID: "+session.getId());

			cart.getProducts().forEach( (key,value)->System.out.println(key.getName() + ":" + value) );
		}

		JSONObject json = new JSONObject();
		json.put("success",true);

		return json.toString();
	}

	// --------------------------------------
	// !PRODUKTY
	// --------------------------------------

	// --------------------------------------
	// KOSZYK
	// --------------------------------------

	@GetMapping(value="/cart", produces = "application/json")
	public String getCart() {

		Cart cart = Cart.getCartInSession(session);

		JSONObject model = new JSONObject();
		JSONObject koszyk = new JSONObject();
		koszyk.put("products",cart.getProductList());
		koszyk.put("quantities",cart.getQuantity());
		model.put("cart",cart.getProductList());
		model.put("sum",cart.getSum());
		return new JSONArray().put(model).toString();
	}

	@RequestMapping(value = { "/cart/plus" }, method = RequestMethod.GET)
	public String cartPlus(@RequestParam(value = "code", required=true) long product_code) {

		Cart cart = Cart.getCartInSession(session);

		// dodawanie produktu do koszyka
		if (product_code !=0) {
			Product product = productRepository.findById(product_code);
			cart.addProduct(product);
			System.out.println("Dodano produkt o kodzie " + product_code);
			System.out.println("Produkty w koszyku: ");

			// wypis
			cart.getProducts().forEach( (key,value)->System.out.println(key.getName() + ":" + value) );
		}

		JSONObject json = new JSONObject();
		json.put("success",true);

		return json.toString();
	}

	@RequestMapping(value = { "/cart/minus" }, method = RequestMethod.GET)
	public String cartMinus(HttpSession session, @RequestParam(value = "code", required=true) long product_code) {

		Cart cart = Cart.getCartInSession(session);

		// dodawanie produktu do koszyka
		if (product_code!=0) {
			Product product = productRepository.findById(product_code);
			cart.removeProduct(product);
			System.out.println("Dodano produkt o kodzie " + product_code);
			System.out.println("Produkty w koszyku: ");

			cart.getProducts().forEach( (key,value)->System.out.println(key.getName() + ":" + value) );
		}

		JSONObject json = new JSONObject();
		json.put("success",true);

		return json.toString();
	}

	@GetMapping("/cart/removeAll")
	public String removeCart() {

		Cart cart = Cart.getCartInSession(session);
		cart.removeCart();

		JSONObject json = new JSONObject();
		json.put("success",true);

		return json.toString();
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
		Cart cart = Cart.getCartInSession(session);

		model.put("cart",cart.getProductList());
		model.put("sum",cart.getSum());

		System.out.println("Produkty w koszyku:");
		cart.getProducts().forEach( (key,value)->System.out.println(key.getName() + ":" + value) );

		model.put("cartQuantity",cart.getQuantity());

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
		Cart cart = (Cart) session.getAttribute("myCart");

		model.put("cart",cart.getProductList());
		model.put("sum",cart.getSum());

		if(cart!=null) {

			Date now = new Date();

			order.setDate(now);
			order.setCustomer(userRepository.findByUsername(username).get());

			List<OrderDetail> orderDetailList = new ArrayList<>();
			cart.getProducts().forEach((k,v)->{
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
		Cart cart = Cart.getCartInSession(session);

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

		cart.removeCart();

		session.removeAttribute("myOrder");

		cart = Cart.getCartInSession(session);

		model.addObject("cart",cart.getProducts());
		model.addObject("sum",cart.getSum());
		model.addObject("cartQuantity",cart.getQuantity());

		return new JSONArray().put(model.getModel()).toString();
	}

	// --------------------------------------
	// !ZAMOWIENIE
	// --------------------------------------

}
