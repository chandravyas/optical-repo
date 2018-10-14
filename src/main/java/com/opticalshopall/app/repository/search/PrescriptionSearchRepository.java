package com.opticalshopall.app.repository.search;

import com.opticalshopall.app.domain.Prescription;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Prescription entity.
 */
public interface PrescriptionSearchRepository extends ElasticsearchRepository<Prescription, Long> {
}
