package com.razdeep.konsignapi.controller;

import com.google.gson.Gson;
import com.razdeep.konsignapi.model.Buyer;
import com.razdeep.konsignapi.model.Supplier;
import com.razdeep.konsignapi.model.Transport;
import com.razdeep.konsignapi.service.BuyerService;
import com.razdeep.konsignapi.service.SupplierService;
import com.razdeep.konsignapi.service.TransportService;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MasterController {

    private final Gson gson;
    private final SupplierService supplierService;
    private final BuyerService buyerService;
    private final TransportService transportService;

    @Autowired
    public MasterController(Gson gson, SupplierService supplierService, BuyerService buyerService, TransportService transportService) {
        this.gson = gson;
        this.supplierService = supplierService;
        this.buyerService = buyerService;
        this.transportService = transportService;
    }

    @Timed
    @GetMapping("/suppliers")
    ResponseEntity<String> getSuppliers() {
        Map<String, List<Supplier>> message = new HashMap<>();
        message.put("suppliers", supplierService.getSuppliers());
        return new ResponseEntity<>(gson.toJson(message), HttpStatus.OK);
    }

    @Timed
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

    @Timed
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

    @Timed
    @GetMapping("/buyers")
    ResponseEntity<String> getBuyers() {
        Map<String, List<Buyer>> message = new HashMap<>();
        message.put("buyers", buyerService.getBuyers());
        return new ResponseEntity<>(gson.toJson(message), HttpStatus.OK);
    }

    @Timed
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

    @Timed
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

    @Timed
    @PostMapping("/transport")
    ResponseEntity<String> addTransport(@RequestBody Transport transport) {
        Map<java.lang.String, java.lang.String> messageMap = new HashMap<>();
        if (transportService.addTransport(transport)) {
            messageMap.put("message", "Successfully added transport");
            return new ResponseEntity<>(gson.toJson(messageMap), HttpStatus.OK);
        } else {
            messageMap.put("message", "Failed to add transport");
            return new ResponseEntity<>(gson.toJson(messageMap), HttpStatus.BAD_REQUEST);
        }
    }

    @Timed
    @GetMapping("/transports")
    ResponseEntity<String> getTransports() {
        Map<String, List<Transport>> message = new HashMap<>();
        message.put("transports", transportService.getTransports());
        return new ResponseEntity<>(gson.toJson(message), HttpStatus.OK);
    }

    @Timed
    @DeleteMapping("/transport/{transportId}")
    ResponseEntity<String> deleteTransport(@PathVariable String transportId) {
        String message;
        if (transportService.deleteTransport(transportId)) {
            message = "Successfully deleted transport Id: " + transportId;
        } else {
            message = transportId + " is already deleted";
        }
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("message", message);
        return new ResponseEntity<>(gson.toJson(responseMap), HttpStatus.OK);
    }
}
