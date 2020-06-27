package fr.magnolia.dsi.signaturemanager.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.magnolia.dsi.signaturemanager.web.rest.TestUtil;

public class SignatureRequestDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SignatureRequestDTO.class);
        SignatureRequestDTO signatureRequestDTO1 = new SignatureRequestDTO();
        signatureRequestDTO1.setId("id1");
        SignatureRequestDTO signatureRequestDTO2 = new SignatureRequestDTO();
        assertThat(signatureRequestDTO1).isNotEqualTo(signatureRequestDTO2);
        signatureRequestDTO2.setId(signatureRequestDTO1.getId());
        assertThat(signatureRequestDTO1).isEqualTo(signatureRequestDTO2);
        signatureRequestDTO2.setId("id2");
        assertThat(signatureRequestDTO1).isNotEqualTo(signatureRequestDTO2);
        signatureRequestDTO1.setId(null);
        assertThat(signatureRequestDTO1).isNotEqualTo(signatureRequestDTO2);
    }
}
