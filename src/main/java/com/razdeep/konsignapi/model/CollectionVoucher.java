package com.razdeep.konsignapi.model;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class CollectionVoucher {

    private String voucherNo;

    private String voucherDate;

    private String buyerName;

    private List<CollectionVoucherItem> collectionVoucherItemList;
}
