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
    private String lr, pm;

    public LrPmEntity() {}
    public LrPmEntity(LrPm lrPm) {
        this.lr = lrPm.getLr();
        this.pm = lrPm.getPm();
    }
}
