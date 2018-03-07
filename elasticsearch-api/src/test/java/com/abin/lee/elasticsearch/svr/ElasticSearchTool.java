package com.abin.lee.elasticsearch.svr;

import com.abin.lee.elasticsearch.svr.api.enums.GeographyPosition;
import com.abin.lee.elasticsearch.svr.api.util.BusinessRandom;
import com.abin.lee.elasticsearch.svr.api.util.LantiRandom;
import com.abin.lee.elasticsearch.svr.common.util.DateUtil;
import com.abin.lee.elasticsearch.svr.common.util.HttpClientUtil;
import org.apache.commons.lang3.RandomUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abin on 2018/1/25 14:56.
 * elasticsearch-svr
 * com.abin.lee.elasticsearch.svr
 */
public class ElasticSearchTool {
    private static final String httpMapURL = "http://localhost:8080/indexMap";
    private static final String httpObjectURL = "http://localhost:8080/indexObject";
    private static final String httpJsonURL = "http://localhost:8080/indexJson";


    public static String testCreateIndex(){
        String id = (int)(Math.random()*1000000)+"";
        String usserId = (int)(Math.random()*100000000)+"";
        try {
            CloseableHttpClient httpClient = HttpClientUtil.getHttpClient();
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();

            nvps.add(new BasicNameValuePair("id", id));
            nvps.add(new BasicNameValuePair("userId", usserId));
            nvps.add(new BasicNameValuePair("longitude", LantiRandom.randomObject(GeographyPosition.LONGITUDE)));
            nvps.add(new BasicNameValuePair("latitude", LantiRandom.randomObject(GeographyPosition.LATITUDE)));
            nvps.add(new BasicNameValuePair("businessName", "清香"+ RandomUtils.nextInt(1, 10)));
            nvps.add(new BasicNameValuePair("businessPrice", RandomUtils.nextDouble(1,1000) + ""));
            nvps.add(new BasicNameValuePair("provinceName", "北京"));
            nvps.add(new BasicNameValuePair("cityName", "北京市"));
            nvps.add(new BasicNameValuePair("districtName", "朝阳区"));
            nvps.add(new BasicNameValuePair("category", "COMMON"));
            nvps.add(new BasicNameValuePair("field1", "二狗"));
            nvps.add(new BasicNameValuePair("field2", "狗蛋"));
            nvps.add(new BasicNameValuePair("flag", "PAYED"));
            nvps.add(new BasicNameValuePair("createTime", DateUtil.getYMDHMSTime()));
            nvps.add(new BasicNameValuePair("updateTime", DateUtil.getYMDHMSTime()));
            HttpPost httpPost = new HttpPost(httpMapURL);
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
        return id;
    }


    public static String testCreateObject(){
        String id = (int)(Math.random()*1000000)+"";
        String usserId = (int)(Math.random()*100000000)+"";
        try {
            CloseableHttpClient httpClient = HttpClientUtil.getHttpClient();
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("id", id));
            nvps.add(new BasicNameValuePair("userId", usserId));
            nvps.add(new BasicNameValuePair("longitude", LantiRandom.randomObject(GeographyPosition.LONGITUDE)));
            nvps.add(new BasicNameValuePair("latitude", LantiRandom.randomObject(GeographyPosition.LATITUDE)));
            nvps.add(new BasicNameValuePair("businessName", BusinessRandom.findBusiness(3)));
            nvps.add(new BasicNameValuePair("businessPrice", RandomUtils.nextDouble(1,1000) + ""));
            nvps.add(new BasicNameValuePair("provinceName", "北京"));
            nvps.add(new BasicNameValuePair("cityName", "北京市"));
            nvps.add(new BasicNameValuePair("districtName", "朝阳区"));
            nvps.add(new BasicNameValuePair("category", "COMMON"));
            nvps.add(new BasicNameValuePair("field1", "二狗"));
            nvps.add(new BasicNameValuePair("field2", "狗蛋"));
            nvps.add(new BasicNameValuePair("flag", "PAYED"));
            nvps.add(new BasicNameValuePair("createTime", DateUtil.getYMDHMSTime()));
            nvps.add(new BasicNameValuePair("updateTime", DateUtil.getYMDHMSTime()));
            HttpPost httpPost = new HttpPost(httpObjectURL);
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
        return id;
    }


    public static String testCreateObjectAll(){
        String id = (int)(Math.random()*1000000)+"";
        String usserId = (int)(Math.random()*100000000)+"";
        try {
            CloseableHttpClient httpClient = HttpClientUtil.getHttpClient();
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();

            nvps.add(new BasicNameValuePair("id", id));
            nvps.add(new BasicNameValuePair("userId", usserId));
            nvps.add(new BasicNameValuePair("longitude", LantiRandom.randomObject(GeographyPosition.LONGITUDE)));
            nvps.add(new BasicNameValuePair("latitude", LantiRandom.randomObject(GeographyPosition.LATITUDE)));
            nvps.add(new BasicNameValuePair("businessName", "清香"+ RandomUtils.nextInt(1, 10)));
            nvps.add(new BasicNameValuePair("businessPrice", RandomUtils.nextDouble(1,1000) + ""));
            nvps.add(new BasicNameValuePair("provinceName", "北京"));
            nvps.add(new BasicNameValuePair("cityName", "北京市"));
            nvps.add(new BasicNameValuePair("districtName", "朝阳区"));
            nvps.add(new BasicNameValuePair("category", "COMMON"));
            nvps.add(new BasicNameValuePair("field1", "二狗"));
            nvps.add(new BasicNameValuePair("field2", "狗蛋"));
            nvps.add(new BasicNameValuePair("flag", "PAYED"));
            nvps.add(new BasicNameValuePair("createTime", DateUtil.getYMDHMSTime()));
            nvps.add(new BasicNameValuePair("updateTime", DateUtil.getYMDHMSTime()));
            HttpPost httpPost = new HttpPost(httpObjectURL);
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
        return id;
    }












}
