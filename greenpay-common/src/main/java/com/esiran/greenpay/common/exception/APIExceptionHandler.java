package com.esiran.greenpay.common.exception;

import com.esiran.greenpay.common.entity.APIError;
import com.esiran.greenpay.common.entity.APIException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class APIExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletResponse response){
        BindingResult bindResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindResult.getFieldErrors();
        List<APIError> errors = fieldErrors.stream().map(item->{
            APIError apiError = new APIError();
            String code = item.getCode();
            String field = item.getField();
            assert code != null;
            apiError.setCode(field.concat(".").concat(code));
            apiError.setMessage(item.getDefaultMessage());
            return apiError;
        }).collect(Collectors.toList());
        Map<String,Object> map = new HashMap<>();
        map.put("code","ARGUMENT_NOT_VALID");
        map.put("message", "请求参数校验失败");
        map.put("errors", errors);
        response.setStatus(400);
        return map;
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Map<String,Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException e, HttpServletResponse response){
        Map<String,Object> map = new HashMap<>();
        map.put("code","ARGUMENT_NOT_VALID");
        map.put("message", "请求参数校验失败");
        response.setStatus(400);
        return map;
    }

    @ExceptionHandler(APIException.class)
    public  Map<String,Object> handleAPIException(APIException e,HttpServletResponse response){
        Map<String,Object> map = new HashMap<>();
        map.put("code",e.getCode());
        map.put("message", e.getMessage());
        response.setStatus(400);
        return map;
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public  Map<String,Object> handleAPIException(HttpRequestMethodNotSupportedException e,HttpServletResponse response){
        Map<String,Object> map = new HashMap<>();
        map.put("code","REQUEST_METHOD_NOT_SUPPORTED");
        map.put("message", e.getMessage());
        response.setStatus(404);
        return map;
    }
    @ExceptionHandler(Exception.class)
    public Map<String,Object> handleDefaultException(Exception e,HttpServletResponse response){
        e.printStackTrace();
        response.setStatus(500);
        Map<String,Object> map = new HashMap<>();
        map.put("code","SERVER_ERROR");
        map.put("message", e.getMessage());
        return map;
    }
}
