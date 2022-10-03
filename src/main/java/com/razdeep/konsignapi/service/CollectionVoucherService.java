package com.razdeep.konsignapi.service;

import com.razdeep.konsignapi.entity.CollectionVoucherEntity;
import com.razdeep.konsignapi.entity.CollectionVoucherItemEntity;
import com.razdeep.konsignapi.model.CollectionVoucher;
import com.razdeep.konsignapi.repository.CollectionVoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CollectionVoucherService {

    private final CollectionVoucherRepository collectionVoucherRepository;
    private final BuyerService buyerService;

    @Autowired
    public CollectionVoucherService(CollectionVoucherRepository collectionVoucherRepository, BuyerService buyerService) {
        this.collectionVoucherRepository = collectionVoucherRepository;
        this.buyerService = buyerService;
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
                    .map(collectionVoucherItem ->
                            CollectionVoucherItemEntity.builder()
                                    .amountCollected(collectionVoucherItem.getAmountCollected())
                                    .collectionVoucher(collectionVoucherEntity)
                                    .ddNo(collectionVoucherItem.getDdNo())
                                    .ddDate(collectionVoucherItem.getDdDate())
                                    .build())
                    .collect(Collectors.toList());
        }

        collectionVoucherEntity.setCollectionVoucherItemEntityList(collectionVoucherItemEntityList);
        return collectionVoucherRepository.save(collectionVoucherEntity) != null;
    }
}
