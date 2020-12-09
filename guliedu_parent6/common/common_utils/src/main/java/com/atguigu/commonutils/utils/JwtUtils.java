package com.atguigu.commonutils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;


/**
 * JWT工具类
 */
public class JwtUtils {

    public static final long EXPIRE = 1000 * 60 * 60 * 24;  //经过多长时间过期
    public static final String APP_SECRET = "ukc8BDbRigUDaY6pZFfWus2jZWLPHO";   //秘钥

    /*
        根据传递的参数生成token字符串（根据实际业务逻辑传递参数个数和类型）
            注：今后在使用时只需要修改，分类和主题部分即可
     */
    public static String getJwtToken(String id, String nickname){
        String JwtToken = Jwts.builder()
                //1.头信息
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                //2.设置分类（随便起名）
                .setSubject("guli-user")
                //3.过期时间
                .setIssuedAt(new Date())    //当前时刻
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))   //过期时刻：当前时刻+时间段
                //4.设置jwt主体内容部分
                .claim("id", id)
                .claim("nickname", nickname)
                //5.jwt的签名哈希
                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();
        return JwtToken;
    }

    /*
        判断token是否有效（是否过期、是否伪造）
     */
    public static boolean checkToken(String jwtToken) {
        if(StringUtils.isEmpty(jwtToken)) return false;
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /*
        判断token是否存在与有效（根据请求头获取token信息）
     */
    public static boolean checkToken(HttpServletRequest request) {
        try {
            String jwtToken = request.getHeader("token");
            if(StringUtils.isEmpty(jwtToken)) return false;
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /*
        根据token获取主体中的token内容
     */
    public static String getMemberIdByJwtToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("token");
        if(StringUtils.isEmpty(jwtToken)) return "";
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        return (String)claims.get("id");
    }
}
