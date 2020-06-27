package fr.magnolia.dsi.signaturemanager.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import fr.magnolia.dsi.signaturemanager.domain.enumeration.Status;

/**
 * A DTO for the {@link fr.magnolia.dsi.signaturemanager.domain.SignatureRequest} entity.
 */
public class SignatureRequestDTO implements Serializable {
    
    private String id;

    @NotNull
    private String customerId;

    @NotNull
    private String contractId;

    @NotNull
    private String transactionId;

    @NotNull
    private LocalDate requestDate;

    private String fileUrl;

    private LocalDate signatureDate;

    @NotNull
    private Status status;

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public LocalDate getSignatureDate() {
        return signatureDate;
    }

    public void setSignatureDate(LocalDate signatureDate) {
        this.signatureDate = signatureDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SignatureRequestDTO)) {
            return false;
        }

        return id != null && id.equals(((SignatureRequestDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SignatureRequestDTO{" +
            "id=" + getId() +
            ", customerId='" + getCustomerId() + "'" +
            ", contractId='" + getContractId() + "'" +
            ", transactionId='" + getTransactionId() + "'" +
            ", requestDate='" + getRequestDate() + "'" +
            ", fileUrl='" + getFileUrl() + "'" +
            ", signatureDate='" + getSignatureDate() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
