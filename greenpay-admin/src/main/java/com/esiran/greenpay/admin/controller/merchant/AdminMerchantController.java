package com.esiran.greenpay.admin.controller.merchant;

import com.esiran.greenpay.common.entity.APIError;
import com.esiran.greenpay.merchant.entity.Merchant;
import com.esiran.greenpay.merchant.entity.MerchantInputDTO;
import com.esiran.greenpay.merchant.entity.MerchantProductDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin/merchant")
public class AdminMerchantController {

    @GetMapping("/list")
    public String list(){
        return "admin/merchant/list";
    }
    @GetMapping("/list/{mchId}/edit")
    public String add(@PathVariable String mchId){
        return "admin/merchant/edit";
    }
    @GetMapping("/list/{mchId}/product/list")
    public String password(@PathVariable String mchId){
        return "admin/merchant/product/list";
    }


    @GetMapping("/list/{mchId}/product/edit")
    public String product(@PathVariable String mchId, @RequestParam String payTypeCode){
        return "admin/merchant/product/edit";
    }
    @GetMapping("/add")
    @SuppressWarnings("unchecked")
    public String add(HttpSession httpSession, ModelMap modelMap){
        List<APIError> apiErrors = (List<APIError>) httpSession.getAttribute("errors");
        modelMap.addAttribute("errors", apiErrors);
        httpSession.removeAttribute("errors");
        return "admin/merchant/add";
    }

    @PostMapping("/add")
    public String add(@Valid MerchantInputDTO merchant){
        return "admin/merchant/add";
    }
}
