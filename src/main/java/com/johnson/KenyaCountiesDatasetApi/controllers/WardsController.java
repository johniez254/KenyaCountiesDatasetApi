package com.johnson.KenyaCountiesDatasetApi.controllers;

import com.johnson.KenyaCountiesDatasetApi.models.Wards;
import com.johnson.KenyaCountiesDatasetApi.repository.WardsRepository;
import com.johnson.KenyaCountiesDatasetApi.services.WardsService;
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
public class WardsController {

    @Autowired
    WardsRepository wardsRepository;

    @Autowired
    WardsService wardsService;

    @GetMapping("/wards")
    public ResponseEntity<List<Wards>> getAllWards(@RequestParam(required = false) String wardName){
        try {
            List<Wards> wards = new ArrayList<>();
            if (wardName==null)
                wards.addAll(wardsRepository.findAll());
            else
                wards.addAll(wardsRepository.findByWardNameContaining(wardName));
            if (wards.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(wards, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/wards")
    @Operation(summary = "post new ward", description = "Allows for creation of a new ward. Once created, it returns the object response of the new ward that was created")
    public  ResponseEntity<Wards> creatWard(@RequestBody Wards ward){
        try {
            Wards postWard = wardsService.saveWard(ward);
            return new ResponseEntity<>(postWard, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/wards/{id}")
    @Operation(summary = "find ward its ward_id", description = "Provide an id for ward to look up. If, found it returns a ward object")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "403", description = "FORBIDDEN. You must be authenticated to access this API",
                            content = @Content),
                    @ApiResponse(responseCode = "404", description = "NOT FOUND. The given Ward Id Cannot be fetched",
                            content = @Content),
                    @ApiResponse(responseCode = "200", description = "SUCCESS. Ward with given ID Fetched",
                            content = { @Content(mediaType = "application/json") })
            }
    )
    public ResponseEntity<Wards> getWardById(@Parameter(required = true) @PathVariable("id") long id){
        Optional<Wards> wardData = wardsService.getWardById(id);
        return wardData.map(wards -> new ResponseEntity<>(wards, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/wards/county/{countyId}")
    @Operation(summary = "find wards by county_id", description = "Provide an id for particular county to look up. If, found it returns a list of wards object on that county id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "403", description = "FORBIDDEN. You must be authenticated to access this API",
                            content = @Content),
                    @ApiResponse(responseCode = "204", description = "NO CONTENT. The given County Id Cannot be fetched",
                            content = @Content),
                    @ApiResponse(responseCode = "404", description = "NOT FOUND. No Wards found on given County Id",
                            content = @Content),
                    @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
                            content = @Content),
                    @ApiResponse(responseCode = "200", description = "SUCCESS. Wards with given County ID Fetched",
                            content = { @Content(mediaType = "application/json") })
            }
    )
    public ResponseEntity<List<Wards>> getWardsByCountyId(@Parameter(required = true) @PathVariable("countyId") Integer countyId){
        try {
            List<Wards> wards = new ArrayList<>(wardsRepository.findByWardCountyId(countyId));
            if (wards.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(wards, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/wards/constituency/{constituencyId}")
    @Operation(summary = "find wards by constituency_id", description = "Provide an id for particular constituency to look up. If, found it returns a list of wards object on that constituency id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "403", description = "FORBIDDEN. You must be authenticated to access this API",
                            content = @Content),
                    @ApiResponse(responseCode = "204", description = "NO CONTENT. The given County Id Cannot be fetched",
                            content = @Content),
                    @ApiResponse(responseCode = "404", description = "NOT FOUND. No Wards found on given constituency Id",
                            content = @Content),
                    @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
                            content = @Content),
                    @ApiResponse(responseCode = "200", description = "SUCCESS. Wards with given constituency ID Fetched",
                            content = { @Content(mediaType = "application/json") })
            }
    )
    public ResponseEntity<List<Wards>> getWardsByConstituencyId(@Parameter(required = true) @PathVariable("constituencyId") Integer constituencyId){
        try {
            List<Wards> wards = new ArrayList<>(wardsRepository.findByWardConstituencyId(constituencyId));
            if (wards.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(wards, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/wards/{id}")
    @Operation(summary = "update ward by its Id", description = "Allows for updating of a ward based on given id. Once updated, it returns the object response of the updated ward")
    public ResponseEntity<Wards> updateWard(@Parameter(required = true) @PathVariable("id") long id, @RequestBody Wards ward){
        Optional<Wards> wardsData = wardsRepository.findById(id);
        if (wardsData.isPresent()){
            Wards getPostedWardData = wardsData.get();
            getPostedWardData.setWardCode(ward.getWardCode());
            getPostedWardData.setWardName(ward.getWardName());
            getPostedWardData.setWardCountyId(ward.getWardCountyId());
            getPostedWardData.setWardConstituencyId(ward.getWardConstituencyId());

            return new ResponseEntity<>(wardsRepository.save(getPostedWardData), HttpStatus.OK);
        }else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/wards/{id}")
    @Operation(summary = "delete ward by id", description = "Allows for deletion of a ward based on given id. Once deleted, it returns the NO-CONTENT response")
    public ResponseEntity<HttpStatus> deleteWard(@Parameter(required = true) @PathVariable("id") long id){
        try {
            wardsRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
