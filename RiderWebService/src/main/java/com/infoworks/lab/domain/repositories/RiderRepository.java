package com.infoworks.lab.domain.repositories;

import com.infoworks.lab.domain.entities.Rider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RiderRepository extends JpaRepository<Rider, Integer> {
    List<Rider> findByName(String name);
    long countByName(String name);

    @Override
    long count();

    @Override
    Page<Rider> findAll(Pageable pageable);
}
