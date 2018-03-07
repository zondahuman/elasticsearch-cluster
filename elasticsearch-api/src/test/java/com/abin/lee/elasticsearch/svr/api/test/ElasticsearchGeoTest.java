package com.abin.lee.elasticsearch.svr.api.test;

import com.abin.lee.elasticsearch.svr.api.enums.GeographyPosition;
import com.abin.lee.elasticsearch.svr.api.util.LantiRandom;
import com.abin.lee.elasticsearch.svr.common.util.HttpClientUtil;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abin on 2018/1/24 17:42.
 * elasticsearch-svr
 * com.abin.lee.elasticsearch.svr.api.test
 */
public class ElasticsearchGeoTest {
//    private static final String httpURL = "http://localhost:8080/business/{keyword}/{lat}/{lon}/";
    private static final String httpGeoURL = "http://localhost:8080/nearby";
    private static final String httpGeoAnalyzerURL = "http://localhost:8080/geoAnalyzer";


    @Test
    public void testGeoSearch(){
        try {
            CloseableHttpClient httpClient = HttpClientUtil.getHttpClient();
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();

            nvps.add(new BasicNameValuePair("lon", LantiRandom.randomObject(GeographyPosition.LONGITUDE)));
            nvps.add(new BasicNameValuePair("lat", LantiRandom.randomObject(GeographyPosition.LATITUDE)));
            HttpPost httpPost = new HttpPost(httpGeoURL);
//            httpPost.setHeader("Cookie", getCookie());
//            httpPost.setHeader("Cookie", "JSESSIONID=7588C522A6900BFD581AA18FDA64D347");

            httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
            System.out.println("Executing request: " + httpPost.getRequestLine());
            HttpResponse response = httpClient.execute(httpPost);
            System.out.println("----------------------------------------");
            System.out.println(response.getStatusLine());
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Test
    public void testGeoAnayzerSearch(){
        try {
            CloseableHttpClient httpClient = HttpClientUtil.getHttpClient();
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();

            nvps.add(new BasicNameValuePair("lon", LantiRandom.randomObject(GeographyPosition.LONGITUDE)));
            nvps.add(new BasicNameValuePair("lat", LantiRandom.randomObject(GeographyPosition.LATITUDE)));
            nvps.add(new BasicNameValuePair("geoName", "location"));
            HttpPost httpPost = new HttpPost(httpGeoAnalyzerURL);
//            httpPost.setHeader("Cookie", getCookie());
//            httpPost.setHeader("Cookie", "JSESSIONID=7588C522A6900BFD581AA18FDA64D347");

            httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
            System.out.println("Executing request: " + httpPost.getRequestLine());
            HttpResponse response = httpClient.execute(httpPost);
            System.out.println("----------------------------------------");
            System.out.println(response.getStatusLine());
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }






}
