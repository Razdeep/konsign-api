package com.razdeep.konsignapi.controller;

import com.google.gson.Gson;
import com.razdeep.konsignapi.model.Bill;
import com.razdeep.konsignapi.service.BillEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
public class BillEntryController {

    @Autowired
    private Gson gson;

    @Autowired
    private BillEntryService billEntryService;

    @PostMapping(value = "/billentry")
    public ResponseEntity<String> billEntry(@RequestBody Bill bill) {
        Map<String, String> body = new HashMap<>();
        ResponseEntity<String> response;
        if (billEntryService.enterBill(bill)) {
            body.put("message", "Successfully saved bill");
            response = new ResponseEntity<>(gson.toJson(body), HttpStatus.OK);
        } else {
            body.put("message", "Saving bill failed");
            response = new ResponseEntity<>(gson.toJson(body), HttpStatus.NOT_ACCEPTABLE);
        }
        return response;
    }

    @GetMapping(value = "/")
    public ResponseEntity<String> welcome() {
        return new ResponseEntity<>("Welcome to konsign-api",
                HttpStatus.OK);
    }
}
