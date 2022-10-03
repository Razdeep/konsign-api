package com.razdeep.konsignapi.service;

import com.razdeep.konsignapi.entity.*;
import com.razdeep.konsignapi.model.Bill;
import com.razdeep.konsignapi.model.LrPm;
import com.razdeep.konsignapi.repository.BillEntryRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillEntryService {


    private final BuyerService buyerService;
    private final SupplierService supplierService;
    private final TransportService transportService;
    private final BillEntryRepository billEntryRepository;

    @Autowired
    public BillEntryService(BuyerService buyerService, SupplierService supplierService, TransportService transportService, BillEntryRepository billEntryRepository) {
        this.buyerService = buyerService;
        this.supplierService = supplierService;
        this.transportService = transportService;
        this.billEntryRepository = billEntryRepository;
    }

    public boolean enterBill(Bill bill) {

        BuyerEntity buyerEntity = buyerService.getBuyerByBuyerName(bill.getBuyerName());
        SupplierEntity supplierEntity = supplierService.getSupplierBySupplierName(bill.getSupplierName());
        TransportEntity transportEntity = transportService.getTransportByTransportName(bill.getTransportName());

        if (buyerEntity == null || supplierEntity == null || transportEntity == null || bill.getLrPmList() == null) {
            return false;
        }

        BillEntity billEntity = BillEntity.builder()
                .buyerEntity(buyerEntity)
                .billNo(bill.getBillNo())
                .billAmount(bill.getBillAmount())
                .billDate(bill.getBillDate())
                .lrDate(bill.getLrDate())
                .supplierEntity(supplierEntity)
                .transportEntity(transportEntity)
                .build();

        List<LrPmEntity> lrPmEntityList = bill.getLrPmList().stream()
                .map(lrPm -> {
                    LrPmEntity lrPmEntity = new LrPmEntity(lrPm);
                    lrPmEntity.setBillEntry(billEntity);
                    return lrPmEntity;
                })
                .collect(Collectors.toList());

        billEntity.setLrPmEntityList(lrPmEntityList);

        return billEntryRepository.save(billEntity) != null;
    }

    public Bill getBill(String billNo) {
        val billEntryOptional = billEntryRepository.findById(billNo);
        if (billEntryOptional.isEmpty()) {
            return null;
        }
        val billEntry = billEntryOptional.get();

        List<LrPm> lrPmList = billEntry.getLrPmEntityList().stream()
                .map((lrPmEntity -> {
                    return new LrPm(lrPmEntity.getLr(), lrPmEntity.getPm());
                }))
                .collect(Collectors.toList());

        return Bill.builder()
                .billNo(billEntry.getBillNo())
                .billAmount(billEntry.getBillAmount())
                .billDate(billEntry.getBillDate())
                .buyerName(billEntry.getBuyerEntity().getBuyerName())
                .supplierName(billEntry.getSupplierEntity().getSupplierName())
                .transportName(billEntry.getTransportEntity().getTransportName())
                .lrPmList(lrPmList)
                .lrDate(billEntry.getLrDate())
                .build();
    }

    public boolean deleteBill(String billNo) {
        boolean wasPresent = false;
        if (billEntryRepository.findById(billNo).isPresent()) {
            wasPresent = true;
            billEntryRepository.deleteById(billNo);
        }
        return wasPresent;
    }
}
