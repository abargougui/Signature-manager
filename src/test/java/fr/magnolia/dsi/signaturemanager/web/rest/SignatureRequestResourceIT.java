package fr.magnolia.dsi.signaturemanager.web.rest;

import fr.magnolia.dsi.signaturemanager.SignaturemanagerApp;
import fr.magnolia.dsi.signaturemanager.domain.SignatureRequest;
import fr.magnolia.dsi.signaturemanager.repository.SignatureRequestRepository;
import fr.magnolia.dsi.signaturemanager.service.SignatureRequestService;
import fr.magnolia.dsi.signaturemanager.service.dto.SignatureRequestDTO;
import fr.magnolia.dsi.signaturemanager.service.mapper.SignatureRequestMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.magnolia.dsi.signaturemanager.domain.enumeration.Status;
/**
 * Integration tests for the {@link SignatureRequestResource} REST controller.
 */
@SpringBootTest(classes = SignaturemanagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SignatureRequestResourceIT {

    private static final String DEFAULT_CUSTOMER_ID = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CONTRACT_ID = "AAAAAAAAAA";
    private static final String UPDATED_CONTRACT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TRANSACTION_ID = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION_ID = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_REQUEST_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REQUEST_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_FILE_URL = "AAAAAAAAAA";
    private static final String UPDATED_FILE_URL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_SIGNATURE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SIGNATURE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Status DEFAULT_STATUS = Status.PREPARING_REQUEST;
    private static final Status UPDATED_STATUS = Status.SIGNATURE_REQUESTED;

    @Autowired
    private SignatureRequestRepository signatureRequestRepository;

    @Autowired
    private SignatureRequestMapper signatureRequestMapper;

    @Autowired
    private SignatureRequestService signatureRequestService;

    @Autowired
    private MockMvc restSignatureRequestMockMvc;

    private SignatureRequest signatureRequest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SignatureRequest createEntity() {
        SignatureRequest signatureRequest = new SignatureRequest()
            .customerId(DEFAULT_CUSTOMER_ID)
            .contractId(DEFAULT_CONTRACT_ID)
            .transactionId(DEFAULT_TRANSACTION_ID)
            .requestDate(DEFAULT_REQUEST_DATE)
            .fileUrl(DEFAULT_FILE_URL)
            .signatureDate(DEFAULT_SIGNATURE_DATE)
            .status(DEFAULT_STATUS);
        return signatureRequest;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SignatureRequest createUpdatedEntity() {
        SignatureRequest signatureRequest = new SignatureRequest()
            .customerId(UPDATED_CUSTOMER_ID)
            .contractId(UPDATED_CONTRACT_ID)
            .transactionId(UPDATED_TRANSACTION_ID)
            .requestDate(UPDATED_REQUEST_DATE)
            .fileUrl(UPDATED_FILE_URL)
            .signatureDate(UPDATED_SIGNATURE_DATE)
            .status(UPDATED_STATUS);
        return signatureRequest;
    }

    @BeforeEach
    public void initTest() {
        signatureRequestRepository.deleteAll();
        signatureRequest = createEntity();
    }

    @Test
    public void createSignatureRequest() throws Exception {
        int databaseSizeBeforeCreate = signatureRequestRepository.findAll().size();
        // Create the SignatureRequest
        SignatureRequestDTO signatureRequestDTO = signatureRequestMapper.toDto(signatureRequest);
        restSignatureRequestMockMvc.perform(post("/api/signature-requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(signatureRequestDTO)))
            .andExpect(status().isCreated());

        // Validate the SignatureRequest in the database
        List<SignatureRequest> signatureRequestList = signatureRequestRepository.findAll();
        assertThat(signatureRequestList).hasSize(databaseSizeBeforeCreate + 1);
        SignatureRequest testSignatureRequest = signatureRequestList.get(signatureRequestList.size() - 1);
        assertThat(testSignatureRequest.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
        assertThat(testSignatureRequest.getContractId()).isEqualTo(DEFAULT_CONTRACT_ID);
        assertThat(testSignatureRequest.getTransactionId()).isEqualTo(DEFAULT_TRANSACTION_ID);
        assertThat(testSignatureRequest.getRequestDate()).isEqualTo(DEFAULT_REQUEST_DATE);
        assertThat(testSignatureRequest.getFileUrl()).isEqualTo(DEFAULT_FILE_URL);
        assertThat(testSignatureRequest.getSignatureDate()).isEqualTo(DEFAULT_SIGNATURE_DATE);
        assertThat(testSignatureRequest.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    public void createSignatureRequestWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = signatureRequestRepository.findAll().size();

        // Create the SignatureRequest with an existing ID
        signatureRequest.setId("existing_id");
        SignatureRequestDTO signatureRequestDTO = signatureRequestMapper.toDto(signatureRequest);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSignatureRequestMockMvc.perform(post("/api/signature-requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(signatureRequestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SignatureRequest in the database
        List<SignatureRequest> signatureRequestList = signatureRequestRepository.findAll();
        assertThat(signatureRequestList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkCustomerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = signatureRequestRepository.findAll().size();
        // set the field null
        signatureRequest.setCustomerId(null);

        // Create the SignatureRequest, which fails.
        SignatureRequestDTO signatureRequestDTO = signatureRequestMapper.toDto(signatureRequest);


        restSignatureRequestMockMvc.perform(post("/api/signature-requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(signatureRequestDTO)))
            .andExpect(status().isBadRequest());

        List<SignatureRequest> signatureRequestList = signatureRequestRepository.findAll();
        assertThat(signatureRequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkContractIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = signatureRequestRepository.findAll().size();
        // set the field null
        signatureRequest.setContractId(null);

        // Create the SignatureRequest, which fails.
        SignatureRequestDTO signatureRequestDTO = signatureRequestMapper.toDto(signatureRequest);


        restSignatureRequestMockMvc.perform(post("/api/signature-requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(signatureRequestDTO)))
            .andExpect(status().isBadRequest());

        List<SignatureRequest> signatureRequestList = signatureRequestRepository.findAll();
        assertThat(signatureRequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkTransactionIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = signatureRequestRepository.findAll().size();
        // set the field null
        signatureRequest.setTransactionId(null);

        // Create the SignatureRequest, which fails.
        SignatureRequestDTO signatureRequestDTO = signatureRequestMapper.toDto(signatureRequest);


        restSignatureRequestMockMvc.perform(post("/api/signature-requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(signatureRequestDTO)))
            .andExpect(status().isBadRequest());

        List<SignatureRequest> signatureRequestList = signatureRequestRepository.findAll();
        assertThat(signatureRequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkRequestDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = signatureRequestRepository.findAll().size();
        // set the field null
        signatureRequest.setRequestDate(null);

        // Create the SignatureRequest, which fails.
        SignatureRequestDTO signatureRequestDTO = signatureRequestMapper.toDto(signatureRequest);


        restSignatureRequestMockMvc.perform(post("/api/signature-requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(signatureRequestDTO)))
            .andExpect(status().isBadRequest());

        List<SignatureRequest> signatureRequestList = signatureRequestRepository.findAll();
        assertThat(signatureRequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = signatureRequestRepository.findAll().size();
        // set the field null
        signatureRequest.setStatus(null);

        // Create the SignatureRequest, which fails.
        SignatureRequestDTO signatureRequestDTO = signatureRequestMapper.toDto(signatureRequest);


        restSignatureRequestMockMvc.perform(post("/api/signature-requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(signatureRequestDTO)))
            .andExpect(status().isBadRequest());

        List<SignatureRequest> signatureRequestList = signatureRequestRepository.findAll();
        assertThat(signatureRequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllSignatureRequests() throws Exception {
        // Initialize the database
        signatureRequestRepository.save(signatureRequest);

        // Get all the signatureRequestList
        restSignatureRequestMockMvc.perform(get("/api/signature-requests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(signatureRequest.getId())))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID)))
            .andExpect(jsonPath("$.[*].contractId").value(hasItem(DEFAULT_CONTRACT_ID)))
            .andExpect(jsonPath("$.[*].transactionId").value(hasItem(DEFAULT_TRANSACTION_ID)))
            .andExpect(jsonPath("$.[*].requestDate").value(hasItem(DEFAULT_REQUEST_DATE.toString())))
            .andExpect(jsonPath("$.[*].fileUrl").value(hasItem(DEFAULT_FILE_URL)))
            .andExpect(jsonPath("$.[*].signatureDate").value(hasItem(DEFAULT_SIGNATURE_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    public void getSignatureRequest() throws Exception {
        // Initialize the database
        signatureRequestRepository.save(signatureRequest);

        // Get the signatureRequest
        restSignatureRequestMockMvc.perform(get("/api/signature-requests/{id}", signatureRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(signatureRequest.getId()))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID))
            .andExpect(jsonPath("$.contractId").value(DEFAULT_CONTRACT_ID))
            .andExpect(jsonPath("$.transactionId").value(DEFAULT_TRANSACTION_ID))
            .andExpect(jsonPath("$.requestDate").value(DEFAULT_REQUEST_DATE.toString()))
            .andExpect(jsonPath("$.fileUrl").value(DEFAULT_FILE_URL))
            .andExpect(jsonPath("$.signatureDate").value(DEFAULT_SIGNATURE_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }
    @Test
    public void getNonExistingSignatureRequest() throws Exception {
        // Get the signatureRequest
        restSignatureRequestMockMvc.perform(get("/api/signature-requests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateSignatureRequest() throws Exception {
        // Initialize the database
        signatureRequestRepository.save(signatureRequest);

        int databaseSizeBeforeUpdate = signatureRequestRepository.findAll().size();

        // Update the signatureRequest
        SignatureRequest updatedSignatureRequest = signatureRequestRepository.findById(signatureRequest.getId()).get();
        updatedSignatureRequest
            .customerId(UPDATED_CUSTOMER_ID)
            .contractId(UPDATED_CONTRACT_ID)
            .transactionId(UPDATED_TRANSACTION_ID)
            .requestDate(UPDATED_REQUEST_DATE)
            .fileUrl(UPDATED_FILE_URL)
            .signatureDate(UPDATED_SIGNATURE_DATE)
            .status(UPDATED_STATUS);
        SignatureRequestDTO signatureRequestDTO = signatureRequestMapper.toDto(updatedSignatureRequest);

        restSignatureRequestMockMvc.perform(put("/api/signature-requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(signatureRequestDTO)))
            .andExpect(status().isOk());

        // Validate the SignatureRequest in the database
        List<SignatureRequest> signatureRequestList = signatureRequestRepository.findAll();
        assertThat(signatureRequestList).hasSize(databaseSizeBeforeUpdate);
        SignatureRequest testSignatureRequest = signatureRequestList.get(signatureRequestList.size() - 1);
        assertThat(testSignatureRequest.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testSignatureRequest.getContractId()).isEqualTo(UPDATED_CONTRACT_ID);
        assertThat(testSignatureRequest.getTransactionId()).isEqualTo(UPDATED_TRANSACTION_ID);
        assertThat(testSignatureRequest.getRequestDate()).isEqualTo(UPDATED_REQUEST_DATE);
        assertThat(testSignatureRequest.getFileUrl()).isEqualTo(UPDATED_FILE_URL);
        assertThat(testSignatureRequest.getSignatureDate()).isEqualTo(UPDATED_SIGNATURE_DATE);
        assertThat(testSignatureRequest.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    public void updateNonExistingSignatureRequest() throws Exception {
        int databaseSizeBeforeUpdate = signatureRequestRepository.findAll().size();

        // Create the SignatureRequest
        SignatureRequestDTO signatureRequestDTO = signatureRequestMapper.toDto(signatureRequest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSignatureRequestMockMvc.perform(put("/api/signature-requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(signatureRequestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SignatureRequest in the database
        List<SignatureRequest> signatureRequestList = signatureRequestRepository.findAll();
        assertThat(signatureRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteSignatureRequest() throws Exception {
        // Initialize the database
        signatureRequestRepository.save(signatureRequest);

        int databaseSizeBeforeDelete = signatureRequestRepository.findAll().size();

        // Delete the signatureRequest
        restSignatureRequestMockMvc.perform(delete("/api/signature-requests/{id}", signatureRequest.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SignatureRequest> signatureRequestList = signatureRequestRepository.findAll();
        assertThat(signatureRequestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
