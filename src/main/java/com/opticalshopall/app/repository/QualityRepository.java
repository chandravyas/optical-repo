package com.opticalshopall.app.repository;

import com.opticalshopall.app.domain.Quality;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Quality entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QualityRepository extends JpaRepository<Quality, Long> {

}
