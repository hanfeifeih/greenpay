package com.esiran.greenpay.openapi.controller;

import com.esiran.greenpay.common.entity.APIException;
import com.esiran.greenpay.merchant.entity.Merchant;
import com.esiran.greenpay.openapi.entity.Invoice;
import com.esiran.greenpay.openapi.entity.InvoiceInputDTO;
import com.esiran.greenpay.openapi.security.OpenAPISecurityUtils;
import com.esiran.greenpay.openapi.service.IInvoiceService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/invoices")
public class APIInvoices {
    private final IInvoiceService invoiceService;

    public APIInvoices(IInvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping
    public Invoice create(@Valid InvoiceInputDTO invoiceDto) throws Exception {
        Merchant merchant = OpenAPISecurityUtils.getSubject();
        return invoiceService.createInvoiceByInput(invoiceDto,merchant);
    }
}
