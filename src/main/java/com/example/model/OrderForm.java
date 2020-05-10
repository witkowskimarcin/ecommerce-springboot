package com.example.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class OrderForm {

    private Map<Long, Long> productIdsAndQuantities;
}
