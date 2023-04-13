package com.razdeep.konsignapi.mapper;

import com.razdeep.konsignapi.entity.BillEntity;
import com.razdeep.konsignapi.model.Bill;
import org.mapstruct.Mapper;

@Mapper
public interface BillMapper {
    Bill billEntityToBill(BillEntity billEntity);
}
