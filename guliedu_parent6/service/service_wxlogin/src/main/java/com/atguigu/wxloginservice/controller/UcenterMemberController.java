package com.atguigu.wxloginservice.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.servicebase.handler.GuliException;
import com.atguigu.wxloginservice.entity.UcenterMember;
import com.atguigu.wxloginservice.service.UcenterMemberService;
import com.atguigu.wxloginservice.utils.ConstantWxPropertiesUtil;
import com.atguigu.wxloginservice.utils.HttpClientUtils;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.net.URLEncoder;
import java.util.HashMap;


/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-01-07
 */
@Controller
@RequestMapping("/api/ucenter/wx")
@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;

    /*
        扫码后通过传递过来的code，请求微信提供的固定地址
     */
    @GetMapping("callback")
    public String callback(String code, String state) {
        System.out.println("--------------------------------");
        System.out.println("--------------------------------");
        System.out.println("--------------------------------");
        System.out.println("--------------------------------");
        System.out.println("--------------------------------");

        //1.通过code值，请求微信固定的地址，得到openid和accessToken
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";

        //2.向地址里面设置参数
        baseAccessTokenUrl = String.format(
                baseAccessTokenUrl,
                ConstantWxPropertiesUtil.WX_OPEN_APP_ID,    //id
                ConstantWxPropertiesUtil.WX_OPEN_APP_SECRET,    //秘钥
                code    //传递过来的code
        );

        //3.请求这个地址，使用httpclient（因为不需要访问网址并得到界面，所以通过httpclient模拟浏览器访问过程即可）
        try {
            String accessTokenInfo = HttpClientUtils.get(baseAccessTokenUrl);
            //把获取accessTokenInfo字符串转换map集合
            Gson gson = new Gson();
            HashMap accessTokenMap = gson.fromJson(accessTokenInfo, HashMap.class);
            //从map获取数据
            String access_token = (String) accessTokenMap.get("access_token");
            String openid = (String) accessTokenMap.get("openid");

            //拿着access_token和openid再去请求微信固定地址
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";
            //设置两个参数
            baseUserInfoUrl = String.format(
                    baseUserInfoUrl,
                    access_token,
                    openid);
            //httpclient请求地址
            String userInfo = HttpClientUtils.get(baseUserInfoUrl);
            //System.out.println("userInfo:"+userInfo);
            HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
            String nickname = (String) userInfoMap.get("nickname");//微信昵称
            String headimgurl = (String) userInfoMap.get("headimgurl");//微信头像

            //把获取出来微信信息添加到数据库中
            //判断数据库表是否存在相同的微信数据openid
            UcenterMember member = memberService.getWxInfoByOpenid(openid);
            if (member == null) {//表没有相同微信数据，进行添加
                member = new UcenterMember();
                member.setOpenid(openid);
                member.setNickname(nickname);
                member.setAvatar(headimgurl);
                memberService.save(member);
            }

            //生成token，返回
            String jwtToken = JwtUtils.getJwtToken(member.getId(), nickname);
            //返回首页面中
            return "redirect:http://localhost:3000?token=" + jwtToken;

        } catch (Exception e) {
            throw new GuliException(20001, "扫描失败");
        }
    }

    /*
        生成微信登录所需二维码的方法
        即，访问，https://open.weixin.qq.com/connect/qrconnect
                ?appid=APPID
                &redirect_uri=REDIRECT_URI
                &response_type=code
                &scope=SCOPE
                &state=STATE
                #wechat_redirect
        时会自动生成所需二维码
     */
    //todo http://localhost:8150/api/ucenter/wx/callback?code=011RQjY11zSnhS1x7lY11AMaY11RQjYt&state=wxatguigu
    @GetMapping("login")
    public String login() {
        /*
            第一种：最原始的拼接字符串的方式
            第二种：利用%s表示占位符“?”，传递需要参数
         */
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +   //id
                "&redirect_uri=%s" +    //需要访问的地址，请使用urlEncode对链接进行处理
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +   //能表示意思的字符串即可
                "#wechat_redirect";

        //请使用urlEncode对链接进行处理
        String wxOpenRedirectUrl = ConstantWxPropertiesUtil.WX_OPEN_REDIRECT_URL;
        try {
            wxOpenRedirectUrl = URLEncoder.encode(wxOpenRedirectUrl,"utf-8");
        }catch(Exception e) { }

        //向%s传递参数
        String wxUrl = String.format(
                        baseUrl,
                        ConstantWxPropertiesUtil.WX_OPEN_APP_ID,
                        wxOpenRedirectUrl,
                        "wxatguigu");
        //重定向
        return "redirect:" + wxUrl;
    }
}

