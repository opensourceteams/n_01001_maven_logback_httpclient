package com.opensourceteam.modules.common.core.util.httpclient;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.opensourceteam.modules.common.core.vo.message.ResultBack;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.StatusLine;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * 开发人:刘文
 * 日期:  2017/12/20.
 * 功能描述:
 */
public class HttpClientUtil {

    static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class) ;

    public static ResultBack postJSONRequest(String url) throws IOException {
        return postJSONRequest(null,url);
    }
    public static ResultBack postJSONRequest(String json,String url) throws IOException {
        HttpClient httpclient = new HttpClient();
        httpclient.getParams().setContentCharset("UTF-8");
        PostMethod post = new PostMethod( url);

        //post.setRequestEntity(new StringRequestEntity(json,"application/json","UTF-8"));
        if(StringUtils.isEmpty(json)){
            json = "{}";
        }else{
            logger.debug("[HttpClientUtil postJSONRequest] json:{}" ,json);
        }
        JSONObject jsonObject = JSON.parseObject(json);
        for(Map.Entry<String, Object> entry : jsonObject.entrySet()){
            post.setParameter(entry.getKey(),entry.getValue()+"");
        }
        httpclient.executeMethod(post);
        StatusLine statusLine = post.getStatusLine();
        String responseBody = post.getResponseBodyAsString();

        post.releaseConnection();

        logger.debug("[HttpClientUtil postJSONRequest] response code:{}, response: {}" ,statusLine.getStatusCode(),responseBody);

        ResultBack resultBack =new ResultBack(true,String.valueOf(statusLine.getStatusCode()),responseBody);

        return resultBack;
    }
}
