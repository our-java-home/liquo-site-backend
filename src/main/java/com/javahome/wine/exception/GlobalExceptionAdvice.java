package com.javahome.wine.exception;

import com.javahome.wine.vo.ResultDataVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * @author 勿忘初心
 * @since 2023-06-19-17:05
 * 全局异常统一处理
 */

@RestControllerAdvice
@Slf4j
public class GlobalExceptionAdvice {


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
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResultDataVO handleArgumentTypeException(MethodArgumentTypeMismatchException e){
        log.error("捕获参数类型不匹配异常",e);
        return ResultDataVO.failure(ExceptionCodeEnum.EC10001.getCode(),
                ExceptionCodeEnum.EC10001.getMessage());
    }

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
}
