package com.vcom.auxsys.exception;

import com.vcom.auxsys.pojo.JSONResult;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    public Object errorHander( MissingServletRequestParameterException exception){
        return JSONResult.errorMsg("参数" + exception.getParameterName() + "不能为空！");
    }

    @ExceptionHandler
    @ResponseBody
    public Object errUrl(NoHandlerFoundException exception){
        return JSONResult.errorMsg("接口地址" + exception.getRequestURL() + "不存在！");
    }
}
