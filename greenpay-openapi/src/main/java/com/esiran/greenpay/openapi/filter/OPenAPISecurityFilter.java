package com.esiran.greenpay.openapi.filter;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.esiran.greenpay.common.entity.APIException;
import com.esiran.greenpay.common.sign.HMACMD5SignType;
import com.esiran.greenpay.common.sign.Md5SignType;
import com.esiran.greenpay.common.sign.SignType;
import com.esiran.greenpay.common.sign.SignVerify;
import com.esiran.greenpay.common.util.MapUtil;
import com.esiran.greenpay.common.wrapper.JsonRequestWrapper;
import com.esiran.greenpay.merchant.entity.Merchant;
import com.esiran.greenpay.merchant.service.IMerchantService;
import com.esiran.greenpay.openapi.security.OpenAPISecurityUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Component
public class OPenAPISecurityFilter implements Filter {
    private final static Gson gson = new Gson();
    private final IMerchantService merchantService;
    @Autowired
    public OPenAPISecurityFilter(IMerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    private void printError(HttpServletResponse response, String code,String msg, int status) throws IOException {
        Map<String,String> out = new LinkedHashMap<>();
        out.put("code",code);
        out.put("msg",msg);
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(status);
        String body = gson.toJson(out);
        PrintWriter pw = response.getWriter();
        pw.println(body);
        pw.flush();
        pw.close();
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        JsonRequestWrapper jsonRequestWrapper = new JsonRequestWrapper(httpServletRequest);
        String body = jsonRequestWrapper.getBody();
        try {
            Map<String,String> params = gson.fromJson(body,new TypeToken<Map<String,String>>(){}.getType());
            verifyRequisiteParam(params);
            verifyAuth(params);
            filterChain.doFilter(jsonRequestWrapper,servletResponse);
        }catch (Exception e){
            if (e instanceof ClassCastException || e instanceof NumberFormatException){
                printError(httpServletResponse,"INVALID_REQUEST","请求参数无效",400);
            }else if(e instanceof APIException){
                APIException apiException = (APIException) e;
                printError(
                        httpServletResponse,
                        apiException.getCode(),
                        e.getMessage(),
                        apiException.getStatus()
                );
            }else {
                printError(httpServletResponse,"UNKNOWN_ERROR","未知错误",500);
                e.printStackTrace();
            }
        }
    }


    private static void verifyRequisiteParam(Map<String,String> params) throws APIException {
        String timestampStr = params.get("timestamp");
        if (timestampStr == null)
            throw new APIException("请求已过期，或超时","INVALID_REQUEST",400);
        long timestamp = Long.parseLong(timestampStr);
        long currentTimestamp = System.currentTimeMillis();
        long timeDiff = currentTimestamp - timestamp;
        if (timeDiff > 180000)
            throw new APIException("请求已过期，或超时","INVALID_REQUEST",400);
    }


    private void verifyAuth(Map<String,String> params) throws APIException {
        String signTypeStr = params.get("signType");
        if (StringUtils.isEmpty(signTypeStr))
            throw new APIException("无效的签名方式","INVALID_REQUEST_SIGN_TYPE",400);
        String principal = MapUtil.sortAndSerialize(params,new String[]{"sign"});
        SignType signType = signTypeStr.equals("md5") ? new Md5SignType(principal)
                : signTypeStr.equals("hmac_md5") ? new HMACMD5SignType(principal):null;
        if (signType == null)
            throw new APIException("无效的签名方式","INVALID_REQUEST_SIGN_TYPE",400);
        String apiKey = params.get("apiKey");
        if (StringUtils.isEmpty(apiKey))
            throw new APIException("无效的 API_KEY","INVALID_API_KEY",400);
        LambdaQueryWrapper<Merchant> queryWrapper = new QueryWrapper<Merchant>()
                .lambda().eq(Merchant::getApiKey,apiKey);
        Merchant m = merchantService.getOne(queryWrapper);
        if (m == null)
            throw new APIException("无效的 API_KEY","INVALID_API_KEY",400);
        SignVerify signVerify = signType.sign(m.getApiSecurity());
        String sign = params.get("sign");
        if (StringUtils.isEmpty(sign))
            throw new APIException("签名内容不能为空","INVALID_REQUEST",400);
        if (signVerify.verify(sign))
            throw new APIException("签名校验失败","INVALID_SIGN",400);
        OpenAPISecurityUtils.setSubject(m);
    }

    @Override
    public void destroy() {

    }
}
