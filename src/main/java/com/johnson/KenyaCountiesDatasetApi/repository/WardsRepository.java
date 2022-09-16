package com.johnson.KenyaCountiesDatasetApi.repository;

import com.johnson.KenyaCountiesDatasetApi.models.Wards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface WardsRepository extends JpaRepository<Wards, Long> {
    Collection<? extends Wards> findByWardNameContaining(String wardName);

    Collection<? extends Wards> findByWardCountyId(Integer countyId);

    Collection<? extends Wards> findByWardConstituencyId(Integer constituencyId);

//    Object findByWardConstituencyId(Integer constituencyId);
}
