package com.lee.filters;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lee.common.JsonRes;
import com.lee.utils.JsonUtil;
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
                printRes(response);
                return;
            }
            //校验token是否存在
            Map<String, String> resultMap = JwtHelper.verifyToken(authToken);
            if(resultMap.isEmpty()){
                printRes(response);
                return;
            }
            //获取名称
            String userName = resultMap.get("userName");
            if(StringUtils.isBlank(userName)){
                printRes(response);
                return;
            }
            //校验redis的token
            String token = redisTemplate.opsForValue().get(userName);
            if(StringUtils.isBlank(token)||!authToken.equals(token)){
                printRes(response);
                return;
            }
            filterChain.doFilter(request, response);
        } catch (BusinessException e) {
            logger.error("errmsg:", e);
            printRes(response);
        }
    }

    /**
     * 没有token或者token不正确，打印错误信息
     * @param response
     * @throws IOException
     */
    private void printRes(HttpServletResponse response) throws IOException {
        JsonRes jsonRes = new JsonRes();
        jsonRes.setSuccess(false);
        jsonRes.setMsg("missing or invalid token");
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter pw = response.getWriter();
        pw.write(JsonUtil.objectToJson(jsonRes));
        pw.close();
    }
}
