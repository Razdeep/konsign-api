package com.razdeep.konsignapi.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CollectionVoucher {

    private String voucherNo;

    // TODO find a way to use LocalDate here
//    @JsonFormat(pattern="yyyy-MM-dd")
    private String voucherDate;

    private String buyerName;

    private List<CollectionVoucherItem> collectionVoucherItemList;
}
