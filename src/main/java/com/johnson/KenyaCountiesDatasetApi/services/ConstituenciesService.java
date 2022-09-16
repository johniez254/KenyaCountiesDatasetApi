package com.johnson.KenyaCountiesDatasetApi.services;

import com.johnson.KenyaCountiesDatasetApi.models.Constituencies;
import com.johnson.KenyaCountiesDatasetApi.repository.ConstituenciesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConstituenciesService {

    @Autowired
    ConstituenciesRepository constituenciesRepository;

    public Constituencies saveConstituency(Constituencies constituency) {
        return constituenciesRepository.save(new Constituencies(constituency.getCountyId(), constituency.getConstituencyName(), constituency.getConstituencyCode(), constituency.getConstituencyTotalWards()));
    }

    public Optional<Constituencies> getConstituencyById(long id) {
        return constituenciesRepository.findById(id);
    }

}
