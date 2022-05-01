package com.razdeep.konsignapi.controller;

import com.google.gson.Gson;
import com.razdeep.konsignapi.model.Bill;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class BillEntryController {
    @CrossOrigin
    @PostMapping(value = "/billentry")
    public ResponseEntity<String> billEntry(@RequestBody Bill bill) {
        Gson gson = new Gson();
        Map<String, String> body = new HashMap<>();
        body.put("message", "received");
        ResponseEntity<String> res = new ResponseEntity<>(gson.toJson(body),
                bill.anyFieldEmpty() ? HttpStatus.NOT_ACCEPTABLE : HttpStatus.OK);
        return res;
    }
}
