package com.johnson.KenyaCountiesDatasetApi.repository;

import com.johnson.KenyaCountiesDatasetApi.models.Constituencies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ConstituenciesRepository extends JpaRepository<Constituencies, Long> {
    Collection<? extends Constituencies> findByConstituencyNameContaining(String constituencyName);

    Collection<? extends Constituencies> findByCountyId(Integer countyId);
//    List<Constituencies> findByCountyId(Integer countyId);
}
