package com.opticalshopall.app.service;

import com.opticalshopall.app.domain.Supplier;
import com.opticalshopall.app.repository.SupplierRepository;
import com.opticalshopall.app.repository.search.SupplierSearchRepository;
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
 * Service Implementation for managing Supplier.
 */
@Service
@Transactional
public class SupplierService {

    private final Logger log = LoggerFactory.getLogger(SupplierService.class);

    private final SupplierRepository supplierRepository;

    private final SupplierSearchRepository supplierSearchRepository;

    public SupplierService(SupplierRepository supplierRepository, SupplierSearchRepository supplierSearchRepository) {
        this.supplierRepository = supplierRepository;
        this.supplierSearchRepository = supplierSearchRepository;
    }

    /**
     * Save a supplier.
     *
     * @param supplier the entity to save
     * @return the persisted entity
     */
    public Supplier save(Supplier supplier) {
        log.debug("Request to save Supplier : {}", supplier);
        Supplier result = supplierRepository.save(supplier);
        supplierSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the suppliers.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Supplier> findAll() {
        log.debug("Request to get all Suppliers");
        return supplierRepository.findAll();
    }


    /**
     * Get one supplier by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Supplier> findOne(Long id) {
        log.debug("Request to get Supplier : {}", id);
        return supplierRepository.findById(id);
    }

    /**
     * Delete the supplier by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Supplier : {}", id);
        supplierRepository.deleteById(id);
        supplierSearchRepository.deleteById(id);
    }

    /**
     * Search for the supplier corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Supplier> search(String query) {
        log.debug("Request to search Suppliers for query {}", query);
        return StreamSupport
            .stream(supplierSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
