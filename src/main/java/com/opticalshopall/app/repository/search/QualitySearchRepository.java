package com.opticalshopall.app.repository.search;

import com.opticalshopall.app.domain.Quality;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Quality entity.
 */
public interface QualitySearchRepository extends ElasticsearchRepository<Quality, Long> {
}
