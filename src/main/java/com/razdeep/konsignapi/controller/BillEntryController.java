package com.razdeep.konsignapi.controller;

import com.google.gson.Gson;
import com.razdeep.konsignapi.model.Bill;
import com.razdeep.konsignapi.model.BillRequest;
import com.razdeep.konsignapi.service.BillEntryService;
import lombok.val;
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

    @GetMapping(value = "/getBill")
    public ResponseEntity<String> getBill(@RequestBody BillRequest billRequest) {
        val bill = billEntryService.getBill(billRequest.getBillNo());
        Map<String, String> body = new HashMap<>();
        if (bill == null) {
            body.put("message", "Bill not found");
            return new ResponseEntity<>(gson.toJson(body), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(gson.toJson(bill), HttpStatus.OK);
    }

    @GetMapping(value = "/")
    public ResponseEntity<String> welcome() {
        return new ResponseEntity<>("Welcome to konsign-api",
                HttpStatus.OK);
    }
}
