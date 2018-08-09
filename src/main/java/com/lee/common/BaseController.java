package com.lee.common;

public class BaseController {

    protected JsonRes success(){
        JsonRes jsonRes = new JsonRes();
        jsonRes.setSuccess(true);
        jsonRes.setMsg("请求成功");
        return jsonRes;
    }

    protected JsonRes success(Object data){
        JsonRes jsonRes = new JsonRes();
        jsonRes.setSuccess(true);
        jsonRes.setData(data);
        jsonRes.setMsg("请求成功");
        return jsonRes;
    }

    protected JsonRes error(){
        JsonRes jsonRes = new JsonRes();
        jsonRes.setSuccess(false);
        jsonRes.setMsg("请求失败");
        return jsonRes;
    }
}
