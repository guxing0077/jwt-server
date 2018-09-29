package com.lee.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lee.common.JsonRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理
 */
@ControllerAdvice
@ResponseBody
public class ExceptionAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

    @ExceptionHandler(value = Exception.class)
    public JsonRes handle(Exception e, HttpServletRequest request, HttpServletResponse response) {
        logger.error("exception:", e);
        JsonRes jsonRes = new JsonRes();
        jsonRes.setSuccess(false);
        jsonRes.setMsg(e.getMessage());
        return jsonRes;
    }
}