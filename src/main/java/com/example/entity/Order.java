package com.example.entity;

import com.example.model.CartModel;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "orders")
//uniqueConstraints = { @UniqueConstraint(columnNames = "Order_Num") })
public class Order{
 
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
	
	@Temporal(TemporalType.TIMESTAMP)
    private Date date;
    
    @OneToOne
    private User customer;
    
    private String firstname;
    
    private String lastname;
    
    private String locality;
    
    private String street;
    
    @Column(name = "zip_code")
    private String zipCode;
    
    private String phone;
    
    private int shipment;
    
    private String description;
    
    @OneToMany
    //@JoinTable(name = "o_d", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "orderdetail_id"))
    private List<OrderDetail> orderDetails;
    
//    @OneToMany(cascade=CascadeType.ALL)
//	@JoinColumn(name="quantity")
//    @MapKeyColumn(name = "id")
//	private Map<Product, Integer> products;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getCustomer() {
		return customer;
	}

	public void setCustomer(User customer) {
		this.customer = customer;
	}

	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}
	
	public void setProducts(CartModel cartModel) {
		
		cartModel.getProducts().forEach( (key, value)->{
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setProduct(key);
			orderDetail.setQuantity(value);
			orderDetails.add(orderDetail);
		});
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getShipment() {
		return shipment;
	}

	public void setShipment(int shipment) {
		this.shipment = shipment;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public HashMap<Product, Integer> getHashMap() {
		HashMap<Product, Integer> hashMap = new HashMap<>();
		
		for(int i=0; i<orderDetails.size(); ++i) {
			hashMap.put(orderDetails.get(0).getProduct(), orderDetails.get(0).getQuantity());
		}
		return hashMap;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	
	
	

//	public Map<Product, Integer> getProducts() {
//		return products;
//	}
//
//	public void setProducts(Map<Product, Integer> products) {
//		this.products = products;
//	}
}
