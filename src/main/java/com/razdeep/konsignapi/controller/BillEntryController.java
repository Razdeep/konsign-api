package com.razdeep.konsignapi.controller;

import com.razdeep.konsignapi.model.Bill;
import com.razdeep.konsignapi.model.ResponseVerdict;
import com.razdeep.konsignapi.service.BillEntryService;
import io.micrometer.core.annotation.Timed;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
public class BillEntryController {

    private final static Logger LOG = LoggerFactory.getLogger(BillEntryController.class.getName());

    private final BillEntryService billEntryService;

    @Autowired
    public BillEntryController(BillEntryService billEntryService) {
        this.billEntryService = billEntryService;
    }

    @Timed
    @PostMapping(value = "/addBillEntry")
    public ResponseEntity<ResponseVerdict> addBillEntry(@RequestBody Bill bill) {
        ResponseEntity<ResponseVerdict> response;
        ResponseVerdict responseVerdict = new ResponseVerdict();

        if (bill.anyFieldEmpty()) {
            responseVerdict.setMessage("Saving bill failed because all fields are not properly filled");
            response = new ResponseEntity<>(responseVerdict, HttpStatus.NOT_ACCEPTABLE);
        } else if (billEntryService.enterBill(bill)) {
            responseVerdict.setMessage("Successfully saved bill");
            response = new ResponseEntity<>(responseVerdict, HttpStatus.OK);
        } else {
            responseVerdict.setMessage("Saving bill failed");
            response = new ResponseEntity<>(responseVerdict, HttpStatus.NOT_ACCEPTABLE);
        }
        return response;
    }

    @Timed
    @GetMapping(value = "/getBill")
    public ResponseEntity<ResponseVerdict> getBill(@RequestParam(name = "billNo") String billNo) {
        val bill = billEntryService.getBill(billNo);
        ResponseVerdict responseVerdict = new ResponseVerdict();
        if (bill == null) {
            responseVerdict.setMessage("Bill not found");
            return new ResponseEntity<>(responseVerdict, HttpStatus.NOT_FOUND);
        }
        responseVerdict.setData(bill);
        return new ResponseEntity<>(responseVerdict, HttpStatus.OK);
    }

    @Timed
    @GetMapping(value = "/getAllBills/{offset}/{pageSize}")
    public ResponseEntity<ResponseVerdict> getAllBills(@PathVariable int offset, @PathVariable int pageSize) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        val bills = billEntryService.getAllBills(offset, pageSize);
        stopWatch.stop();
        LOG.info("billEntryService.getAllBills() took " + stopWatch.getLastTaskTimeMillis() + " ms");
        ResponseVerdict responseVerdict = new ResponseVerdict();
        if (bills == null) {
            responseVerdict.setMessage("Bill not found");
            return new ResponseEntity<>(responseVerdict, HttpStatus.NOT_FOUND);
        }
        responseVerdict.setData(bills);
        return new ResponseEntity<>(responseVerdict, HttpStatus.OK);
    }

    @Timed
    @DeleteMapping(value = "/bill")
    public ResponseEntity<ResponseVerdict> deleteBill(@RequestParam(name = "billNo") String billNo) {
        ResponseVerdict responseVerdict = new ResponseVerdict();
        if (billEntryService.deleteBill(billNo)) {
            responseVerdict.setMessage("Successfully deleted bill " + billNo);
        } else {
            responseVerdict.setMessage("Bill " + billNo + " is already deleted.");
        }
        return new ResponseEntity<>(responseVerdict, HttpStatus.OK);
    }

    @GetMapping(value = "/")
    public ResponseEntity<String> welcome() {
        return new ResponseEntity<>("Welcome to konsign-api",
                HttpStatus.OK);
    }
}
