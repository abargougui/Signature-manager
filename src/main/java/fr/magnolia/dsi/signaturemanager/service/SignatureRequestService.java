package fr.magnolia.dsi.signaturemanager.service;

import fr.magnolia.dsi.signaturemanager.domain.SignatureRequest;
import fr.magnolia.dsi.signaturemanager.repository.SignatureRequestRepository;
import fr.magnolia.dsi.signaturemanager.service.dto.SignatureRequestDTO;
import fr.magnolia.dsi.signaturemanager.service.mapper.SignatureRequestMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link SignatureRequest}.
 */
@Service
public class SignatureRequestService {

    private final Logger log = LoggerFactory.getLogger(SignatureRequestService.class);

    private final SignatureRequestRepository signatureRequestRepository;

    private final SignatureRequestMapper signatureRequestMapper;

    public SignatureRequestService(SignatureRequestRepository signatureRequestRepository, SignatureRequestMapper signatureRequestMapper) {
        this.signatureRequestRepository = signatureRequestRepository;
        this.signatureRequestMapper = signatureRequestMapper;
    }

    /**
     * Save a signatureRequest.
     *
     * @param signatureRequestDTO the entity to save.
     * @return the persisted entity.
     */
    public SignatureRequestDTO save(SignatureRequestDTO signatureRequestDTO) {
        log.debug("Request to save SignatureRequest : {}", signatureRequestDTO);
        SignatureRequest signatureRequest = signatureRequestMapper.toEntity(signatureRequestDTO);
        signatureRequest = signatureRequestRepository.save(signatureRequest);
        return signatureRequestMapper.toDto(signatureRequest);
    }

    /**
     * Get all the signatureRequests.
     *
     * @return the list of entities.
     */
    public List<SignatureRequestDTO> findAll() {
        log.debug("Request to get all SignatureRequests");
        return signatureRequestRepository.findAll().stream()
            .map(signatureRequestMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one signatureRequest by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<SignatureRequestDTO> findOne(String id) {
        log.debug("Request to get SignatureRequest : {}", id);
        return signatureRequestRepository.findById(id)
            .map(signatureRequestMapper::toDto);
    }

    /**
     * Delete the signatureRequest by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete SignatureRequest : {}", id);
        signatureRequestRepository.deleteById(id);
    }
}
