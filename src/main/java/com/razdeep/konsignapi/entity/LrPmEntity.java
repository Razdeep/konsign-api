package com.razdeep.konsignapi.entity;

import com.razdeep.konsignapi.model.LrPm;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "lrpm")
@Data
public class LrPmEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int lrPmId;

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
