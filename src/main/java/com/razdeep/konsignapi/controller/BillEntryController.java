package com.razdeep.konsignapi.controller;

import com.razdeep.konsignapi.model.Bill;
import com.razdeep.konsignapi.service.BillEntryService;
import io.micrometer.core.annotation.Timed;
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

    private final BillEntryService billEntryService;

    @Autowired
    public BillEntryController(BillEntryService billEntryService) {
        this.billEntryService = billEntryService;
    }

    @Timed
    @PostMapping(value = "/billentry")
    public ResponseEntity<Map<String, String>> billEntry(@RequestBody Bill bill) {
        Map<String, String> body = new HashMap<>();
        ResponseEntity<Map<String, String>> response;
        if (bill.anyFieldEmpty()) {
            body.put("message", "Saving bill failed because all fields are not properly filled");
            response = new ResponseEntity<>(body, HttpStatus.NOT_ACCEPTABLE);
        } else if (billEntryService.enterBill(bill)) {
            body.put("message", "Successfully saved bill");
            response = new ResponseEntity<>(body, HttpStatus.OK);
        } else {
            body.put("message", "Saving bill failed");
            response = new ResponseEntity<>(body, HttpStatus.NOT_ACCEPTABLE);
        }
        return response;
    }

    @Timed
    @GetMapping(value = "/getBill")
    public ResponseEntity<Object> getBill(@RequestParam(name = "billNo") String billNo) {
        val bill = billEntryService.getBill(billNo);
        Map<String, String> body = new HashMap<>();
        if (bill == null) {
            body.put("message", "Bill not found");
            return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bill, HttpStatus.OK);
    }

    @Timed
    @GetMapping(value = "/getAllBills/{offset}/{pageSize}")
    public ResponseEntity<Object> getAllBills(@PathVariable int offset, @PathVariable int pageSize) {
        val bills = billEntryService.getAllBills(offset, pageSize);
        if (bills == null) {
            Map<String, String> body = new HashMap<>();
            body.put("message", "Bill not found");
            return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bills, HttpStatus.OK);
    }

    @Timed
    @DeleteMapping(value = "/bill")
    public ResponseEntity<Map<String, String>> deleteBill(@RequestParam(name = "billNo") String billNo) {
        Map<String, String> body = new HashMap<>();
        if (billEntryService.deleteBill(billNo)) {
            body.put("message", "Successfully deleted bill " + billNo);
        } else {
            body.put("message", "Bill " + billNo + " is already deleted.");
        }
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @GetMapping(value = "/")
    public ResponseEntity<String> welcome() {
        return new ResponseEntity<>("Welcome to konsign-api",
                HttpStatus.OK);
    }
}
