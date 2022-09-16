package com.johnson.KenyaCountiesDatasetApi.services;

import com.johnson.KenyaCountiesDatasetApi.models.Counties;
import com.johnson.KenyaCountiesDatasetApi.repository.CountiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CountiesService {

    @Autowired
    private CountiesRepository countiesRepository;

    public Counties saveCounty(Counties county){
        return countiesRepository.save(new Counties(county.getCountyName(),county.getCountyCode(), county.getTotalConstituencies(), county.getTotalWards()));
    }

    public Optional<Counties> getCountiesById(Long id){ return countiesRepository.findById(id);}
    public Optional<Counties> getCountiesByCountyCode(String county_code){ return countiesRepository.findByCountyCodeContaining(county_code);}

}
