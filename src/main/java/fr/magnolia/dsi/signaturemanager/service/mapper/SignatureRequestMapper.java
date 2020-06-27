package fr.magnolia.dsi.signaturemanager.service.mapper;


import fr.magnolia.dsi.signaturemanager.domain.*;
import fr.magnolia.dsi.signaturemanager.service.dto.SignatureRequestDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SignatureRequest} and its DTO {@link SignatureRequestDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SignatureRequestMapper extends EntityMapper<SignatureRequestDTO, SignatureRequest> {



    default SignatureRequest fromId(String id) {
        if (id == null) {
            return null;
        }
        SignatureRequest signatureRequest = new SignatureRequest();
        signatureRequest.setId(id);
        return signatureRequest;
    }
}
