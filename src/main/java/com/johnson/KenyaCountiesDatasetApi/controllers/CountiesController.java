package com.johnson.KenyaCountiesDatasetApi.controllers;

import com.johnson.KenyaCountiesDatasetApi.models.Counties;
import com.johnson.KenyaCountiesDatasetApi.repository.CountiesRepository;
import com.johnson.KenyaCountiesDatasetApi.services.CountiesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@SecurityRequirement(name = "BearerJWT")
public class CountiesController {

    @Autowired
    CountiesRepository countiesRepository;

    @Autowired
    CountiesService countiesService;

    @GetMapping("/counties")
    public ResponseEntity<List<Counties>> getAllCounties (@RequestParam(required = false) String county_name){
        try {
            List<Counties> counties = new ArrayList<>();
            if (county_name==null)
                counties.addAll(countiesRepository.findAll());
            else
                counties.addAll(countiesRepository.findByCountyNameContaining(county_name));
            if (counties.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(counties, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/counties/{id}")
    @Operation(summary = "find county its county_id", description = "Provide an id for county to look up. If, found it returns a county object")
    public ResponseEntity<Counties> getCountyById(@Parameter(required = true) @PathVariable("id") long id){
        Optional<Counties> countiesData = countiesService.getCountiesById(id);
        return countiesData.map(counties -> new ResponseEntity<>(counties, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/counties/code/{county_code}")
    @Operation(summary = "find county its county_code", description = "Provide a county code for particular county to look up. If, found it returns a county object")
    public ResponseEntity<Counties> getCountyByCode(@Parameter(required = true) @PathVariable("county_code") String county_code){
        Optional<Counties> countiesData = countiesService.getCountiesByCountyCode(county_code);
        return countiesData.map(counties -> new ResponseEntity<>(counties, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/counties")
    @Operation(summary = "post new county", description = "Allows for creation of a new county. Once created, it returns the object response of the new county that was created")
    public ResponseEntity<Counties> createCounty(@RequestBody Counties county){
        try {
            Counties postCounty = countiesService.saveCounty(county);
            return new ResponseEntity<>(postCounty, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/counties/{id}")
    @Operation(summary = "update county by id", description = "Allows for updating of a county based on given id. Once updated, it returns the object response of the updated county")
    public ResponseEntity<Counties> updateCounty(@Parameter(required = true) @PathVariable("id") long id, @RequestBody Counties county){
        Optional<Counties> countiesData = countiesRepository.findById(id);
        if (countiesData.isPresent()) {
            Counties getPostedCountiesData = countiesData.get();
            getPostedCountiesData.setCountyCode(county.getCountyCode());
            getPostedCountiesData.setCountyName(county.getCountyName());
            getPostedCountiesData.setTotalConstituencies(county.getTotalConstituencies());
            getPostedCountiesData.setTotalWards(county.getTotalWards());
            return new ResponseEntity<>(countiesRepository.save(getPostedCountiesData), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/counties/{id}")
    @Operation(summary = "delete county by id", description = "Allows for deletion of a county based on given id. Once deleted, it returns the NO-CONTENT response")
    public ResponseEntity<HttpStatus> deleteCounty(@Parameter(required = true) @PathVariable("id") long id){
        try {
            countiesRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
