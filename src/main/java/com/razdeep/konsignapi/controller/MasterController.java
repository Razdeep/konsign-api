package com.razdeep.konsignapi.controller;

import com.google.gson.Gson;
import com.razdeep.konsignapi.model.Buyer;
import com.razdeep.konsignapi.model.Supplier;
import com.razdeep.konsignapi.service.BuyerService;
import com.razdeep.konsignapi.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MasterController {

    @Autowired
    private Gson gson;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private BuyerService buyerService;

    @GetMapping("/suppliers")
    ResponseEntity<String> getSuppliers() {
        Map<String, List<Supplier>> message = new HashMap<>();
        message.put("suppliers", supplierService.getSuppliers());
        return new ResponseEntity<>(gson.toJson(message), HttpStatus.OK);
    }

    @PostMapping("/addSupplier")
    ResponseEntity<String> addSupplier(@RequestBody Supplier supplier) {
        Map<String, String> messageMap = new HashMap<>();
        if (supplierService.addSupplier(supplier)) {
            messageMap.put("message", "Successfully added supplier");
            return new ResponseEntity<>(gson.toJson(messageMap), HttpStatus.OK);
        } else {
            messageMap.put("message", "Failed to add supplier");
            return new ResponseEntity<>(gson.toJson(messageMap), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/supplier/{supplierId}")
    ResponseEntity<String> deleteSupplier(@PathVariable String supplierId) {
        String message;
        if (supplierService.deleteSupplier(supplierId)) {
            message = "Successfully deleted Supplier Id: " + supplierId;
        } else {
            message = supplierId + " is already deleted";
        }
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("message", message);
        return new ResponseEntity<>(gson.toJson(responseMap), HttpStatus.OK);
    }

    @GetMapping("/buyers")
    ResponseEntity<String> getBuyers() {
        Map<String, List<Buyer>> message = new HashMap<>();
        message.put("buyers", buyerService.getBuyers());
        return new ResponseEntity<>(gson.toJson(message), HttpStatus.OK);
    }

    @PostMapping("/addBuyer")
    ResponseEntity<String> addBuyer(@RequestBody Buyer buyer) {
        Map<String, String> messageMap = new HashMap<>();
        if (buyerService.addBuyer(buyer)) {
            messageMap.put("message", "Successfully added buyer");
            return new ResponseEntity<>(gson.toJson(messageMap), HttpStatus.OK);
        } else {
            messageMap.put("message", "Failed to add supplier");
            return new ResponseEntity<>(gson.toJson(messageMap), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/buyer/{buyerId}")
    ResponseEntity<String> deleteBuyer(@PathVariable String buyerId) {
        String message;
        if (buyerService.deleteBuyer(buyerId)) {
            message = "Successfully deleted buyer Id: " + buyerId;
        } else {
            message = buyerId + " is already deleted";
        }
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("message", message);
        return new ResponseEntity<>(gson.toJson(responseMap), HttpStatus.OK);
    }
}
