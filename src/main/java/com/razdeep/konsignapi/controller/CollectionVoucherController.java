package com.razdeep.konsignapi.controller;

import com.google.gson.Gson;
import com.razdeep.konsignapi.entity.BuyerEntity;
import com.razdeep.konsignapi.model.CollectionVoucher;
import com.razdeep.konsignapi.service.BuyerService;
import com.razdeep.konsignapi.service.CollectionVoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CollectionVoucherController {

    private final Gson gson;

    private final CollectionVoucherService collectionVoucherService;
    private final BuyerService buyerService;

    @Autowired
    public CollectionVoucherController(Gson gson, CollectionVoucherService collectionVoucherService, BuyerService buyerService) {
        this.gson = gson;
        this.collectionVoucherService = collectionVoucherService;
        this.buyerService = buyerService;
    }

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

    @DeleteMapping("/collection-voucher/{voucherNo}")
    ResponseEntity<String> deleteBuyer(@PathVariable String voucherNo) {
        String message;
        if (collectionVoucherService.deleteVoucher(voucherNo)) {
            message = "Successfully deleted Collection Voucher Id: " + voucherNo;
        } else {
            message = voucherNo + " is already deleted";
        }
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("message", message);
        return new ResponseEntity<>(gson.toJson(responseMap), HttpStatus.OK);
    }

    @GetMapping(value = "/get-pending-bill-numbers-to-be-collected", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Map<String, Object>> getPendingBillNumbersToBeCollected(@RequestParam(required = false) String buyerId,
                                                              @RequestParam(required = false) String buyerName) {
        List<String> pendingBillNumbers = null;
        if (buyerId != null && !buyerId.equals("")) {
            pendingBillNumbers = collectionVoucherService.getPendingBillNumbersToBeCollected(buyerId);
        } else if (buyerName != null && !buyerName.equals("")) {
            BuyerEntity retrievedBuyerEntity = buyerService.getBuyerByBuyerName(buyerName);
            if (retrievedBuyerEntity == null) {
                String message = "Buyer name not found in database";
                Map<String, Object> responseMap = new HashMap<>();
                responseMap.put("message", message);
                return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
            }
            String retriedBuyerId = retrievedBuyerEntity.getBuyerId();
            if (retriedBuyerId == null) {
                String message = "Buyer name not found in database";
                Map<String, Object> responseMap = new HashMap<>();
                responseMap.put("message", message);
                return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
            }
            pendingBillNumbers = collectionVoucherService.getPendingBillNumbersToBeCollected(retriedBuyerId);
        } else {
            String message = "Either buyerId or buyerName must be present the request param";
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("message", message);
            return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
        }
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("pendingBillNumbers", pendingBillNumbers);
        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }
}
