package com.razdeep.konsignapi.service;

import com.razdeep.konsignapi.entity.TransportEntity;
import com.razdeep.konsignapi.model.Transport;
import com.razdeep.konsignapi.repository.TransportRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
        if (!transportRepository.findAllTransportByTransportName(transport.getTransportName()).isEmpty()) {
            return false;
        }
        if (transport.getTransportId().isEmpty()) {
            if (transport.getTransportName().isEmpty()) {
                return false;
            }
            val baseCandidateTransportId = commonService.generateInitials(transport.getTransportName());
            String candidateTransportId = baseCandidateTransportId;
            int attempt = 2;
            while (isTransportIdTaken(candidateTransportId)) {
                candidateTransportId = baseCandidateTransportId + attempt++;
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
        val resultList = transportRepository.findAllTransportByTransportName(transportName);
        return resultList == null || resultList.isEmpty() ? null : resultList.get(0);
    }

    @Cacheable(value = "getTransports", key = "")
    public List<Transport> getTransports() {
        List<Transport> result = new ArrayList<>();
        transportRepository.findAll().forEach((transportEntity) -> result.add(new Transport(transportEntity.getTransportId(), transportEntity.getTransportName())));
        return result;
    }

    public boolean deleteTransport(String transportId) {
        boolean wasPresent = transportRepository.findById(transportId).isPresent();
        if (wasPresent) {
            transportRepository.deleteById(transportId);
        }
        return wasPresent;
    }
}
