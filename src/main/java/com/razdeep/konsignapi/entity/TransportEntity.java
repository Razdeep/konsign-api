package com.razdeep.konsignapi.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "transport")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransportEntity {
    @Id
    private String transportId;

    @NonNull
    private String transportName;

    @OneToMany(mappedBy = "transportEntity")
    private List<BillEntity> billEntities;
}
