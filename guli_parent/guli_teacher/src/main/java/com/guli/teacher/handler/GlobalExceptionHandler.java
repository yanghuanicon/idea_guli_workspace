package com.guli.teacher.handler;

import com.guli.common.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 统一异常处理类
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        e.printStackTrace();
        return Result.error().message("出错了");
    }

    /**
     *  特殊异常配置
     */
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result error(ArithmeticException e){
        e.printStackTrace();
        return Result.error().message("特殊异常执行啦！");
    }
    /**
     * 自定义异常
     */
    @ExceptionHandler(EduException.class)
    @ResponseBody
    public Result error(EduException e) {
        e.printStackTrace();
        return Result.error().code(e.getCode()).message(e.getMessage());
    }

}
