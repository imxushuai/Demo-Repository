package com.imxushuai.jsr303.exception;

import com.imxushuai.jsr303.entity.CustomerResult;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一异常处理
 */
@RestControllerAdvice
public class Jsr303ExceptionControllerAdvice {

    /**
     * MethodArgumentNotValidException异常统一处理
     * 仅处理MethodArgumentNotValidException异常
     *
     * @param exception MethodArgumentNotValidException
     * @return 异常返回
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<CustomerResult> handleValidException(MethodArgumentNotValidException exception) {
        Map<String, String> map = new HashMap<>();
        BindingResult bindingResult = exception.getBindingResult();
        bindingResult.getFieldErrors().forEach(fieldError -> {
            String message = fieldError.getDefaultMessage();
            String field = fieldError.getField();
            map.put(field, message);
        });

        return ResponseEntity.ok().body(CustomerResult.result(400, "参数异常", map));
    }

    /**
     * 通用异常处理
     *
     * @param throwable 异常
     * @return 异常返回
     */
    @ExceptionHandler(value = Throwable.class)
    public ResponseEntity<CustomerResult> handleException(Throwable throwable) {
        return ResponseEntity.ok().body(CustomerResult.result(500, "未知异常"));
    }

}
