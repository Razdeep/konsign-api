package com.razdeep.konsignapi.service;

import com.razdeep.konsignapi.entity.TransportEntity;
import com.razdeep.konsignapi.model.Transport;
import com.razdeep.konsignapi.repository.TransportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransportService {


    private final TransportRepository transportRepository;

    @Autowired
    public TransportService(TransportRepository transportRepository) {
        this.transportRepository = transportRepository;
    }

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
