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
    private BillEntryService billEntryService;

    @PostMapping(value = "/billentry")
    public ResponseEntity<String> billEntry(@RequestBody Bill bill) {
        Gson gson = new Gson();
        Map<String, String> body = new HashMap<>();
        body.put("message", "received");
        ResponseEntity<String> res = new ResponseEntity<>(gson.toJson(body),
                billEntryService.enterBill(bill) ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE);
        return res;
    }

    @GetMapping(value = "/")
    public ResponseEntity<String> welcome() {
        ResponseEntity<String> res = new ResponseEntity<>("Welcome",
                HttpStatus.OK);
        return res;
    }
}
