package com.opticalshopall.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.opticalshopall.app.domain.enumeration.ProductType;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Document(indexName = "product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_type")
    private ProductType productType;

    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String description;

    @Column(name = "color")
    private String color;

    @Column(name = "costprice", precision = 10, scale = 2)
    private BigDecimal costprice;

    @Column(name = "selling_price", precision = 10, scale = 2)
    private BigDecimal sellingPrice;

    @Column(name = "glass_coating")
    private String glassCoating;

    @Column(name = "glass_design")
    private String glassDesign;

    @Column(name = "quantity_available")
    private Long quantityAvailable;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    @Column(name = "updated_on")
    private ZonedDateTime updatedOn;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "barcode")
    private String barcode;

    @ManyToOne
    @JsonIgnoreProperties("products")
    private Company company;

    @ManyToOne
    @JsonIgnoreProperties("products")
    private Quality quality;

    @ManyToOne
    @JsonIgnoreProperties("products")
    private Supplier supplier;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Product name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductType getProductType() {
        return productType;
    }

    public Product productType(ProductType productType) {
        this.productType = productType;
        return this;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public String getCode() {
        return code;
    }

    public Product code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public Product description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public Product color(String color) {
        this.color = color;
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public BigDecimal getCostprice() {
        return costprice;
    }

    public Product costprice(BigDecimal costprice) {
        this.costprice = costprice;
        return this;
    }

    public void setCostprice(BigDecimal costprice) {
        this.costprice = costprice;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public Product sellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
        return this;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getGlassCoating() {
        return glassCoating;
    }

    public Product glassCoating(String glassCoating) {
        this.glassCoating = glassCoating;
        return this;
    }

    public void setGlassCoating(String glassCoating) {
        this.glassCoating = glassCoating;
    }

    public String getGlassDesign() {
        return glassDesign;
    }

    public Product glassDesign(String glassDesign) {
        this.glassDesign = glassDesign;
        return this;
    }

    public void setGlassDesign(String glassDesign) {
        this.glassDesign = glassDesign;
    }

    public Long getQuantityAvailable() {
        return quantityAvailable;
    }

    public Product quantityAvailable(Long quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
        return this;
    }

    public void setQuantityAvailable(Long quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public ZonedDateTime getCreatedOn() {
        return createdOn;
    }

    public Product createdOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public ZonedDateTime getUpdatedOn() {
        return updatedOn;
    }

    public Product updatedOn(ZonedDateTime updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(ZonedDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Boolean isActive() {
        return active;
    }

    public Product active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getBarcode() {
        return barcode;
    }

    public Product barcode(String barcode) {
        this.barcode = barcode;
        return this;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Company getCompany() {
        return company;
    }

    public Product company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Quality getQuality() {
        return quality;
    }

    public Product quality(Quality quality) {
        this.quality = quality;
        return this;
    }

    public void setQuality(Quality quality) {
        this.quality = quality;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public Product supplier(Supplier supplier) {
        this.supplier = supplier;
        return this;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
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
        Product product = (Product) o;
        if (product.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), product.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", productType='" + getProductType() + "'" +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", color='" + getColor() + "'" +
            ", costprice=" + getCostprice() +
            ", sellingPrice=" + getSellingPrice() +
            ", glassCoating='" + getGlassCoating() + "'" +
            ", glassDesign='" + getGlassDesign() + "'" +
            ", quantityAvailable=" + getQuantityAvailable() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", active='" + isActive() + "'" +
            ", barcode='" + getBarcode() + "'" +
            "}";
    }
}
