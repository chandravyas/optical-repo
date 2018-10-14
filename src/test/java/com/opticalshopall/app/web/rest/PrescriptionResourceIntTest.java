package com.opticalshopall.app.web.rest;

import com.opticalshopall.app.OpticalshopallApp;

import com.opticalshopall.app.domain.Prescription;
import com.opticalshopall.app.repository.PrescriptionRepository;
import com.opticalshopall.app.repository.search.PrescriptionSearchRepository;
import com.opticalshopall.app.service.PrescriptionService;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;


import static com.opticalshopall.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PrescriptionResource REST controller.
 *
 * @see PrescriptionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OpticalshopallApp.class)
public class PrescriptionResourceIntTest {

    private static final String DEFAULT_PHONENUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONENUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_PATIENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PATIENT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DOCTOR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DOCTOR_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_RIGHT_VISION = "AAAAAAAAAA";
    private static final String UPDATED_RIGHT_VISION = "BBBBBBBBBB";

    private static final String DEFAULT_LEFT_VISION = "AAAAAAAAAA";
    private static final String UPDATED_LEFT_VISION = "BBBBBBBBBB";

    private static final String DEFAULT_POWER_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_POWER_DETAILS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CONTINOUS_WEAR = false;
    private static final Boolean UPDATED_CONTINOUS_WEAR = true;

    @Autowired
    private PrescriptionRepository prescriptionRepository;
    
    @Autowired
    private PrescriptionService prescriptionService;

    /**
     * This repository is mocked in the com.opticalshopall.app.repository.search test package.
     *
     * @see com.opticalshopall.app.repository.search.PrescriptionSearchRepositoryMockConfiguration
     */
    @Autowired
    private PrescriptionSearchRepository mockPrescriptionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPrescriptionMockMvc;

    private Prescription prescription;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PrescriptionResource prescriptionResource = new PrescriptionResource(prescriptionService);
        this.restPrescriptionMockMvc = MockMvcBuilders.standaloneSetup(prescriptionResource)
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
    public static Prescription createEntity(EntityManager em) {
        Prescription prescription = new Prescription()
            .phonenumber(DEFAULT_PHONENUMBER)
            .patientName(DEFAULT_PATIENT_NAME)
            .doctorName(DEFAULT_DOCTOR_NAME)
            .rightVision(DEFAULT_RIGHT_VISION)
            .leftVision(DEFAULT_LEFT_VISION)
            .powerDetails(DEFAULT_POWER_DETAILS)
            .continousWear(DEFAULT_CONTINOUS_WEAR);
        return prescription;
    }

    @Before
    public void initTest() {
        prescription = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrescription() throws Exception {
        int databaseSizeBeforeCreate = prescriptionRepository.findAll().size();

        // Create the Prescription
        restPrescriptionMockMvc.perform(post("/api/prescriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prescription)))
            .andExpect(status().isCreated());

        // Validate the Prescription in the database
        List<Prescription> prescriptionList = prescriptionRepository.findAll();
        assertThat(prescriptionList).hasSize(databaseSizeBeforeCreate + 1);
        Prescription testPrescription = prescriptionList.get(prescriptionList.size() - 1);
        assertThat(testPrescription.getPhonenumber()).isEqualTo(DEFAULT_PHONENUMBER);
        assertThat(testPrescription.getPatientName()).isEqualTo(DEFAULT_PATIENT_NAME);
        assertThat(testPrescription.getDoctorName()).isEqualTo(DEFAULT_DOCTOR_NAME);
        assertThat(testPrescription.getRightVision()).isEqualTo(DEFAULT_RIGHT_VISION);
        assertThat(testPrescription.getLeftVision()).isEqualTo(DEFAULT_LEFT_VISION);
        assertThat(testPrescription.getPowerDetails()).isEqualTo(DEFAULT_POWER_DETAILS);
        assertThat(testPrescription.isContinousWear()).isEqualTo(DEFAULT_CONTINOUS_WEAR);

        // Validate the Prescription in Elasticsearch
        verify(mockPrescriptionSearchRepository, times(1)).save(testPrescription);
    }

    @Test
    @Transactional
    public void createPrescriptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = prescriptionRepository.findAll().size();

        // Create the Prescription with an existing ID
        prescription.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrescriptionMockMvc.perform(post("/api/prescriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prescription)))
            .andExpect(status().isBadRequest());

        // Validate the Prescription in the database
        List<Prescription> prescriptionList = prescriptionRepository.findAll();
        assertThat(prescriptionList).hasSize(databaseSizeBeforeCreate);

        // Validate the Prescription in Elasticsearch
        verify(mockPrescriptionSearchRepository, times(0)).save(prescription);
    }

    @Test
    @Transactional
    public void checkPhonenumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = prescriptionRepository.findAll().size();
        // set the field null
        prescription.setPhonenumber(null);

        // Create the Prescription, which fails.

        restPrescriptionMockMvc.perform(post("/api/prescriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prescription)))
            .andExpect(status().isBadRequest());

        List<Prescription> prescriptionList = prescriptionRepository.findAll();
        assertThat(prescriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPatientNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = prescriptionRepository.findAll().size();
        // set the field null
        prescription.setPatientName(null);

        // Create the Prescription, which fails.

        restPrescriptionMockMvc.perform(post("/api/prescriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prescription)))
            .andExpect(status().isBadRequest());

        List<Prescription> prescriptionList = prescriptionRepository.findAll();
        assertThat(prescriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPrescriptions() throws Exception {
        // Initialize the database
        prescriptionRepository.saveAndFlush(prescription);

        // Get all the prescriptionList
        restPrescriptionMockMvc.perform(get("/api/prescriptions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prescription.getId().intValue())))
            .andExpect(jsonPath("$.[*].phonenumber").value(hasItem(DEFAULT_PHONENUMBER.toString())))
            .andExpect(jsonPath("$.[*].patientName").value(hasItem(DEFAULT_PATIENT_NAME.toString())))
            .andExpect(jsonPath("$.[*].doctorName").value(hasItem(DEFAULT_DOCTOR_NAME.toString())))
            .andExpect(jsonPath("$.[*].rightVision").value(hasItem(DEFAULT_RIGHT_VISION.toString())))
            .andExpect(jsonPath("$.[*].leftVision").value(hasItem(DEFAULT_LEFT_VISION.toString())))
            .andExpect(jsonPath("$.[*].powerDetails").value(hasItem(DEFAULT_POWER_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].continousWear").value(hasItem(DEFAULT_CONTINOUS_WEAR.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPrescription() throws Exception {
        // Initialize the database
        prescriptionRepository.saveAndFlush(prescription);

        // Get the prescription
        restPrescriptionMockMvc.perform(get("/api/prescriptions/{id}", prescription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(prescription.getId().intValue()))
            .andExpect(jsonPath("$.phonenumber").value(DEFAULT_PHONENUMBER.toString()))
            .andExpect(jsonPath("$.patientName").value(DEFAULT_PATIENT_NAME.toString()))
            .andExpect(jsonPath("$.doctorName").value(DEFAULT_DOCTOR_NAME.toString()))
            .andExpect(jsonPath("$.rightVision").value(DEFAULT_RIGHT_VISION.toString()))
            .andExpect(jsonPath("$.leftVision").value(DEFAULT_LEFT_VISION.toString()))
            .andExpect(jsonPath("$.powerDetails").value(DEFAULT_POWER_DETAILS.toString()))
            .andExpect(jsonPath("$.continousWear").value(DEFAULT_CONTINOUS_WEAR.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPrescription() throws Exception {
        // Get the prescription
        restPrescriptionMockMvc.perform(get("/api/prescriptions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrescription() throws Exception {
        // Initialize the database
        prescriptionService.save(prescription);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockPrescriptionSearchRepository);

        int databaseSizeBeforeUpdate = prescriptionRepository.findAll().size();

        // Update the prescription
        Prescription updatedPrescription = prescriptionRepository.findById(prescription.getId()).get();
        // Disconnect from session so that the updates on updatedPrescription are not directly saved in db
        em.detach(updatedPrescription);
        updatedPrescription
            .phonenumber(UPDATED_PHONENUMBER)
            .patientName(UPDATED_PATIENT_NAME)
            .doctorName(UPDATED_DOCTOR_NAME)
            .rightVision(UPDATED_RIGHT_VISION)
            .leftVision(UPDATED_LEFT_VISION)
            .powerDetails(UPDATED_POWER_DETAILS)
            .continousWear(UPDATED_CONTINOUS_WEAR);

        restPrescriptionMockMvc.perform(put("/api/prescriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPrescription)))
            .andExpect(status().isOk());

        // Validate the Prescription in the database
        List<Prescription> prescriptionList = prescriptionRepository.findAll();
        assertThat(prescriptionList).hasSize(databaseSizeBeforeUpdate);
        Prescription testPrescription = prescriptionList.get(prescriptionList.size() - 1);
        assertThat(testPrescription.getPhonenumber()).isEqualTo(UPDATED_PHONENUMBER);
        assertThat(testPrescription.getPatientName()).isEqualTo(UPDATED_PATIENT_NAME);
        assertThat(testPrescription.getDoctorName()).isEqualTo(UPDATED_DOCTOR_NAME);
        assertThat(testPrescription.getRightVision()).isEqualTo(UPDATED_RIGHT_VISION);
        assertThat(testPrescription.getLeftVision()).isEqualTo(UPDATED_LEFT_VISION);
        assertThat(testPrescription.getPowerDetails()).isEqualTo(UPDATED_POWER_DETAILS);
        assertThat(testPrescription.isContinousWear()).isEqualTo(UPDATED_CONTINOUS_WEAR);

        // Validate the Prescription in Elasticsearch
        verify(mockPrescriptionSearchRepository, times(1)).save(testPrescription);
    }

    @Test
    @Transactional
    public void updateNonExistingPrescription() throws Exception {
        int databaseSizeBeforeUpdate = prescriptionRepository.findAll().size();

        // Create the Prescription

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrescriptionMockMvc.perform(put("/api/prescriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prescription)))
            .andExpect(status().isBadRequest());

        // Validate the Prescription in the database
        List<Prescription> prescriptionList = prescriptionRepository.findAll();
        assertThat(prescriptionList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Prescription in Elasticsearch
        verify(mockPrescriptionSearchRepository, times(0)).save(prescription);
    }

    @Test
    @Transactional
    public void deletePrescription() throws Exception {
        // Initialize the database
        prescriptionService.save(prescription);

        int databaseSizeBeforeDelete = prescriptionRepository.findAll().size();

        // Get the prescription
        restPrescriptionMockMvc.perform(delete("/api/prescriptions/{id}", prescription.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Prescription> prescriptionList = prescriptionRepository.findAll();
        assertThat(prescriptionList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Prescription in Elasticsearch
        verify(mockPrescriptionSearchRepository, times(1)).deleteById(prescription.getId());
    }

    @Test
    @Transactional
    public void searchPrescription() throws Exception {
        // Initialize the database
        prescriptionService.save(prescription);
        when(mockPrescriptionSearchRepository.search(queryStringQuery("id:" + prescription.getId())))
            .thenReturn(Collections.singletonList(prescription));
        // Search the prescription
        restPrescriptionMockMvc.perform(get("/api/_search/prescriptions?query=id:" + prescription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prescription.getId().intValue())))
            .andExpect(jsonPath("$.[*].phonenumber").value(hasItem(DEFAULT_PHONENUMBER.toString())))
            .andExpect(jsonPath("$.[*].patientName").value(hasItem(DEFAULT_PATIENT_NAME.toString())))
            .andExpect(jsonPath("$.[*].doctorName").value(hasItem(DEFAULT_DOCTOR_NAME.toString())))
            .andExpect(jsonPath("$.[*].rightVision").value(hasItem(DEFAULT_RIGHT_VISION.toString())))
            .andExpect(jsonPath("$.[*].leftVision").value(hasItem(DEFAULT_LEFT_VISION.toString())))
            .andExpect(jsonPath("$.[*].powerDetails").value(hasItem(DEFAULT_POWER_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].continousWear").value(hasItem(DEFAULT_CONTINOUS_WEAR.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Prescription.class);
        Prescription prescription1 = new Prescription();
        prescription1.setId(1L);
        Prescription prescription2 = new Prescription();
        prescription2.setId(prescription1.getId());
        assertThat(prescription1).isEqualTo(prescription2);
        prescription2.setId(2L);
        assertThat(prescription1).isNotEqualTo(prescription2);
        prescription1.setId(null);
        assertThat(prescription1).isNotEqualTo(prescription2);
    }
}
