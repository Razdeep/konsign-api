package com.razdeep.konsignapi.service;

import com.razdeep.konsignapi.entity.BillEntity;
import com.razdeep.konsignapi.entity.BuyerEntity;
import com.razdeep.konsignapi.entity.LrPmEntity;
import com.razdeep.konsignapi.entity.SupplierEntity;
import com.razdeep.konsignapi.model.Bill;
import com.razdeep.konsignapi.model.LrPm;
import com.razdeep.konsignapi.repository.BillEntryRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillEntryService {

    @Autowired
    private BuyerService buyerService;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private BillEntryRepository billEntryRepository;
    public boolean enterBill(Bill bill) {

        BuyerEntity buyerEntity = buyerService.getBuyerByBuyerName(bill.getBuyerName());
        SupplierEntity supplierEntity = supplierService.getSupplierBySupplierName(bill.getSupplierName());

        if (buyerEntity == null || supplierEntity == null || bill.getLrPmList() == null) {
            return false;
        }

        BillEntity billEntity = BillEntity.builder()
                .buyerEntity(buyerEntity)
                .billNo(bill.getBillNo())
                .billAmount(bill.getBillAmount())
                .billDate(bill.getBillDate())
                .lrDate(bill.getLrDate())
                .supplierEntity(supplierEntity)
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
}
