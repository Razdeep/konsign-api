package com.razdeep.konsignapi.controller;

import com.google.gson.Gson;
import com.razdeep.konsignapi.model.Supplier;
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

    @GetMapping("/suppliers")
    ResponseEntity<String> getSuppliers() {
        Map<String, List<Supplier>> message = new HashMap<>();
        message.put("suppliers", supplierService.getSuppliers());
        return new ResponseEntity<>(gson.toJson(message), HttpStatus.OK);
    }

    @PostMapping("/addSupplier")
    ResponseEntity<String> addSupplier(@RequestBody Supplier supplier) {
        Map<String, Boolean> message = new HashMap<>();
        supplierService.addSupplier(supplier);
        message.put("success", true);
        return new ResponseEntity<>(gson.toJson(message), HttpStatus.OK);
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

}
