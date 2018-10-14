package com.opticalshopall.app.service;

import com.opticalshopall.app.domain.Quality;
import com.opticalshopall.app.repository.QualityRepository;
import com.opticalshopall.app.repository.search.QualitySearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Quality.
 */
@Service
@Transactional
public class QualityService {

    private final Logger log = LoggerFactory.getLogger(QualityService.class);

    private final QualityRepository qualityRepository;

    private final QualitySearchRepository qualitySearchRepository;

    public QualityService(QualityRepository qualityRepository, QualitySearchRepository qualitySearchRepository) {
        this.qualityRepository = qualityRepository;
        this.qualitySearchRepository = qualitySearchRepository;
    }

    /**
     * Save a quality.
     *
     * @param quality the entity to save
     * @return the persisted entity
     */
    public Quality save(Quality quality) {
        log.debug("Request to save Quality : {}", quality);
        Quality result = qualityRepository.save(quality);
        qualitySearchRepository.save(result);
        return result;
    }

    /**
     * Get all the qualities.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Quality> findAll() {
        log.debug("Request to get all Qualities");
        return qualityRepository.findAll();
    }


    /**
     * Get one quality by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Quality> findOne(Long id) {
        log.debug("Request to get Quality : {}", id);
        return qualityRepository.findById(id);
    }

    /**
     * Delete the quality by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Quality : {}", id);
        qualityRepository.deleteById(id);
        qualitySearchRepository.deleteById(id);
    }

    /**
     * Search for the quality corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Quality> search(String query) {
        log.debug("Request to search Qualities for query {}", query);
        return StreamSupport
            .stream(qualitySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
