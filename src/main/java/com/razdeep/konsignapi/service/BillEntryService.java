package com.razdeep.konsignapi.service;

import com.razdeep.konsignapi.entity.BillEntity;
import com.razdeep.konsignapi.model.Bill;
import com.razdeep.konsignapi.repository.BillEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillEntryService {

    @Autowired
    private BillEntryRepository billEntryRepository;
    public boolean enterBill(Bill bill) {
        return billEntryRepository.save(new BillEntity(bill)) != null;
    }
}
