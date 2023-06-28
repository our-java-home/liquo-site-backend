package com.javahome.wine.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javahome.wine.exception.BusinessException;
import com.javahome.wine.exception.ExceptionCodeEnum;
import com.javahome.wine.vo.ResultDataVO;
import javassist.tools.web.BadHttpRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.ExceptionUtil;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.zip.DataFormatException;

/**
 * @Author: 勿忘初心
 * @Date: 2023-06-28 17:22
 * 全局异常统一处理
 * 新增响应体增强，用于消除重复调用ResultVO对象返回数据而产生的代码冗余以及不便
 * @RestControllerAdvice 全局捕获抛出的异常，全局数据绑定，全局数据预处理。
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionAdvice implements ResponseBodyAdvice<Object> {


    @Resource
    private ObjectMapper objectMapper;

    /**
     * http请求的方法不正确
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResultDataVO handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("捕获http请求的方法不匹配异常",e);
        return ResultDataVO.failure(ExceptionCodeEnum.EC10006.getCode(),
                ExceptionCodeEnum.EC10006.getMessage());
    }

    /**
     * 请求参数不全
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResultDataVO missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException e) {
        log.error("捕获参数缺失异常",e);
        return ResultDataVO.failure(ExceptionCodeEnum.EC10001.getCode(),
                ExceptionCodeEnum.EC10001.getMessage());
    }

    /**
     * 请求参数类型不正确
     */
    @ExceptionHandler(TypeMismatchException.class)
    public ResultDataVO typeMismatchExceptionHandler(TypeMismatchException e) {
        log.error("捕获请求参数类型不匹配异常",e);
        return ResultDataVO.failure(ExceptionCodeEnum.EC10001.getCode(),
                ExceptionCodeEnum.EC10001.getMessage());
    }

    /**
     * 数据格式转换错误
     */
    @ExceptionHandler(DataFormatException.class)
    @ResponseBody
    public ResultDataVO dataFormatExceptionHandler(DataFormatException e) {
        log.error("捕获数据格式转换错误异常",e);
        return ResultDataVO.failure(ExceptionCodeEnum.EC10001.getCode(),
                ExceptionCodeEnum.EC10001.getMessage());
    }


    ///**
    // * 请求参数类型不匹配异常
    // * @param e
    // * @return
    // */
    //@ExceptionHandler(value = HttpMessageNotReadableException.class)
    //public ResultDataVO handleHTTPBindException(HttpMessageNotReadableException e) {
    //    log.error("捕获HTTP请求参数异常", e);
    //    return ResultDataVO.failure(ExceptionCodeEnum.EC10001.getCode(),
    //            ExceptionCodeEnum.EC10001.getMessage());
    //}

    /**
     * 业务异常捕获
     * @param businessException
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    public ResultDataVO handleBusinessException(BusinessException businessException){
        log.error("捕获业务异常", businessException);
        return ResultDataVO.failure(businessException.getCode(),
                businessException.getMessage());
    }


    /**
     * 普通参数入参不合法,类型不匹配
     * @return
     */
    //@ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    //public ResultDataVO handleArgumentTypeException(MethodArgumentTypeMismatchException e){
    //    log.error("捕获参数类型不匹配异常",e);
    //    return ResultDataVO.failure(ExceptionCodeEnum.EC10001.getCode(),
    //            ExceptionCodeEnum.EC10001.getMessage());
    //}

    /**
     * 请求参数绑定到JavaBean对象参数检验失败异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResultDataVO handleBeanBindException(MethodArgumentNotValidException e){
        log.error("捕获绑定Bean参数异常", e);
        // 参数错误有多个，需要用List接收
        List<ObjectError> errorList = e.getBindingResult().getAllErrors();
        // 拼接错误信息
        StringBuffer errMsg = new StringBuffer();
        errorList.forEach(error ->
                errMsg.append(error.getDefaultMessage()).append(";"));
        return ResultDataVO.failure(ExceptionCodeEnum.EC10001.getCode(),
                errMsg.toString());
    }

    /**
     * 普通参数对象绑定失败异常
     * @return
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResultDataVO handleMethodArgsNotValidException(ConstraintViolationException e){
        log.error("捕获普通参数异常", e);
        String sourceMsg = e.getMessage();
        // [),需要+1
        int index  = sourceMsg.lastIndexOf(".") + 1;
        String errMsg = sourceMsg.substring(index).replace(": ", "");
        return ResultDataVO.failure(ExceptionCodeEnum.EC10001.getCode(),
                errMsg);
    }

    /**
     * 运行时异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = RuntimeException.class)
    public ResultDataVO handleRuntimeException(RuntimeException e){
        log.error("捕获运行时异常", e);
        return ResultDataVO.failure(ExceptionCodeEnum.EC10000.getCode(),
                ExceptionCodeEnum.EC10000.getMessage());
    }


    /**
     * 系统级异常
     * @param throwable
     */
    @ExceptionHandler(value = Throwable.class)
    public ResultDataVO handleThrowable(Throwable throwable){
        log.error("捕获系统级异常", throwable);
        return ResultDataVO.failure(ExceptionCodeEnum.EC10000.getCode(),
                ExceptionCodeEnum.EC10000.getMessage());
    }

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
        /**
         * 判断是否为404,500等错误类型
         */
        if(body instanceof LinkedHashMap){
            LinkedHashMap<String,Object> httpErrorCode =(LinkedHashMap<String, Object>) body;
            int code = (int)httpErrorCode.get("status");
            String message = (String) httpErrorCode.get("error");
            return new ResultDataVO(false,code,message,null);

        }


        return ResultDataVO.success(body);
    }
}
