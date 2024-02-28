/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paymentchain.billing.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;



/**
 *
 * @author sotobotero
 */
@Entity
@Table(name="invoice")
@Data
public class Invoice {
   @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
   private long id;
   
   @Column(name = "customerId")
   private long customerId;
   
   @Column(name = "number")
   private String number;
   
   @Column(name = "detail")
   private String detail;
   
   @Column(name = "amount")
   private double amount;  
}
