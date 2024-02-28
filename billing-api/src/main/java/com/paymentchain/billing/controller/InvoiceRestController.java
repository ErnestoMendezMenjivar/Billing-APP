/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paymentchain.billing.controller;

import com.paymentchain.billing.common.InvoiceRequestMapper;
import com.paymentchain.billing.common.InvoiceResponseMapper;
import com.paymentchain.billing.common.dto.InvoiceRequest;
import com.paymentchain.billing.common.dto.InvoiceResponse;
import com.paymentchain.billing.entities.Invoice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import com.paymentchain.billing.respository.InvoiceRepository;
import com.paymentchain.billing.service.InvoiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.http.HttpStatus;

/**
 *
 * @author mendez
 */
@Tag(name = "Billing API", description = "This API serve all funcionality for management invoices")
@RestController
@RequestMapping("/billing")
public class InvoiceRestController {
    
    @Autowired
    InvoiceService invoiceService;
    
    @Autowired
    InvoiceRequestMapper irm;

    @Autowired
    InvoiceResponseMapper irspm;
    
    @Operation(description = "Return all invoices bundled into Response", summary = "return 204 if no data found")
    @ApiResponse(responseCode = "500", description = "Internal error")
    @GetMapping()
    public ResponseEntity<List<InvoiceResponse>> list() {
        List<Invoice> findAll = invoiceService.getAllInvoices();

        if (findAll.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            List<InvoiceResponse> InvoiceListToInvoiceResposeList = irspm.InvoiceListToInvoiceResposeList(findAll);
            return ResponseEntity.ok().body(InvoiceListToInvoiceResposeList);
        }

    } 
    
    @GetMapping("/{id}")
    public InvoiceResponse get(@PathVariable String id) {
        Optional<Invoice> findById = invoiceService.getInvoiceById(Long.valueOf(id));
        InvoiceResponse InvoiceToInvoiceResponse = irspm.InvoiceToInvoiceResponse(findById.get());
        return  InvoiceToInvoiceResponse;
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable String id, @RequestBody InvoiceRequest input) {
        Optional<Invoice> findById = invoiceService.getInvoiceById(Long.valueOf(id));
        if (findById.isPresent()) {
                Invoice existingInvoice = findById.get();
                existingInvoice.setAmount(input.getAmount());
                existingInvoice.setDetail(input.getDetail());
                existingInvoice.setCustomerId(input.getCustomer());
                existingInvoice.setNumber(input.getNumber());
                
                Invoice UpdateInvoice = invoiceService.saveInvoice(existingInvoice);
                InvoiceResponse updatedResponse = irspm.InvoiceToInvoiceResponse(UpdateInvoice);
            return ResponseEntity.ok(updatedResponse);
        } else {
            return ResponseEntity.notFound().build();
        }

    }
    
    @Transactional
    @PostMapping
    public ResponseEntity<InvoiceResponse> post(@RequestBody InvoiceRequest input) {
        Invoice InvoiceRequestToInvoice = irm.InvoiceRequestToInvoice(input);
        Invoice savedInvoice = invoiceService.saveInvoice(InvoiceRequestToInvoice);
        InvoiceResponse InvoiceToInvoiceResponse = irspm.InvoiceToInvoiceResponse(savedInvoice);
        return ResponseEntity.status(HttpStatus.CREATED).body(InvoiceToInvoiceResponse);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.ok().build();
    }
}

    

