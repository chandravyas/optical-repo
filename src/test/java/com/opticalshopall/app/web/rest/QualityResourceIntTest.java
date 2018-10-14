package com.opticalshopall.app.web.rest;

import com.opticalshopall.app.OpticalshopallApp;

import com.opticalshopall.app.domain.Quality;
import com.opticalshopall.app.repository.QualityRepository;
import com.opticalshopall.app.repository.search.QualitySearchRepository;
import com.opticalshopall.app.service.QualityService;
import com.opticalshopall.app.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;


import static com.opticalshopall.app.web.rest.TestUtil.sameInstant;
import static com.opticalshopall.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the QualityResource REST controller.
 *
 * @see QualityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OpticalshopallApp.class)
public class QualityResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private QualityRepository qualityRepository;
    
    @Autowired
    private QualityService qualityService;

    /**
     * This repository is mocked in the com.opticalshopall.app.repository.search test package.
     *
     * @see com.opticalshopall.app.repository.search.QualitySearchRepositoryMockConfiguration
     */
    @Autowired
    private QualitySearchRepository mockQualitySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restQualityMockMvc;

    private Quality quality;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final QualityResource qualityResource = new QualityResource(qualityService);
        this.restQualityMockMvc = MockMvcBuilders.standaloneSetup(qualityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Quality createEntity(EntityManager em) {
        Quality quality = new Quality()
            .name(DEFAULT_NAME)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedOn(DEFAULT_UPDATED_ON)
            .active(DEFAULT_ACTIVE);
        return quality;
    }

    @Before
    public void initTest() {
        quality = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuality() throws Exception {
        int databaseSizeBeforeCreate = qualityRepository.findAll().size();

        // Create the Quality
        restQualityMockMvc.perform(post("/api/qualities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quality)))
            .andExpect(status().isCreated());

        // Validate the Quality in the database
        List<Quality> qualityList = qualityRepository.findAll();
        assertThat(qualityList).hasSize(databaseSizeBeforeCreate + 1);
        Quality testQuality = qualityList.get(qualityList.size() - 1);
        assertThat(testQuality.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testQuality.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testQuality.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testQuality.isActive()).isEqualTo(DEFAULT_ACTIVE);

        // Validate the Quality in Elasticsearch
        verify(mockQualitySearchRepository, times(1)).save(testQuality);
    }

    @Test
    @Transactional
    public void createQualityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = qualityRepository.findAll().size();

        // Create the Quality with an existing ID
        quality.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQualityMockMvc.perform(post("/api/qualities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quality)))
            .andExpect(status().isBadRequest());

        // Validate the Quality in the database
        List<Quality> qualityList = qualityRepository.findAll();
        assertThat(qualityList).hasSize(databaseSizeBeforeCreate);

        // Validate the Quality in Elasticsearch
        verify(mockQualitySearchRepository, times(0)).save(quality);
    }

    @Test
    @Transactional
    public void getAllQualities() throws Exception {
        // Initialize the database
        qualityRepository.saveAndFlush(quality);

        // Get all the qualityList
        restQualityMockMvc.perform(get("/api/qualities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quality.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getQuality() throws Exception {
        // Initialize the database
        qualityRepository.saveAndFlush(quality);

        // Get the quality
        restQualityMockMvc.perform(get("/api/qualities/{id}", quality.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(quality.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingQuality() throws Exception {
        // Get the quality
        restQualityMockMvc.perform(get("/api/qualities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuality() throws Exception {
        // Initialize the database
        qualityService.save(quality);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockQualitySearchRepository);

        int databaseSizeBeforeUpdate = qualityRepository.findAll().size();

        // Update the quality
        Quality updatedQuality = qualityRepository.findById(quality.getId()).get();
        // Disconnect from session so that the updates on updatedQuality are not directly saved in db
        em.detach(updatedQuality);
        updatedQuality
            .name(UPDATED_NAME)
            .createdOn(UPDATED_CREATED_ON)
            .updatedOn(UPDATED_UPDATED_ON)
            .active(UPDATED_ACTIVE);

        restQualityMockMvc.perform(put("/api/qualities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedQuality)))
            .andExpect(status().isOk());

        // Validate the Quality in the database
        List<Quality> qualityList = qualityRepository.findAll();
        assertThat(qualityList).hasSize(databaseSizeBeforeUpdate);
        Quality testQuality = qualityList.get(qualityList.size() - 1);
        assertThat(testQuality.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testQuality.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testQuality.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testQuality.isActive()).isEqualTo(UPDATED_ACTIVE);

        // Validate the Quality in Elasticsearch
        verify(mockQualitySearchRepository, times(1)).save(testQuality);
    }

    @Test
    @Transactional
    public void updateNonExistingQuality() throws Exception {
        int databaseSizeBeforeUpdate = qualityRepository.findAll().size();

        // Create the Quality

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQualityMockMvc.perform(put("/api/qualities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quality)))
            .andExpect(status().isBadRequest());

        // Validate the Quality in the database
        List<Quality> qualityList = qualityRepository.findAll();
        assertThat(qualityList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Quality in Elasticsearch
        verify(mockQualitySearchRepository, times(0)).save(quality);
    }

    @Test
    @Transactional
    public void deleteQuality() throws Exception {
        // Initialize the database
        qualityService.save(quality);

        int databaseSizeBeforeDelete = qualityRepository.findAll().size();

        // Get the quality
        restQualityMockMvc.perform(delete("/api/qualities/{id}", quality.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Quality> qualityList = qualityRepository.findAll();
        assertThat(qualityList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Quality in Elasticsearch
        verify(mockQualitySearchRepository, times(1)).deleteById(quality.getId());
    }

    @Test
    @Transactional
    public void searchQuality() throws Exception {
        // Initialize the database
        qualityService.save(quality);
        when(mockQualitySearchRepository.search(queryStringQuery("id:" + quality.getId())))
            .thenReturn(Collections.singletonList(quality));
        // Search the quality
        restQualityMockMvc.perform(get("/api/_search/qualities?query=id:" + quality.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quality.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Quality.class);
        Quality quality1 = new Quality();
        quality1.setId(1L);
        Quality quality2 = new Quality();
        quality2.setId(quality1.getId());
        assertThat(quality1).isEqualTo(quality2);
        quality2.setId(2L);
        assertThat(quality1).isNotEqualTo(quality2);
        quality1.setId(null);
        assertThat(quality1).isNotEqualTo(quality2);
    }
}
