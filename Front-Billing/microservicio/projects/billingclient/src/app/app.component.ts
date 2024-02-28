import { Component, TemplateRef, OnInit, ViewChild, AfterViewInit, ElementRef } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { BillingAPIService } from '../services/swaggerBillingAPI/api/billingAPI.service';
import { InvoiceResponse } from '../services/swaggerBillingAPI/model/invoiceResponse';
import 'jquery';
declare var $: any;
import Swal from 'sweetalert2';









@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements AfterViewInit {

  displayedColumns: string[] = ['invoiceId', 'number', 'customer', 'detail', 'amount','actions'];
  title = 'billingclient';
  bills: MatTableDataSource<InvoiceResponse>;
  billingForm: FormGroup;
  hideFields: boolean = false;
  billingFormEdit: FormGroup;
  billingFormDelete: FormGroup;
  selectedInvoice: InvoiceResponse | null = null;


  @ViewChild(MatPaginator)
  paginator!: MatPaginator;

  @ViewChild('modalTemplate')
  modalTemplate!: ElementRef;

  constructor(private billingService: BillingAPIService, private formBuilder: FormBuilder) {
    this.bills = new MatTableDataSource<InvoiceResponse>();

    this.billingForm = this.formBuilder.group({
      number: ['', Validators.required],
      customer: ['', Validators.required],
      detail: ['', Validators.required],
      amount: ['', Validators.required],
    });

    this.billingFormEdit = this.formBuilder.group({
      invoiceId: [['', /*Validators.required*/]],
      number: [[Validators.required]],
      customer: [ [Validators.required]],
      detail: ['', [Validators.required]],
      amount: ['', [Validators.required]],
    });

    this.billingFormDelete = this.formBuilder.group({
      invoiceId: [['', /*Validators.required*/]],
      number: [[Validators.required]],
      customer: [ [Validators.required]],
      detail: ['', [Validators.required]],
      amount: ['', [Validators.required]],
    });

  }

  ngAfterViewInit() {
    this.bills.paginator = this.paginator;
  }




  ngOnInit(): void {
    this.billingService.listUsingLIST().subscribe(
      data => {
        console.log(data);
        this.bills.data = data;;
      },
      error => {
        console.error(error);
      }
    );
  }


  //crear nueva factura
  onSubmit() {
    this.markFormGroupTouched(this.billingForm);
    console.log('Your form data : ',);
    if (this.billingForm.valid) {
      this.billingService.postUsingPOST(this.billingForm.value).subscribe(response => {
        console.log(response);
        $('#staticEdit').modal('hide');
                // Muestra el mensaje de éxito con SweetAlert2
                Swal.fire({
                  position: 'top-end',
                  icon: 'success',
                  title: 'Éxito',
                  text: 'Factura Creada con éxito',
                  showConfirmButton: false,
                  timer: 2300
                });
        this.ngOnInit();
      },
        error => {
          console.log(error.error.mensaje);
        });
      console.log('Datos del formulario', this.billingForm.value);
    } else {
      console.log('Formulario no valido');
    }

  }

  //editar factura existente
  onSubmitEdit(){
    this.markFormGroupTouched(this.billingFormEdit);

    this.billingFormEdit.get('invoiceId')?.enable();
    this.billingFormEdit.get('number')?.enable();
    this.billingFormEdit.get('customer')?.enable();

    console.log(this.billingFormEdit.value);
    if(this.billingFormEdit.valid){
      const id = this.billingFormEdit.get('invoiceId')?.value; // Obtener el id de la factura
      this.billingService.putUsingPUT(this.billingFormEdit.value,id).subscribe(response => {
        console.log(response);
        $('#staticEdit').modal('hide');
                // Muestra el mensaje de éxito con SweetAlert2
                Swal.fire({
                  position: 'top-end',
                  icon: 'success',
                  title: 'Éxito',
                  text: 'Factura actualiza con éxito',
                  showConfirmButton: false,
                  timer: 2300
                });
        this.ngOnInit();
      });
      this.hideFields = true;
    }
  }


  onSubmitDelete(){
    this.markFormGroupTouched(this.billingFormDelete);

    if(this.billingFormDelete.valid){
      const id = this.billingFormDelete.get('invoiceId')?.value;
      this.billingService.deleteUsingDELETE(id).subscribe(data => {
        $('#staticDelete').modal('hide');
        Swal.fire({
          position: 'top-end',
          icon: 'success',
          title: 'Exito',
          text: 'Factura eliminada con exito',
          showConfirmButton: false,
          timer: 2300
        });
        this.ngOnInit();
      })
    }

  }


  //validar campos vacios
  private markFormGroupTouched(formGroup: FormGroup) {
    Object.values(formGroup.controls).forEach(control => {
      control.markAsTouched();
  
      if (control instanceof FormGroup) {
        this.markFormGroupTouched(control);
      }
    });
  }

  //mostrar datos en modal editar
  editInvoice(invoice: InvoiceResponse) {
    this.selectedInvoice = invoice;
    console.log('Editar factura', invoice);

    this.billingFormEdit.patchValue({
      invoiceId: invoice.invoiceId,
      number: invoice.number,
      customer: invoice.customer,
      detail: invoice.detail,
      amount: invoice.amount
    });
    this.hideFields = true; 
    $('#staticEdit').modal('show'); 

  }

  deleteInvoice(invoice: InvoiceResponse) {
    this.selectedInvoice = invoice;

    this.billingFormDelete.patchValue({
      invoiceId: invoice.invoiceId,
      number: invoice.number,
      customer: invoice.customer,
      detail: invoice.detail,
      amount: invoice.amount
    });
    
  // Deshabilitar todos los controles excepto el botón de eliminación
  const controls = this.billingFormDelete.controls;
  Object.keys(controls).forEach(controlName => {
    if (controlName !== 'invoiceId' && controlName !== 'number' &&
        controlName !== 'customer' && controlName !== 'detail' &&
        controlName !== 'amount') {
      controls[controlName].disable();
    }
  });

    $('staticDelete').modal('show');
  } 


}
