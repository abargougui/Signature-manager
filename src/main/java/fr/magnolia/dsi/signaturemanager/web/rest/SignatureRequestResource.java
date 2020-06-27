package fr.magnolia.dsi.signaturemanager.web.rest;

import fr.magnolia.dsi.signaturemanager.service.SignatureRequestService;
import fr.magnolia.dsi.signaturemanager.web.rest.errors.BadRequestAlertException;
import fr.magnolia.dsi.signaturemanager.service.dto.SignatureRequestDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link fr.magnolia.dsi.signaturemanager.domain.SignatureRequest}.
 */
@RestController
@RequestMapping("/api")
public class SignatureRequestResource {

    private final Logger log = LoggerFactory.getLogger(SignatureRequestResource.class);

    private static final String ENTITY_NAME = "signaturemanagerSignatureRequest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SignatureRequestService signatureRequestService;

    public SignatureRequestResource(SignatureRequestService signatureRequestService) {
        this.signatureRequestService = signatureRequestService;
    }

    /**
     * {@code POST  /signature-requests} : Create a new signatureRequest.
     *
     * @param signatureRequestDTO the signatureRequestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new signatureRequestDTO, or with status {@code 400 (Bad Request)} if the signatureRequest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/signature-requests")
    public ResponseEntity<SignatureRequestDTO> createSignatureRequest(@Valid @RequestBody SignatureRequestDTO signatureRequestDTO) throws URISyntaxException {
        log.debug("REST request to save SignatureRequest : {}", signatureRequestDTO);
        if (signatureRequestDTO.getId() != null) {
            throw new BadRequestAlertException("A new signatureRequest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SignatureRequestDTO result = signatureRequestService.save(signatureRequestDTO);
        return ResponseEntity.created(new URI("/api/signature-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /signature-requests} : Updates an existing signatureRequest.
     *
     * @param signatureRequestDTO the signatureRequestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated signatureRequestDTO,
     * or with status {@code 400 (Bad Request)} if the signatureRequestDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the signatureRequestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/signature-requests")
    public ResponseEntity<SignatureRequestDTO> updateSignatureRequest(@Valid @RequestBody SignatureRequestDTO signatureRequestDTO) throws URISyntaxException {
        log.debug("REST request to update SignatureRequest : {}", signatureRequestDTO);
        if (signatureRequestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SignatureRequestDTO result = signatureRequestService.save(signatureRequestDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, signatureRequestDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /signature-requests} : get all the signatureRequests.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of signatureRequests in body.
     */
    @GetMapping("/signature-requests")
    public List<SignatureRequestDTO> getAllSignatureRequests() {
        log.debug("REST request to get all SignatureRequests");
        return signatureRequestService.findAll();
    }

    /**
     * {@code GET  /signature-requests/:id} : get the "id" signatureRequest.
     *
     * @param id the id of the signatureRequestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the signatureRequestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/signature-requests/{id}")
    public ResponseEntity<SignatureRequestDTO> getSignatureRequest(@PathVariable String id) {
        log.debug("REST request to get SignatureRequest : {}", id);
        Optional<SignatureRequestDTO> signatureRequestDTO = signatureRequestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(signatureRequestDTO);
    }

    /**
     * {@code DELETE  /signature-requests/:id} : delete the "id" signatureRequest.
     *
     * @param id the id of the signatureRequestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/signature-requests/{id}")
    public ResponseEntity<Void> deleteSignatureRequest(@PathVariable String id) {
        log.debug("REST request to delete SignatureRequest : {}", id);
        signatureRequestService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
