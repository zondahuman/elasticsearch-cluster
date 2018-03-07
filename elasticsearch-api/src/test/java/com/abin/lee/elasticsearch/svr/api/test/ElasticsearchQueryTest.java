package com.abin.lee.elasticsearch.svr.api.test;

import com.abin.lee.elasticsearch.svr.api.enums.GeographyPosition;
import com.abin.lee.elasticsearch.svr.api.util.LantiRandom;
import com.abin.lee.elasticsearch.svr.common.util.DateUtil;
import com.abin.lee.elasticsearch.svr.common.util.HttpClientUtil;
import org.apache.commons.lang3.RandomUtils;
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
public class ElasticsearchQueryTest {
//    private static final String httpURL = "http://localhost:8080/business/{keyword}/{lat}/{lon}/";
    private static final String httpURL = "http://localhost:8080/search";
    private static final String httpSearchByKeyURL = "http://localhost:8080/searchByKey";
    private static final String httpSearchPageByKeyURL = "http://localhost:8080/searchPageByKey";
    private static final String httpSearchScollByKeyURL = "http://localhost:8080/searchScollByKey";
    private static final String httpSearchByKeyListURL = "http://localhost:8080/searchByKeyList";
    private static final String httpSearchAllURL = "http://localhost:8080/searchAll";
//    private String httpURL = null;

    @Test
    public void testIndexSearch(){
        try {
            CloseableHttpClient httpClient = HttpClientUtil.getHttpClient();
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();

            nvps.add(new BasicNameValuePair("lon", LantiRandom.randomObject(GeographyPosition.LONGITUDE)));
            nvps.add(new BasicNameValuePair("lat", LantiRandom.randomObject(GeographyPosition.LATITUDE)));
            nvps.add(new BasicNameValuePair("keyword", "清香"));
            HttpPost httpPost = new HttpPost(httpURL);
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
    public void testIndexQuery(){
        try {
            CloseableHttpClient httpClient = HttpClientUtil.getHttpClient();
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();

            nvps.add(new BasicNameValuePair("lon", LantiRandom.randomObject(GeographyPosition.LONGITUDE)));
            nvps.add(new BasicNameValuePair("lat", LantiRandom.randomObject(GeographyPosition.LATITUDE)));
            nvps.add(new BasicNameValuePair("keyword", "清香"));
            String keyword = "清香";
            String lon = LantiRandom.randomObject(GeographyPosition.LONGITUDE);
            String lat = LantiRandom.randomObject(GeographyPosition.LATITUDE);

            String httpQueryURL = "http://localhost:8080/business/"+keyword+"/"+lat+"/"+lon+"/";
            HttpGet httpGet = new HttpGet(httpQueryURL);
//            httpPost.setHeader("Cookie", getCookie());
//            httpPost.setHeader("Cookie", "JSESSIONID=7588C522A6900BFD581AA18FDA64D347");

//            httpGet.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
            System.out.println("Executing request: " + httpGet.getRequestLine());
            HttpResponse response = httpClient.execute(httpGet);
            System.out.println("----------------------------------------");
            System.out.println(response.getStatusLine());
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }



    @Test
    public void testSearchByKey(){
        try {
            CloseableHttpClient httpClient = HttpClientUtil.getHttpClient();
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();

            nvps.add(new BasicNameValuePair("key", "businessName"));
            nvps.add(new BasicNameValuePair("value", "海底"));
            HttpPost httpPost = new HttpPost(httpSearchByKeyURL);

            httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
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
    public void testSearchPageByKey(){
        try {
            CloseableHttpClient httpClient = HttpClientUtil.getHttpClient();
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();

            nvps.add(new BasicNameValuePair("key", "businessName"));
            nvps.add(new BasicNameValuePair("value", "海底"));
            nvps.add(new BasicNameValuePair("pageSize", "10"));
            nvps.add(new BasicNameValuePair("pageNum", "1"));
            HttpPost httpPost = new HttpPost(httpSearchPageByKeyURL);

            httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
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
    public void testSearchScollByKey(){
        try {
            CloseableHttpClient httpClient = HttpClientUtil.getHttpClient();
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();

            nvps.add(new BasicNameValuePair("key", "businessName"));
            nvps.add(new BasicNameValuePair("value", "海底"));
            nvps.add(new BasicNameValuePair("pageSize", "10"));
            nvps.add(new BasicNameValuePair("pageNum", "1"));
            HttpPost httpPost = new HttpPost(httpSearchScollByKeyURL);

            httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
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
    public void testSearchByKeyList(){
        try {
            CloseableHttpClient httpClient = HttpClientUtil.getHttpClient();
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();

            nvps.add(new BasicNameValuePair("key", "businessName"));
            nvps.add(new BasicNameValuePair("value", "清香"));
            HttpPost httpPost = new HttpPost(httpSearchByKeyListURL);

            httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
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
    public void testSearchAll(){
        try {
            CloseableHttpClient httpClient = HttpClientUtil.getHttpClient();
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();

            nvps.add(new BasicNameValuePair("lon", LantiRandom.randomObject(GeographyPosition.LONGITUDE)));
            nvps.add(new BasicNameValuePair("lat", LantiRandom.randomObject(GeographyPosition.LATITUDE)));
            nvps.add(new BasicNameValuePair("keyword", "清香"));
            HttpPost httpPost = new HttpPost(httpSearchAllURL);
//            httpPost.setHeader("Cookie", getCookie());
//            httpPost.setHeader("Cookie", "JSESSIONID=7588C522A6900BFD581AA18FDA64D347");

            httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
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
