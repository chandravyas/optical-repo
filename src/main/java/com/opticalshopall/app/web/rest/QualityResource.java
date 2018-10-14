package com.opticalshopall.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.opticalshopall.app.domain.Quality;
import com.opticalshopall.app.service.QualityService;
import com.opticalshopall.app.web.rest.errors.BadRequestAlertException;
import com.opticalshopall.app.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Quality.
 */
@RestController
@RequestMapping("/api")
public class QualityResource {

    private final Logger log = LoggerFactory.getLogger(QualityResource.class);

    private static final String ENTITY_NAME = "quality";

    private final QualityService qualityService;

    public QualityResource(QualityService qualityService) {
        this.qualityService = qualityService;
    }

    /**
     * POST  /qualities : Create a new quality.
     *
     * @param quality the quality to create
     * @return the ResponseEntity with status 201 (Created) and with body the new quality, or with status 400 (Bad Request) if the quality has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/qualities")
    @Timed
    public ResponseEntity<Quality> createQuality(@RequestBody Quality quality) throws URISyntaxException {
        log.debug("REST request to save Quality : {}", quality);
        if (quality.getId() != null) {
            throw new BadRequestAlertException("A new quality cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Quality result = qualityService.save(quality);
        return ResponseEntity.created(new URI("/api/qualities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /qualities : Updates an existing quality.
     *
     * @param quality the quality to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated quality,
     * or with status 400 (Bad Request) if the quality is not valid,
     * or with status 500 (Internal Server Error) if the quality couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/qualities")
    @Timed
    public ResponseEntity<Quality> updateQuality(@RequestBody Quality quality) throws URISyntaxException {
        log.debug("REST request to update Quality : {}", quality);
        if (quality.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Quality result = qualityService.save(quality);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, quality.getId().toString()))
            .body(result);
    }

    /**
     * GET  /qualities : get all the qualities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of qualities in body
     */
    @GetMapping("/qualities")
    @Timed
    public List<Quality> getAllQualities() {
        log.debug("REST request to get all Qualities");
        return qualityService.findAll();
    }

    /**
     * GET  /qualities/:id : get the "id" quality.
     *
     * @param id the id of the quality to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the quality, or with status 404 (Not Found)
     */
    @GetMapping("/qualities/{id}")
    @Timed
    public ResponseEntity<Quality> getQuality(@PathVariable Long id) {
        log.debug("REST request to get Quality : {}", id);
        Optional<Quality> quality = qualityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(quality);
    }

    /**
     * DELETE  /qualities/:id : delete the "id" quality.
     *
     * @param id the id of the quality to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/qualities/{id}")
    @Timed
    public ResponseEntity<Void> deleteQuality(@PathVariable Long id) {
        log.debug("REST request to delete Quality : {}", id);
        qualityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/qualities?query=:query : search for the quality corresponding
     * to the query.
     *
     * @param query the query of the quality search
     * @return the result of the search
     */
    @GetMapping("/_search/qualities")
    @Timed
    public List<Quality> searchQualities(@RequestParam String query) {
        log.debug("REST request to search Qualities for query {}", query);
        return qualityService.search(query);
    }

}
