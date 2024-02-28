/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paymentchain.billing.service;

import com.paymentchain.billing.entities.Invoice;
import com.paymentchain.billing.respository.InvoiceRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 *
 * @author duglas
 */
@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    /**
     * Obtiene todas las facturas y las almacena en la caché 'invoices'.
     *
     * @return Lista de todas las facturas.
     */
    @Cacheable("invoices")
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    /**
     * Obtiene una factura por su identificador.
     *
     * @param id Identificador de la factura.
     * @return Factura correspondiente al identificador proporcionado.
     */
    public Optional<Invoice> getInvoiceById(Long id) {
        return invoiceRepository.findById(id);
    }

    /**
     * Guarda una nueva factura y limpia la caché 'invoices'.
     *
     * @param invoice Nueva factura a guardar.
     * @return Factura guardada.
     */
    @CacheEvict(value = "invoices", allEntries = true)
    public Invoice saveInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    /**
     * Elimina una factura por su identificador y limpia la caché 'invoices'.
     *
     * @param id Identificador de la factura a eliminar.
     */
    @CacheEvict(value = "invoices", allEntries = true)
    public void deleteInvoice(Long id) {
        invoiceRepository.deleteById(id);
    }

     @CacheEvict(value = "invoices", allEntries = true)
    public void evictAllInvoicesCache() {
        
    }

}
