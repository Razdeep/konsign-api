package com.razdeep.konsignapi.model;

import lombok.Data;

@Data
public class CollectionVoucherItem {

    String billNo;

    Double amountCollected;

    String bank;

    String ddNo;

    String ddDate;
}
