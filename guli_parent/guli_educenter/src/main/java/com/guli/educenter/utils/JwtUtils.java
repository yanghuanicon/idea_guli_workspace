package com.guli.educenter.utils;


import com.guli.educenter.entity.UcenterMember;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * jwt工具类
 */
public class JwtUtils {

    public static final String SUBJECT = "guli";
    //秘钥
    public static final String APPSECRET = "guli";

    public static final long EXPIRE = 1000 * 60 * 30;  //过期时间，毫秒，30分钟

    /**
     * 生成jwt token字符串
     *
     * @param member
     * @return
     */
    public static String geneJsonWebToken(UcenterMember member) {

        if (member == null || StringUtils.isEmpty(member.getId())
                || StringUtils.isEmpty(member.getNickname())
                || StringUtils.isEmpty(member.getAvatar())) {
            return null;
        }
        String token = Jwts.builder().setSubject(SUBJECT)
                .claim("id", member.getId())
                .claim("nickname", member.getNickname())
                .claim("avatar", member.getAvatar())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .signWith(SignatureAlgorithm.HS256, APPSECRET).compact();
        return token;
    }


    /**
     * 校验jwt token
     *
     * @param token
     * @return
     */
    public static Claims checkJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(APPSECRET).parseClaimsJws(token).getBody();
        return claims;
    }



    //下面测试的方法
    public static void main(String[] args){
        UcenterMember member = new UcenterMember();
        member.setId("11");
        member.setNickname("testtest");
        member.setAvatar("imgssss");

        String token = geneJsonWebToken(member);
        System.out.println(token);

        //从token获取添加用户信息
        Claims claims = checkJWT(token);
        String id = (String)claims.get("id");
        String nickname = (String)claims.get("nickname");
        String avatar = (String)claims.get("avatar");
        System.out.println(id+":"+nickname+":"+avatar);
    }
}