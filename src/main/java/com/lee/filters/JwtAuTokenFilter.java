package com.lee.filters;

import java.io.IOException;
import java.util.Map;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lee.common.JsonRes;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.filter.OncePerRequestFilter;
import com.lee.exception.BusinessException;
import com.lee.utils.JwtHelper;

public class JwtAuTokenFilter extends OncePerRequestFilter{

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String authToken = request.getHeader("tokenKey");
            if(StringUtils.isBlank(authToken)){
                throw new BusinessException("missing or invalid token");
            }
            //校验token是否存在
            Map<String, String> resultMap = JwtHelper.verifyToken(authToken);
            if(resultMap.isEmpty()){
                throw new BusinessException("missing or invalid token");
            }
            //获取名称
            String userName = resultMap.get("userName");
            if(StringUtils.isBlank(userName)){
                throw new BusinessException("missing or invalid token");
            }
            //校验redis的token
            String token = redisTemplate.opsForValue().get(userName);
            if(StringUtils.isBlank(token)||!authToken.equals(token)){
                throw new BusinessException("missing or invalid token");
            }
        } catch (BusinessException e) {
            logger.error("errmsg:", e);
        }
        filterChain.doFilter(request, response);
    }
}
