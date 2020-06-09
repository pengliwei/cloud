package com.awei.ad.advice;

import com.awei.ad.exception.AdException;
import com.awei.ad.vo.CommonResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ：penglw
 * @date ：2020/6/9 17:34
 */
@RestControllerAdvice
public class GlobalExceptionAdvice {


    /**
     * 对统一异常进行处理
     */
    @ExceptionHandler(value = AdException.class)
    public CommonResponse<String> handlerAdException(HttpServletRequest req,
                                                     AdException ex) {
        CommonResponse<String> response = new CommonResponse<>(-1,
                "business error(统一异常处理)");
        response.setData(ex.getMessage());
        return response;
    }
}
