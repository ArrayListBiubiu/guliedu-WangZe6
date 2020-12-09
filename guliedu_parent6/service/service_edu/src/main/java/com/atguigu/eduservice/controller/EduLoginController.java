package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.R;
import org.springframework.web.bind.annotation.*;

//模拟登录
@RestController
@RequestMapping("/eduuser")
@CrossOrigin //跨域
public class EduLoginController {

    //login
    //{"code":20000,"data":{"token":"admin"}}
    @PostMapping("login")
    public R login() {
        return R.ok().data("token","admin");
    }

    //info
    //{"code":20000,"data":{"roles":["admin"],"name":"admin","avatar":"https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif"}}
    @GetMapping("info")
    public R info() {
        return R.ok().data("roles","admin").data("name","admin")
                .data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

}
