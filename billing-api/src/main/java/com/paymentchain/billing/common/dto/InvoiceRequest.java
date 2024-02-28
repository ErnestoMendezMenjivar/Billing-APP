/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paymentchain.billing.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

/**
 *
 * @author duglas
 */
@Schema(name = "InvoiceRequest", description = "Model represent a Invoice on database")
@Data
public class InvoiceRequest {
    @Schema(name = "customer", required = true, example = "2", defaultValue = "1", description = "Unique Id of customer that represent the owner of invoice")
    private long customer;
    @Schema(name = "number", required = true, example = "001", defaultValue = "001", description = "Number given in fisical invoice")
    private String number;
    private String detail;
    private double amount;
}
