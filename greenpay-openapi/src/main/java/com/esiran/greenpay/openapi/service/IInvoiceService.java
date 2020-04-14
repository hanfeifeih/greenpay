package com.esiran.greenpay.openapi.service;

import com.esiran.greenpay.merchant.entity.Merchant;
import com.esiran.greenpay.openapi.entity.Invoice;
import com.esiran.greenpay.openapi.entity.InvoiceInputDTO;

public interface IInvoiceService {
    Invoice createInvoiceByInput(InvoiceInputDTO invoiceInputDTO, Merchant merchant) throws Exception;
}
