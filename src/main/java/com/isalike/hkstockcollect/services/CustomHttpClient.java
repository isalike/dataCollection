package com.isalike.hkstockcollect.services;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.http.HttpEntity;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomHttpClient {
    private final static int timeout = 60;// 60s timeout
    private final static Logger logger = LoggerFactory.getLogger(CustomHttpClient.class);

    private static String convertData(Map<String, String> formData) {
        return formData.entrySet().stream().map((entry) -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));
    }

    public static String doPost(String url, Map<String, String> formData) {
        return doPost(url, convertData(formData));
    }

    public static String doPost(String url, String postData) {
        return doPost(url, postData, "UTF-8");
    }

    public static String doPost(String url, String postData, String responseEncoding) {
        return doPost(url, postData, responseEncoding, "GBK");
    }

    public static String doPost(String url, String postData, String responseEncoding, String contentEncoding) {
        // TODO: return data
        String resStr = "";

        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded");
        StringEntity se = new StringEntity(postData, Charset.forName(contentEncoding));
        se.setContentEncoding(contentEncoding);
        se.setContentType("application/json");
        httpPost.setEntity(se);

        // System.out.println("URL:" + url);
        // System.out.println("formData: " + postData);

        try (CloseableHttpClient httpClient = HttpClientFactory.HttpClientPoolingManager(timeout);
             CloseableHttpResponse response = httpClient.execute(httpPost)) {

            // 解析返结果
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                resStr = EntityUtils.toString(entity, responseEncoding);
                // System.out.println(resStr);
            }
        } catch (NoHttpResponseException ex) {// failed to respond
            // do nothing
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return resStr;
    }

    public static String doGet(String url) {
        String result = "";
        try {
            result = doGetThrowEx(url);
        } catch (Exception e) {
            // do nothing
        }
        return result;
    }

    public static String doGetThrowEx(String url) throws Exception {
        String resStr = "";
        HttpGet httpget = new HttpGet(url);
        try (CloseableHttpClient httpclient = HttpClientFactory.HttpClientPoolingManager(timeout);
             CloseableHttpResponse response = httpclient.execute(httpget)) {
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                resStr = EntityUtils.toString(entity, "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return resStr;
    }

    public static String doPost(String url, String postData, ContentType contentType) {
        String resStr = "";
        HttpPost httpPost = new HttpPost(url);

        StringEntity se = new StringEntity(postData, Charset.forName("UTF-8"));
        se.setContentEncoding("UTF-8");
        se.setContentType(contentType.toString());
        httpPost.setEntity(se);

        try (CloseableHttpClient httpClient = HttpClientFactory.HttpClientPoolingManager(timeout);
             CloseableHttpResponse response = httpClient.execute(httpPost)) {

            int code = response.getStatusLine().getStatusCode();
            if (200 != code && 202 != code) {
                if (302 == code) {
                    throw new Exception(
                            "HTTP response code " + code + ", redirect " + response.getFirstHeader("Location"));
                } else {
                    throw new Exception(
                            "HTTP response code " + code + ", url" + url + ", params :" + postData + ", Content" + se);
                }
            }

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                resStr = EntityUtils.toString(entity, "UTF-8");
            }
            // System.out.println(postData);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return resStr;
    }

    // for ignore certificate validation in ssl
    public static String doPostSSL(String url, String postData, ContentType contentType) {
        // TODO: return data
        String resStr = "";

        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader(HTTP.CONTENT_TYPE, contentType.toString());
        StringEntity se = new StringEntity(postData, Charset.forName("UTF-8"));
        se.setContentEncoding("UTF-8");
        se.setContentType(contentType.toString());
        httpPost.setEntity(se);

        // System.out.println("URL:" + url);
        // System.out.println("formData: " + postData);

        try (CloseableHttpClient httpClient = HttpClientFactory.HttpClientPoolingManagerSSL(timeout);
             CloseableHttpResponse response = httpClient.execute(httpPost)) {

            // 解析返结果
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                resStr = EntityUtils.toString(entity, "UTF-8");
                // System.out.println(resStr);
            }
        } catch (NoHttpResponseException ex) {// failed to respond
            // do nothing
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return resStr;
    }
}
