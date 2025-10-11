package com.example.AmazonClone.controllers;

import com.example.AmazonClone.models.Customer;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;



@RestController
@RequestMapping("/customers")
public class CustomersController {

    private List<Customer> customersList = new ArrayList<>();
    public void AllCustomers(){

        customersList.add(new Customer("surenishant", "sure.nishant@gmail.com", "Nishant", 1L));
        customersList.add(new Customer("surenishant1", "sure.nishant1@gmail.com", "Nishant1", 2L));
        customersList.add(new Customer("surenishant2", "sure.nishant2@gmail.com", "Nishant2", 3L));
        customersList.add(new Customer("surenishant3", "sure.nishant3@gmail.com", "Nishant3", 4L));
    }


    @GetMapping("/all")
    public List<Customer> getAllCustomers(HttpServletRequest request){
        AllCustomers();
        System.out.println(request.getSession().getId());
        return customersList;
    }

    @PostMapping("/add")
    public String addCustomer(@RequestBody Customer customer) {
        System.out.println(customer);
        customersList.add(customer);
        return "Customer added successfully";

    }






}
