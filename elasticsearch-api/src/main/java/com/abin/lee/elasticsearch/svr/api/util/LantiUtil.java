package com.abin.lee.elasticsearch.svr.api.util;

/**
 * Created by abin on 2018/1/24 15:52.
 * elasticsearch-svr
 * com.abin.lee.elasticsearch.svr.api.util
 */

import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.net.URL;
        import java.net.URLEncoder;
        import java.util.HashMap;
        import java.util.Map;

        import org.apache.commons.lang3.StringUtils;
/**
 *经纬度
 * @author Administrator
 *
 */
public class LantiUtil {

    public static final String KEY_1 = "7d9fbeb43e975cd1e9477a7e5d5e192a";

    public static Map<String,String> getGeocoderLatitude(String address){
        BufferedReader in = null;
        try {
            address = URLEncoder.encode(address, "UTF-8");
            URL tirc = new URL("http://api.map.baidu.com/geocoder?address="+ address +"&output=json&key="+ KEY_1);


            in = new BufferedReader(new InputStreamReader(tirc.openStream(),"UTF-8"));
            String res;
            StringBuilder sb = new StringBuilder("");
            while((res = in.readLine())!=null){
                sb.append(res.trim());
            }
            String str = sb.toString();
            Map<String,String> map = null;
            if(StringUtils.isNotEmpty(str)){
                int lngStart = str.indexOf("lng\":");
                int lngEnd = str.indexOf(",\"lat");
                int latEnd = str.indexOf("},\"precise");
                if(lngStart > 0 && lngEnd > 0 && latEnd > 0){
                    String lng = str.substring(lngStart+5, lngEnd);
                    String lat = str.substring(lngEnd+7, latEnd);
                    map = new HashMap<String,String>();
                    map.put("lng", lng);
                    map.put("lat", lat);
                    return map;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void main(String args[]){
        try {
            Map<String, String> json = LantiUtil.getGeocoderLatitude("南京市栖霞区仙隐北路5号");
            System.out.println("lng : " + json.get("lng"));
            System.out.println("lat : " + json.get("lat"));
        }catch (Exception e ){
            e.printStackTrace();
        }
    }
}