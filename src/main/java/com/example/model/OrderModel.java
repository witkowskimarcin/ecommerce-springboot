package com.example.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class OrderModel{

    @NotNull
    private long id;

    @NotNull
    private Date date;

    @NotNull
    private UserModel customer;

    @NotNull
    @Size(min=3, max = 20)
    private String firstname;

    @NotNull
    @Size(min=3, max = 20)
    private String lastname;

    @NotNull
    @Size(min=3, max = 20)
    private String locality;

    @NotNull
    @Size(min=3, max = 50)
    private String street;

    @NotNull
    @Size(min=3, max = 20)
    private String zipCode;

    @NotNull
    @Size(min=3, max = 20)
    private String phone;

    @NotNull
    private int shipment;

    @Size(max = 500)
    private String description;

    @NotNull
    private List<OrderDetailModel> orderDetails;
	
//	public void setProducts(Cart cart) {
//
//		cart.getProducts().forEach( (key,value)->{
//			OrderDetail orderDetail = new OrderDetail();
//			orderDetail.setProduct(key);
//			orderDetail.setQuantity(value);
//			orderDetails.add(orderDetail);
//		});
//	}
//
//	public HashMap<ProductModel, Integer> getHashMap() {
//		HashMap<ProductModel, Integer> hashMap = new HashMap<>();
//
//		for(int i=0; i<orderDetails.size(); ++i) {
//			hashMap.put(orderDetails.get(0).getProduct(), orderDetails.get(0).getQuantity());
//		}
//		return hashMap;
//	}
}
