package com.byteframework.base.user.security.jwt;

import com.alibaba.fastjson.JSONObject;
import com.byteframework.base.user.config.JWTConfig;
import com.byteframework.base.user.util.RSAUtil;
import com.byteframework.base.user.util.ResultUtil;
import com.byteframework.base.user.domain.User;
import com.byteframework.commons.constant.ErrorCodeEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * JWT接口请求校验拦截器
 * 请求接口时会进入这里验证Token是否合法和过期
 *
 * @Author Sans
 * @CreateTime 2019/10/5 16:41
 */
@Slf4j
public class JWTAuthenticationTokenFilter extends BasicAuthenticationFilter {

    public JWTAuthenticationTokenFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取请求头中JWT的Token
        String tokenHeader = request.getHeader(JWTConfig.tokenHeader);
        if (null != tokenHeader && tokenHeader.startsWith(JWTConfig.tokenPrefix)) {
            try {
                // 截取JWT前缀
                String token = tokenHeader.replace(JWTConfig.tokenPrefix, "");
                // 解析JWT
                Claims claims = Jwts.parser()
                        .setSigningKey(RSAUtil.getPublicKey())
                        .parseClaimsJws(token)
                        .getBody();
                // 获取用户名
                String username = claims.getSubject();
                String userId = claims.getId();
                if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(userId)) {
                    // 获取角色
                    List<GrantedAuthority> authorities = new ArrayList<>();
                    List<String> roles = new ArrayList<>();
                    String authority = claims.get("authorities").toString();
                    if (!StringUtils.isEmpty(authority)) {
                        List<Map<String, String>> authorityMap = JSONObject.parseObject(authority, List.class);
                        for (Map<String, String> role : authorityMap) {
                            if (!StringUtils.isEmpty(role)) {
                                authorities.add(new SimpleGrantedAuthority(role.get("authority")));
                                roles.add(role.get("authority"));
                            }
                        }
                    }
                    //组装参数
                    User user = new User();
                    user.setUsername(claims.getSubject());
                    user.setId(Long.parseLong(claims.getId()));
                    user.setAuthorities(authorities);
                    user.setTruename(claims.get("truename").toString());
                    user.setAvatar(claims.get("avatar").toString());
                    user.setRoles(roles);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, userId, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (ExpiredJwtException e) {
                log.info("Token过期");
                ResultUtil.responseJson(response, ResultUtil.resultCode(ErrorCodeEnum.ERROR_A0311.getCode(), ErrorCodeEnum.ERROR_A0311.getMessage()));
                return;
            } catch (Exception e) {
                e.printStackTrace();
                log.info("Token无效");
                ResultUtil.responseJson(response, ResultUtil.resultCode(ErrorCodeEnum.ERROR_A0312.getCode(), ErrorCodeEnum.ERROR_A0312.getMessage()));
                return;
            }
        }
        filterChain.doFilter(request, response);
        return;
    }
}
