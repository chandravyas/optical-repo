package com.opticalshopall.app.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Prescription.
 */
@Entity
@Table(name = "prescription")
@Document(indexName = "prescription")
public class Prescription implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "phonenumber", nullable = false)
    private String phonenumber;

    @NotNull
    @Column(name = "patient_name", nullable = false)
    private String patientName;

    @Column(name = "doctor_name")
    private String doctorName;

    @Column(name = "right_vision")
    private String rightVision;

    @Column(name = "left_vision")
    private String leftVision;

    @Lob
    @Column(name = "power_details")
    private String powerDetails;

    @Column(name = "continous_wear")
    private Boolean continousWear;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public Prescription phonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
        return this;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPatientName() {
        return patientName;
    }

    public Prescription patientName(String patientName) {
        this.patientName = patientName;
        return this;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public Prescription doctorName(String doctorName) {
        this.doctorName = doctorName;
        return this;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getRightVision() {
        return rightVision;
    }

    public Prescription rightVision(String rightVision) {
        this.rightVision = rightVision;
        return this;
    }

    public void setRightVision(String rightVision) {
        this.rightVision = rightVision;
    }

    public String getLeftVision() {
        return leftVision;
    }

    public Prescription leftVision(String leftVision) {
        this.leftVision = leftVision;
        return this;
    }

    public void setLeftVision(String leftVision) {
        this.leftVision = leftVision;
    }

    public String getPowerDetails() {
        return powerDetails;
    }

    public Prescription powerDetails(String powerDetails) {
        this.powerDetails = powerDetails;
        return this;
    }

    public void setPowerDetails(String powerDetails) {
        this.powerDetails = powerDetails;
    }

    public Boolean isContinousWear() {
        return continousWear;
    }

    public Prescription continousWear(Boolean continousWear) {
        this.continousWear = continousWear;
        return this;
    }

    public void setContinousWear(Boolean continousWear) {
        this.continousWear = continousWear;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Prescription prescription = (Prescription) o;
        if (prescription.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), prescription.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Prescription{" +
            "id=" + getId() +
            ", phonenumber='" + getPhonenumber() + "'" +
            ", patientName='" + getPatientName() + "'" +
            ", doctorName='" + getDoctorName() + "'" +
            ", rightVision='" + getRightVision() + "'" +
            ", leftVision='" + getLeftVision() + "'" +
            ", powerDetails='" + getPowerDetails() + "'" +
            ", continousWear='" + isContinousWear() + "'" +
            "}";
    }
}
