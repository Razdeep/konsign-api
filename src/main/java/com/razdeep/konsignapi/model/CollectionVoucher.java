package com.razdeep.konsignapi.model;

import lombok.Data;
import java.util.List;

@Data
public class CollectionVoucher {

    private String voucherNo;

    private String voucherDate;

    private String buyerName;

    private List<CollectionVoucherItem> collectionVoucherItemList;
}
