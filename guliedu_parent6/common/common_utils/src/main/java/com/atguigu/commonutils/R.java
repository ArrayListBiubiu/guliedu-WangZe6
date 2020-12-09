package com.atguigu.commonutils;


import lombok.Data;
import java.util.HashMap;
import java.util.Map;


/**
 * 统一结果返回的类
 *      其中的4个属性，对应json数据的格式
 *      {
 *          "success" : 布尔,	//响应是否成功
 *          "code" : 数字,	//响应码
 *          "message" : 字符串,	//提示信息
 *          "data" : hashmap	//返回的数据，存放在键值对中
 *      }
 */
@Data
public class R {

    private Boolean success; //是否成功

    private Integer code;  //状态码

    private String message; //提示信息

    private Map<String, Object> data = new HashMap<String, Object>();  //数据

    //构造私有
    private R(){}

    //成功
    public static R ok(){
        R r = new R();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setMessage("成功");
        return r;
    }

    //失败
    public static R error(){
        R r = new R();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR);
        r.setMessage("失败");
        return r;
    }

    //链式编程
    public R success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public R message(String message){
        this.setMessage(message);
        return this;
    }

    public R code(Integer code){
        this.setCode(code);
        return this;
    }

    public R data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public R data(Map<String, Object> map){
        this.setData(map);
        return this;
    }

}