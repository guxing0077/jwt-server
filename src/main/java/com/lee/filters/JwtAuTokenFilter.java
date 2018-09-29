package com.lee.filters;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import com.lee.common.JsonRes;
import com.lee.exception.BusinessException;
import com.lee.utils.JsonUtil;
import com.lee.utils.JwtHelper;

public class JwtAuTokenFilter extends OncePerRequestFilter{

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String uri = request.getRequestURI();
        try {
            String authToken = request.getHeader("tokenKey");
            if(StringUtils.isBlank(authToken)){
				printRes(response, "missing or invalid token");
                return;
            }
            //校验token是否存在
            Map<String, String> resultMap = JwtHelper.verifyToken(authToken);
            if(resultMap.isEmpty()){
				printRes(response, "missing or invalid token");
                return;
            }
            //获取名称
            String userName = resultMap.get("userName");
            if(StringUtils.isBlank(userName)){
				printRes(response, "missing or invalid token");
                return;
            }
            //校验redis的token
            String token = redisTemplate.opsForValue().get(userName);
            if(StringUtils.isBlank(token)||!authToken.equals(token)){
				printRes(response, "missing or invalid token");
                return;
            }
			//授权校验
			List<String> perms = JsonUtil.jsonToList(resultMap.get("perms").toString(), String.class);
			if (!perms.contains(uri)) {
				printRes(response, "permission denied");
				return;
			}
            filterChain.doFilter(request, response);
        } catch (BusinessException e) {
            logger.error("errmsg:", e);
			printRes(response, "missing or invalid token");
        }
    }

    /**
	 * 没有token或者token不正确，打印错误信息
	 * @param response 响应
	 * @param msg 输出信息
	 * @throws IOException
	 */
	private void printRes(HttpServletResponse response, String msg) throws IOException {
        JsonRes jsonRes = new JsonRes();
        jsonRes.setSuccess(false);
		jsonRes.setMsg(msg);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter pw = response.getWriter();
        pw.write(JsonUtil.objectToJson(jsonRes));
        pw.close();
    }
}
