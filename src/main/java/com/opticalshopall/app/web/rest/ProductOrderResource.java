package com.opticalshopall.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.opticalshopall.app.domain.OrderItem;
import com.opticalshopall.app.domain.Prescription;
import com.opticalshopall.app.domain.Product;
import com.opticalshopall.app.domain.ProductOrder;
import com.opticalshopall.app.service.CustomerService;
import com.opticalshopall.app.service.PrescriptionService;
import com.opticalshopall.app.service.ProductOrderService;
import com.opticalshopall.app.service.ProductService;
import com.opticalshopall.app.web.rest.errors.BadRequestAlertException;
import com.opticalshopall.app.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing ProductOrder.
 */
@RestController
@RequestMapping("/api")
public class ProductOrderResource {

    private final Logger log = LoggerFactory.getLogger(ProductOrderResource.class);

    private static final String ENTITY_NAME = "productOrder";

    private final ProductOrderService productOrderService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private ProductService productService;

    public ProductOrderResource(ProductOrderService productOrderService) {
        this.productOrderService = productOrderService;
    }

    /**
     * POST  /product-orders : Create a new productOrder.
     *
     * @param productOrder the productOrder to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productOrder, or with status 400 (Bad Request) if the productOrder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/product-orders")
    @Timed
    public ResponseEntity<ProductOrder> createProductOrder(@Valid @RequestBody ProductOrder productOrder) throws URISyntaxException {
        log.debug("REST request to save ProductOrder : {}", productOrder);
        if (productOrder.getId() != null) {
            throw new BadRequestAlertException("A new productOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductOrder result = productOrderService.save(productOrder);
        return ResponseEntity.created(new URI("/api/product-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * POST  /product-orders : Create a new productOrder.
     *
     *
     * @return the ResponseEntity with status 201 (Created) and with body the new productOrder, or with status 400 (Bad Request) if the productOrder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/product-orders1")
    @Timed
    public ResponseEntity<ProductOrder> createProductOrder() throws URISyntaxException {
        ProductOrder productOrder= new ProductOrder();
        log.debug("REST request to save ProductOrder : {}", productOrder);
        if (productOrder.getId() != null) {
            throw new BadRequestAlertException("A new productOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }

        Prescription prescription= prescriptionService.findOne(1303L).get();
        Prescription prescription1= prescriptionService.findOne(1301L).get();
        Product product = productService.findOne(1151L).get();
        Product product1 = productService.findOne(1152L).get();
        OrderItem item = new OrderItem();

        item.setQuantity(20);
        item.setTotalPrice(new BigDecimal(30));
        item.setFinalPrice(new BigDecimal(30));
        item.setPrescription(prescription);
        item.setProduct(product);

        OrderItem item1 = new OrderItem();

        item1.setQuantity(50);
        item1.setTotalPrice(new BigDecimal(60));
        item1.setFinalPrice(new BigDecimal(60));
        item1.setPrescription(prescription1);
        item1.setProduct(product1);


        productOrder.setCustomer(customerService.findOne(1201L).get());
        productOrder.setInvoiceId(123);
        productOrder.addOrderitem(item);
        productOrder.addOrderitem(item1);

        productOrder.setPlacedDate(Instant.ofEpochMilli(0L));
        productOrder.setTotalCost(new BigDecimal(1000));
        productOrder.setQuantity(200);

        ProductOrder result = productOrderService.save(productOrder);
        System.out.println("output is"+ result.getId());
        return ResponseEntity.created(new URI("/api/product-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /product-orders : Updates an existing productOrder.
     *
     * @param productOrder the productOrder to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productOrder,
     * or with status 400 (Bad Request) if the productOrder is not valid,
     * or with status 500 (Internal Server Error) if the productOrder couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/product-orders")
    @Timed
    public ResponseEntity<ProductOrder> updateProductOrder(@Valid @RequestBody ProductOrder productOrder) throws URISyntaxException {
        log.debug("REST request to update ProductOrder : {}", productOrder);
        if (productOrder.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductOrder result = productOrderService.save(productOrder);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, productOrder.getId().toString()))
            .body(result);
    }

    /**
     * GET  /product-orders : get all the productOrders.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of productOrders in body
     */
    @GetMapping("/product-orders")
    @Timed
    public List<ProductOrder> getAllProductOrders() {
        log.debug("REST request to get all ProductOrders");
        return productOrderService.findAll();
    }

    /**
     * GET  /product-orders/:id : get the "id" productOrder.
     *
     * @param id the id of the productOrder to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productOrder, or with status 404 (Not Found)
     */
    @GetMapping("/product-orders/{id}")
    @Timed
    public ResponseEntity<ProductOrder> getProductOrder(@PathVariable Long id) {
        log.debug("REST request to get ProductOrder : {}", id);
        Optional<ProductOrder> productOrder = productOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productOrder);
    }

    /**
     * DELETE  /product-orders/:id : delete the "id" productOrder.
     *
     * @param id the id of the productOrder to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/product-orders/{id}")
    @Timed
    public ResponseEntity<Void> deleteProductOrder(@PathVariable Long id) {
        log.debug("REST request to delete ProductOrder : {}", id);
        productOrderService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/product-orders?query=:query : search for the productOrder corresponding
     * to the query.
     *
     * @param query the query of the productOrder search
     * @return the result of the search
     */
    @GetMapping("/_search/product-orders")
    @Timed
    public List<ProductOrder> searchProductOrders(@RequestParam String query) {
        log.debug("REST request to search ProductOrders for query {}", query);
        return productOrderService.search(query);
    }

}
