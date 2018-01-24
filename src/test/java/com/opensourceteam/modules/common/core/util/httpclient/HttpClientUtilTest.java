package com.opensourceteam.modules.common.core.util.httpclient;

import com.opensourceteam.modules.common.core.vo.message.ResultBack;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

/**
 * 开发人:刘文
 * 日期:  2018/1/24.
 * 功能描述:
 */
public class HttpClientUtilTest {

    Logger logger = LoggerFactory.getLogger(HttpClientUtilTest.class);

    @Test
    public void postJSONRequest() throws Exception {

        String url = "https://myaccount.vantagefx.com.cn";
        ResultBack resultBack =HttpClientUtil.postJSONRequest(url);
      //  logger.debug("state code:{} response:{}",resultBack.getCode(),resultBack.getMessage());

    }


    @Test
    public void postJSONRequest_admin() throws Exception {

        String url = "https://admin.vantagefx.com.cn/admin/login";
        ResultBack resultBack =HttpClientUtil.postJSONRequest(url);
        //logger.debug("code:{} response:{}",resultBack.getCode(),resultBack.getMessage());

    }
}