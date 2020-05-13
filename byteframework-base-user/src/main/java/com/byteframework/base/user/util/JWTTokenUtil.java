package com.byteframework.base.user.util;

import com.alibaba.fastjson.JSON;
import com.byteframework.base.user.config.JWTConfig;
import com.byteframework.base.user.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * JWT工具类
 *
 * @Author Sans
 * @CreateTime 2019/10/2 7:42
 */
@Slf4j
public class JWTTokenUtil {

    /**
     * 私有化构造器
     */
    private JWTTokenUtil() {
    }

    /**
     * 生成Token
     *
     * @Author Sans
     * @CreateTime 2019/10/2 12:16
     * @Param selfUserEntity 用户安全实体
     * @Return Token
     */
    public static String createAccessToken(User user) throws Exception {
        // 登陆成功生成JWT
        String token = Jwts.builder()
                // 放入用户名和用户ID
                .setId(user.getId() + "")
                // 主题
                .setSubject(user.getUsername())
                // 签发时间
                .setIssuedAt(new Date())
                // 签发者
                .setIssuer("sa")
                // 自定义属性 放入用户拥有权限
                .claim("authorities", JSON.toJSONString(user.getAuthorities()))
                .claim("truename", user.getTruename())
                .claim("avatar", user.getAvatar())
                .claim("roles", user.getRoles())
                // 失效时间
                .setExpiration(new Date(System.currentTimeMillis() + JWTConfig.expiration))
                // 签名算法和密钥
                .signWith(SignatureAlgorithm.RS256, RSAUtil.getPrivateKey())
                .compact();
        return token;
    }


}
