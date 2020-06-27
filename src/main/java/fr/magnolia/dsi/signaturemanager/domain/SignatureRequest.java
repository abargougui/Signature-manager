package fr.magnolia.dsi.signaturemanager.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

import fr.magnolia.dsi.signaturemanager.domain.enumeration.Status;

/**
 * A SignatureRequest.
 */
@Document(collection = "signature_request")
public class SignatureRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("customer_id")
    private String customerId;

    @NotNull
    @Field("contract_id")
    private String contractId;

    @NotNull
    @Field("transaction_id")
    private String transactionId;

    @NotNull
    @Field("request_date")
    private LocalDate requestDate;

    @Field("file_url")
    private String fileUrl;

    @Field("signature_date")
    private LocalDate signatureDate;

    @NotNull
    @Field("status")
    private Status status;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public SignatureRequest customerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getContractId() {
        return contractId;
    }

    public SignatureRequest contractId(String contractId) {
        this.contractId = contractId;
        return this;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public SignatureRequest transactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public SignatureRequest requestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
        return this;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public SignatureRequest fileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
        return this;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public LocalDate getSignatureDate() {
        return signatureDate;
    }

    public SignatureRequest signatureDate(LocalDate signatureDate) {
        this.signatureDate = signatureDate;
        return this;
    }

    public void setSignatureDate(LocalDate signatureDate) {
        this.signatureDate = signatureDate;
    }

    public Status getStatus() {
        return status;
    }

    public SignatureRequest status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SignatureRequest)) {
            return false;
        }
        return id != null && id.equals(((SignatureRequest) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SignatureRequest{" +
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
