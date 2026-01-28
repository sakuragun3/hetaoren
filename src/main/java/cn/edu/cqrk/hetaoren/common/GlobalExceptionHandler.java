package cn.edu.cqrk.hetaoren.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BizException.class)
    public R handleException(BizException e) {

        log.error(e.toString());
        return R.failed(e.getCode(),e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public R handleException(Exception e){

        log.error(e.toString());
        return R.failed("系统开小差了，请稍后再试");
    }
}
