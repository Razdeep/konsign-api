package com.razdeep.konsignapi.service;

import com.razdeep.konsignapi.entity.CollectionVoucherEntity;
import com.razdeep.konsignapi.entity.CollectionVoucherItemEntity;
import com.razdeep.konsignapi.model.Bill;
import com.razdeep.konsignapi.model.CollectionVoucher;
import com.razdeep.konsignapi.model.PendingBill;
import com.razdeep.konsignapi.repository.CollectionVoucherRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CollectionVoucherService {

    private final CollectionVoucherRepository collectionVoucherRepository;
    private final BuyerService buyerService;
    private final BillEntryService billEntryService;

    @Autowired
    public CollectionVoucherService(CollectionVoucherRepository collectionVoucherRepository, BuyerService buyerService,
                                    BillEntryService billEntryService) {
        this.collectionVoucherRepository = collectionVoucherRepository;
        this.buyerService = buyerService;
        this.billEntryService = billEntryService;
    }

    public boolean addCollectionVoucher(CollectionVoucher collectionVoucher) {
        CollectionVoucherEntity collectionVoucherEntity = CollectionVoucherEntity.builder()
                .voucherNo(collectionVoucher.getVoucherNo())
                .voucherDate(collectionVoucher.getVoucherDate())
                .buyer(buyerService.getBuyerByBuyerName(collectionVoucher.getBuyerName()))
                .build();

        List<CollectionVoucherItemEntity> collectionVoucherItemEntityList = null;
        if (collectionVoucher.getCollectionVoucherItemList() != null) {
            collectionVoucherItemEntityList = collectionVoucher.getCollectionVoucherItemList().stream()
                    .map(collectionVoucherItem -> {
                            val targetBill = billEntryService.getBill(collectionVoucherItem.getBillNo());
                            val targetBilEntity = billEntryService.convertBillIntoBillEntity(targetBill);
                            return CollectionVoucherItemEntity.builder()
                                    .collectionVoucher(collectionVoucherEntity)
                                    .bill(targetBilEntity)
                                    .amountCollected(collectionVoucherItem.getAmountCollected())
                                    .bank(collectionVoucherItem.getBank())
                                    .ddNo(collectionVoucherItem.getDdNo())
                                    .ddDate(collectionVoucherItem.getDdDate())
                                    .build();
                    })
                    .collect(Collectors.toList());
        }

        collectionVoucherEntity.setCollectionVoucherItemEntityList(collectionVoucherItemEntityList);
        return collectionVoucherRepository.save(collectionVoucherEntity) != null;
    }

    public boolean deleteVoucher(String voucherNo) {
        boolean wasPresent = collectionVoucherRepository.findById(voucherNo).isPresent();
        collectionVoucherRepository.deleteById(voucherNo);
        return wasPresent;
    }

    public List<PendingBill> getPendingBillsToBeCollected(String buyerId) {
        List<Bill> billsByBuyerId = billEntryService.getBillsByBuyerId(buyerId);
        val collectedAmountSoFar =  this.getCollectedAmountInfoForBuyerId(buyerId);
        List<PendingBill> res = new ArrayList<>();
        for (val billByBuyerId: billsByBuyerId) {
            if (collectedAmountSoFar.containsKey(billByBuyerId.getBillNo())) {
                if (billByBuyerId.getBillAmount() > collectedAmountSoFar.get(billByBuyerId.getBillNo())) {
                    val pendingBillAmount = billByBuyerId.getBillAmount() - collectedAmountSoFar.get(billByBuyerId.getBillNo());
                    val pendingBill = PendingBill.builder()
                            .billNo(billByBuyerId.getBillNo())
                            .billAmount(billByBuyerId.getBillAmount())
                            .buyerName(billByBuyerId.getBuyerName())
                            .supplierName(billByBuyerId.getSupplierName())
                            .pendingAmount(pendingBillAmount)
                            .build();
                    res.add(pendingBill);
                }
            } else {
                val pendingBill = PendingBill.builder()
                        .billNo(billByBuyerId.getBillNo())
                        .billAmount(billByBuyerId.getBillAmount())
                        .buyerName(billByBuyerId.getBuyerName())
                        .supplierName(billByBuyerId.getSupplierName())
                        .pendingAmount(billByBuyerId.getBillAmount())
                        .build();
                res.add(pendingBill);
            }
        }
        return res;
    }

    private Map<String, Double> getCollectedAmountInfoForBuyerId(String buyerId) {
        val collectionVouchers = collectionVoucherRepository.getCollectedAmountInfoForBuyerId(buyerId);
        Map<String, Double> res = new HashMap<>();

        for (val collectionVoucher: collectionVouchers) {
            for (val collectionVoucherItem: collectionVoucher.getCollectionVoucherItemEntityList()) {
                if (res.containsKey(collectionVoucherItem.getBill().getBillNo())) {
                    val newValue = res.get(collectionVoucherItem.getBill().getBillNo()) + collectionVoucherItem.getAmountCollected();
                    res.put(collectionVoucherItem.getBill().getBillNo(), newValue);
                } else {
                    res.put(collectionVoucherItem.getBill().getBillNo(), collectionVoucherItem.getAmountCollected());
                }
            }
        }

        return res;
    }
}
