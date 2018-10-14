package com.opticalshopall.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.opticalshopall.app.domain.enumeration.OrderStatus;

/**
 * A ProductOrder.
 */
@Entity
@Table(name = "product_order")
@Document(indexName = "productorder")
public class ProductOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "total_cost", precision = 10, scale = 2)
    private BigDecimal totalCost;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @NotNull
    @Column(name = "placed_date", nullable = false)
    private Instant placedDate;

    @Column(name = "invoice_id")
    private Integer invoiceId;

    @OneToMany(mappedBy = "productorder", cascade = CascadeType.ALL)
    private Set<OrderItem> orderitems = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("productorders")
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public ProductOrder quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public ProductOrder totalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
        return this;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public ProductOrder status(OrderStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Instant getPlacedDate() {
        return placedDate;
    }

    public ProductOrder placedDate(Instant placedDate) {
        this.placedDate = placedDate;
        return this;
    }

    public void setPlacedDate(Instant placedDate) {
        this.placedDate = placedDate;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public ProductOrder invoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
        return this;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Set<OrderItem> getOrderitems() {
        return orderitems;
    }

    public ProductOrder orderitems(Set<OrderItem> orderItems) {
        this.orderitems = orderItems;
        return this;
    }

    public ProductOrder addOrderitem(OrderItem orderItem) {
        this.orderitems.add(orderItem);
        orderItem.setProductorder(this);
        return this;
    }

    public ProductOrder removeOrderitem(OrderItem orderItem) {
        this.orderitems.remove(orderItem);
        orderItem.setProductorder(null);
        return this;
    }

    public void setOrderitems(Set<OrderItem> orderItems) {
        this.orderitems = orderItems;
    }

    public Customer getCustomer() {
        return customer;
    }

    public ProductOrder customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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
        ProductOrder productOrder = (ProductOrder) o;
        if (productOrder.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productOrder.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductOrder{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", totalCost=" + getTotalCost() +
            ", status='" + getStatus() + "'" +
            ", placedDate='" + getPlacedDate() + "'" +
            ", invoiceId=" + getInvoiceId() +
            "}";
    }
}
