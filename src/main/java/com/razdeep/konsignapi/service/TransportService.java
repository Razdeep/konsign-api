package com.razdeep.konsignapi.service;

import com.razdeep.konsignapi.entity.TransportEntity;
import com.razdeep.konsignapi.model.Buyer;
import com.razdeep.konsignapi.model.Supplier;
import com.razdeep.konsignapi.model.Transport;
import com.razdeep.konsignapi.repository.TransportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransportService {

    @Autowired
    private TransportRepository transportRepository;

    public boolean addTransport(Transport transport) {
        if (transport.getTransportId().isEmpty() || transport.getTransportName().isEmpty()) {
            return false;
        }

        TransportEntity transportEntity = TransportEntity.builder()
                .transportId(transport.getTransportId())
                .transportName(transport.getTransportName())
                .build();

        transportRepository.save(transportEntity);
        return true;
    }

    public TransportEntity getTransportByTransportName(String transportName) {
        return transportRepository.findTransportByTransportName(transportName);
    }

    public List<Transport> getTransports() {
        List<Transport> result = new ArrayList<>();
        transportRepository.findAll().forEach((transportEntity) -> {
            result.add(new Transport(transportEntity.getTransportId(), transportEntity.getTransportName()));
        });
        return result;
    }
}
