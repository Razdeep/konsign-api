package com.razdeep.konsignapi.entity;

import com.razdeep.konsignapi.model.LrPm;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "lrpm")
@Getter
@Setter
public class LrPmEntity {
    @Id
    private String lrPmId;

    @ManyToOne
    @JoinColumn(name="fk_bill_no", nullable = false)
    private BillEntity billEntry;
    private String lr, pm;

    public LrPmEntity() {}
    public LrPmEntity(LrPm lrPm) {
        this.lr = lrPm.getLr();
        this.pm = lrPm.getPm();
    }
}
