package com.fishbook.additional.entity.information.service.impl;

import com.fishbook.additional.entity.information.dao.AdditionalServiceRepository;
import com.fishbook.additional.entity.information.model.AdditionalService;
import com.fishbook.additional.entity.information.service.AdditionalServiceService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AdditionalServiceServiceImpl implements AdditionalServiceService {
    private final AdditionalServiceRepository additionalServiceRepository;

    public AdditionalServiceServiceImpl(AdditionalServiceRepository additionalServiceRepository) {
        this.additionalServiceRepository = additionalServiceRepository;
    }

    @Override
    public HashSet<AdditionalService> saveAll(Set<AdditionalService> additionalServices) {
        return new HashSet<>(additionalServiceRepository.saveAll(additionalServices));
    }
}
