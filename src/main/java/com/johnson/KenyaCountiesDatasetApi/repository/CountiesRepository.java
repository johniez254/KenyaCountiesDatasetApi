package com.johnson.KenyaCountiesDatasetApi.repository;

import com.johnson.KenyaCountiesDatasetApi.models.Counties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CountiesRepository extends JpaRepository<Counties, Long> {

    List<Counties> findByCountyNameContaining(String county_name);

    Optional<Counties> findByCountyCodeContaining(String county_code);
}
