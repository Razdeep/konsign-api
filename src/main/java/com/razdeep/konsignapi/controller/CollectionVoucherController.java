package com.razdeep.konsignapi.controller;

import com.google.gson.Gson;
import com.razdeep.konsignapi.model.CollectionVoucher;
import com.razdeep.konsignapi.service.CollectionVoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CollectionVoucherController {

    @Autowired
    private Gson gson;

    @Autowired
    private CollectionVoucherService collectionVoucherService;

    @PostMapping("/collection-voucher")
    public ResponseEntity<String> addCollectionVoucher(@RequestBody CollectionVoucher collectionVoucher) {
        Map<String, String> body = new HashMap<>();
        ResponseEntity<String> response;
        if (collectionVoucherService.addCollectionVoucher(collectionVoucher)) {
            body.put("message", "Successfully added collection voucher");
            response = new ResponseEntity<>(gson.toJson(body), HttpStatus.OK);
        } else {
            body.put("message", "Saving collection voucher failed");
            response = new ResponseEntity<>(gson.toJson(body), HttpStatus.NOT_ACCEPTABLE);
        }
        return response;
    }
}
