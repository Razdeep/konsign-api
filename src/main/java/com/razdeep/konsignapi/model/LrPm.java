package com.razdeep.konsignapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.razdeep.konsignapi.entity.LrPmEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LrPm {

    @JsonProperty("lr")
    private String lr;

    @JsonProperty("pm")
    private String pm;

    public LrPm(LrPmEntity lrPmEntity) {
        this.lr = lrPmEntity.getLr();
        this.pm = lrPmEntity.getPm();
    }
}
