package com.fishbook.additional.entity.information.service;

import com.fishbook.additional.entity.information.model.AdditionalService;

import java.util.HashSet;
import java.util.Set;

public interface AdditionalServiceService {
    HashSet<AdditionalService> saveAll(Set<AdditionalService> additionalServices);
}
