package fr.magnolia.dsi.signaturemanager.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SignatureRequestMapperTest {

    private SignatureRequestMapper signatureRequestMapper;

    @BeforeEach
    public void setUp() {
        signatureRequestMapper = new SignatureRequestMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(signatureRequestMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(signatureRequestMapper.fromId(null)).isNull();
    }
}
