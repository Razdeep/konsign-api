package com.razdeep.konsignapi.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CollectionVoucherItem {

    String billNo;

    Double amountCollected;

    String bank;

    String ddNo;

    String ddDate;
}
