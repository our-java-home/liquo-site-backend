package com.javahome.wine.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javahome.wine.vo.ResultDataVO;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Resource;

/**
 * @author 勿忘初心
 * @since 2023-06-21-22:14
 * 响应体切面类，用于消除重复调用ResultVO对象返回数据而产生的代码冗余以及不便
 * @RestControllerAdvice 全局捕获抛出的异常，全局数据绑定，全局数据预处理。
 *
 */
@RestControllerAdvice
public class ResponseAspect implements ResponseBodyAdvice<Object> {

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        /**
         * 返回类型为String则需要手动序列化
         */
        if(body instanceof String){
            return objectMapper.writeValueAsString(ResultDataVO.success(body));
        }
        /**
         * 已被包装为全局VO对象直接返回
         */
        if(body instanceof ResultDataVO){
            return body;
        }

        return ResultDataVO.success(body);
    }
}
