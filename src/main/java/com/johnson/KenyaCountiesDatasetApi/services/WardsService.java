package com.johnson.KenyaCountiesDatasetApi.services;

import com.johnson.KenyaCountiesDatasetApi.models.Wards;
import com.johnson.KenyaCountiesDatasetApi.repository.WardsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WardsService {

    @Autowired
    WardsRepository wardsRepository;

    public Wards saveWard(Wards ward) {
        return wardsRepository.save(new Wards(ward.getWardName(), ward.getWardCode(), ward.getWardCountyId(), ward.getWardConstituencyId()));
    }

    public Optional<Wards> getWardById(long id) {
        return wardsRepository.findById(id);
    }
}
