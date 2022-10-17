package com.razdeep.konsignapi.model;

import com.razdeep.konsignapi.entity.LrPmEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class LrPm {
    String lr;
    String pm;

    public LrPm(LrPmEntity lrPmEntity) {
        this.lr = lrPmEntity.getLr();
        this.pm = lrPmEntity.getPm();
    }
}
