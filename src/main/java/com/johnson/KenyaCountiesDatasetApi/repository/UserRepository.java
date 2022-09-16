package com.johnson.KenyaCountiesDatasetApi.repository;

import com.johnson.KenyaCountiesDatasetApi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByusername(String username);
    List<User> findByUsernameContaining(String username);
}
