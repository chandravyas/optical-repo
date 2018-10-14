package com.opticalshopall.app.service;

import com.opticalshopall.app.domain.Prescription;
import com.opticalshopall.app.repository.PrescriptionRepository;
import com.opticalshopall.app.repository.search.PrescriptionSearchRepository;
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
 * Service Implementation for managing Prescription.
 */
@Service
@Transactional
public class PrescriptionService {

    private final Logger log = LoggerFactory.getLogger(PrescriptionService.class);

    private final PrescriptionRepository prescriptionRepository;

    private final PrescriptionSearchRepository prescriptionSearchRepository;

    public PrescriptionService(PrescriptionRepository prescriptionRepository, PrescriptionSearchRepository prescriptionSearchRepository) {
        this.prescriptionRepository = prescriptionRepository;
        this.prescriptionSearchRepository = prescriptionSearchRepository;
    }

    /**
     * Save a prescription.
     *
     * @param prescription the entity to save
     * @return the persisted entity
     */
    public Prescription save(Prescription prescription) {
        log.debug("Request to save Prescription : {}", prescription);
        Prescription result = prescriptionRepository.save(prescription);
        prescriptionSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the prescriptions.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Prescription> findAll() {
        log.debug("Request to get all Prescriptions");
        return prescriptionRepository.findAll();
    }


    /**
     * Get one prescription by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Prescription> findOne(Long id) {
        log.debug("Request to get Prescription : {}", id);
        return prescriptionRepository.findById(id);
    }

    /**
     * Delete the prescription by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Prescription : {}", id);
        prescriptionRepository.deleteById(id);
        prescriptionSearchRepository.deleteById(id);
    }

    /**
     * Search for the prescription corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Prescription> search(String query) {
        log.debug("Request to search Prescriptions for query {}", query);
        return StreamSupport
            .stream(prescriptionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
