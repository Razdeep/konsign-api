package com.razdeep.konsignapi.service;

import com.razdeep.konsignapi.entity.TransportEntity;
import com.razdeep.konsignapi.model.Transport;
import com.razdeep.konsignapi.repository.TransportRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransportService {


    private final TransportRepository transportRepository;

    private final CommonService commonService;

    @Autowired
    public TransportService(TransportRepository transportRepository, CommonService commonService) {
        this.transportRepository = transportRepository;
        this.commonService = commonService;
    }

    public boolean isTransportIdTaken(String transportId) {
        return transportRepository.findById(transportId).isPresent();
    }

    public boolean addTransport(Transport transport) {
        if (transport.getTransportId().isEmpty()) {
            if (transport.getTransportName().isEmpty()) {
                return false;
            }
            val baseCandidateTransportId = commonService.generateInitials(transport.getTransportName());
            String candidateTransportId = baseCandidateTransportId;
            int attempt = 2;
            while (isTransportIdTaken(candidateTransportId)) {
                candidateTransportId = baseCandidateTransportId + Integer.toString(attempt++);
            }
            transport.setTransportId(candidateTransportId);
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
