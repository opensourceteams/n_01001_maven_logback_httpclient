### maven httpclient

## 功能
- maven  httpClient 发送url请求，返回响应状态码，响应内容 
- 单元测试
- url : https://github.com/opensourceteams/n_01001_maven_logback_httpclient

# Editor.md

### 测试类
```java
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
```

### 工具类
```java
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

```

### pom.xml
```java
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>com.opensourceteam</groupId>
  <artifactId>maven-project-logback</artifactId>
  <version>1.0-SNAPSHOT</version>
  <packaging>jar</packaging>

  <name>${artifactId}</name>
  <url>http://maven.apache.org</url>

  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.source>1.8</maven.compiler.source>
    <maven.compiler.target>1.8</maven.compiler.target>
    <jdk.version>1.8</jdk.version>
    <logback.version>1.2.3</logback.version>
  </properties>


  <dependencies>

    <!-- logback 依赖的包-->
    <dependency>
      <groupId>ch.qos.logback</groupId>
      <artifactId>logback-classic</artifactId>
      <version>${logback.version}</version>
    </dependency>

    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.12</version>
      <scope>test</scope>
    </dependency>


    <dependency>
      <groupId>commons-httpclient</groupId>
      <artifactId>commons-httpclient</artifactId>
      <version>3.1</version>
    </dependency>

    <dependency>
      <groupId>com.alibaba</groupId>
      <artifactId>fastjson</artifactId>
      <version>1.2.40</version>
    </dependency>
    <dependency>
      <groupId>org.apache.commons</groupId>
      <artifactId>commons-lang3</artifactId>
      <version>3.7</version>
    </dependency>

  </dependencies>

  <build>
    <sourceDirectory>src/main/java</sourceDirectory>
    <testSourceDirectory>src/test/java</testSourceDirectory>
    <resources>
      <resource>
        <directory>src/main/resources</directory>
      </resource>
    </resources>
  </build>
</project>

```
