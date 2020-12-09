package com.atguigu.servicebase.handler;

import com.atguigu.commonutils.R;
import com.atguigu.servicebase.handler.GuliException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 1.Controller执行时出现异常，并抛出
 * 2.此类捕获被抛出的异常（@ExceptionHandler(Exception.class)）
 * 3.对捕获的异常做数据加工
 * 4.返回数据
 */
@ControllerAdvice   //AOP（这个类对全局无任何影响）
public class GlobalExceptionHandler {

    //全局异常处理
    @ExceptionHandler(Exception.class)  //载入异常处理器，并指定处理什么异常
    @ResponseBody   //当出现异常时可以返回数据（controller部分出现异常中断，需要从这个异常类返回数据）
    public R error(Exception e){
        e.printStackTrace();
        return R.error().message("执行了全局异常处理");
    }

    //特殊异常处理
    @ExceptionHandler(ArithmeticException.class)     //专门处理ArithmeticException异常
    @ResponseBody
    public R error(ArithmeticException e){
        e.printStackTrace();
        return R.error().message("执行了ArithmeticException异常处理");
    }

    //自定义异常处理
    @ExceptionHandler(GuliException.class)
    @ResponseBody
    public R error(GuliException e){
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
