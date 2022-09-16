package com.johnson.KenyaCountiesDatasetApi.controllers;

import com.johnson.KenyaCountiesDatasetApi.models.Constituencies;
import com.johnson.KenyaCountiesDatasetApi.repository.ConstituenciesRepository;
import com.johnson.KenyaCountiesDatasetApi.services.ConstituenciesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
public class ConstituencyController {

    @Autowired
    ConstituenciesRepository constituenciesRepository;

    @Autowired
    ConstituenciesService constituenciesService;

    @GetMapping("/")
    public String home(){
        return "Welcome to the Kenyan Counties API";
    }

    @GetMapping("/constituencies")
    public ResponseEntity<List<Constituencies>> getAllConstituencies(@RequestParam(required = false) String constituencyName){
        try {
            List<Constituencies> constituencies = new ArrayList<>();
            if (constituencyName==null)
                constituencies.addAll(constituenciesRepository.findAll());
            else
                constituencies.addAll(constituenciesRepository.findByConstituencyNameContaining(constituencyName));
            if (constituencies.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(constituencies, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/constituencies")
    @Operation(summary = "post new constituency", description = "Allows for creation of a new constituency. Once created, it returns the object response of the new constituency that was created")
    public ResponseEntity<Constituencies> createConstituency(@RequestBody Constituencies constituency){
        try {
            Constituencies postConstituency = constituenciesService.saveConstituency(constituency);
            return new ResponseEntity<>(postConstituency, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/constituencies/{id}")
    @Operation(summary = "find constituency its constituency_id", description = "Provide an id for constituency to look up. If, found it returns a constituency object")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "403", description = "FORBIDDEN. You must be authenticated to access this API",
                            content = @Content),
                    @ApiResponse(responseCode = "404", description = "NOT FOUND. The given Constituency Id Cannot be fetched",
                            content = @Content),
                    @ApiResponse(responseCode = "200", description = "SUCCESS. Constituency with given ID Fetched",
                            content = { @Content(mediaType = "application/json") })
            }
    )
    public ResponseEntity<Constituencies> getConstituencyById(@Parameter(required = true) @PathVariable("id") long id){
        Optional<Constituencies> constituencyData = constituenciesService.getConstituencyById(id);
        return constituencyData.map(constituencies -> new ResponseEntity<>(constituencies, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/constituencies/county/{countyId}")
    @Operation(summary = "find constituencies by county_id", description = "Provide an id for particular county to look up. If, found it returns a list of constituencies object on that county id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "403", description = "FORBIDDEN. You must be authenticated to access this API",
                            content = @Content),
                    @ApiResponse(responseCode = "204", description = "NO CONTENT. The given County Id Cannot be fetched",
                            content = @Content),
                    @ApiResponse(responseCode = "404", description = "NOT FOUND. No Constituencies found on given County Id",
                            content = @Content),
                    @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
                            content = @Content),
                    @ApiResponse(responseCode = "200", description = "SUCCESS. Constituencies with given County ID Fetched",
                            content = { @Content(mediaType = "application/json") })
            }
    )
    public ResponseEntity<List<Constituencies>> getConstituencyByCountyId(@Parameter(required = true) @PathVariable("countyId") Integer countyId){
        try {
            List<Constituencies> constituencies = new ArrayList<>(constituenciesRepository.findByCountyId(countyId));
            if (constituencies.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(constituencies, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/constituencies/{id}")
    @Operation(summary = "update constituency by its id", description = "Allows for updating of a constituency based on given id. Once updated, it returns the object response of the updated constituency")
    public ResponseEntity<Constituencies> updateConstituency(@Parameter(required = true) @PathVariable("id") long id, @RequestBody Constituencies constituency){
        Optional<Constituencies> constituencyData = constituenciesRepository.findById(id);
        if (constituencyData.isPresent()) {
            Constituencies getPostedConstituencyData = constituencyData.get();
            getPostedConstituencyData.setConstituencyCode(constituency.getConstituencyCode());
            getPostedConstituencyData.setConstituencyName(constituency.getConstituencyName());
            getPostedConstituencyData.setCountyId(constituency.getCountyId());
            getPostedConstituencyData.setConstituencyTotalWards(constituency.getConstituencyTotalWards());
            return new ResponseEntity<>(constituenciesRepository.save(getPostedConstituencyData), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/constituencies/{id}")
    @Operation(summary = "delete constituency by id", description = "Allows for deletion of a county based on given id. Once deleted, it returns the NO-CONTENT response")
    public ResponseEntity<HttpStatus> deleteConstituency(@Parameter(required = true) @PathVariable("id") long id){
        try {
            constituenciesRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
