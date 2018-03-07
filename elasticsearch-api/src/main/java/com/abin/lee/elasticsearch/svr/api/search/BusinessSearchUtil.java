package com.abin.lee.elasticsearch.svr.api.search;

import com.abin.lee.elasticsearch.svr.api.vo.request.BusinessRequest;
import com.abin.lee.elasticsearch.svr.common.util.DateUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * http://blog.csdn.net/napoay/article/details/51707023
 */
@Slf4j
public final class BusinessSearchUtil {


    public static Map<String, Object> generateIndexMap(BusinessRequest businessRequest) {
        Map<String, Object> json = new HashMap<>();

        json.put("id", businessRequest.getId());
        json.put("userId", businessRequest.getUserId());
        json.put("businessName", businessRequest.getBusinessName());
        json.put("businessPrice", businessRequest.getBusinessPrice());
        json.put("privinceName", businessRequest.getProvinceName());
        json.put("cityName", businessRequest.getCityName());
        json.put("districtName", businessRequest.getDistrictName());
        json.put("field1", businessRequest.getField1());
        json.put("field2", businessRequest.getField2());
        json.put("category", businessRequest.getCategory());
        json.put("flag", businessRequest.getFlag());
        json.put("createTime", DateUtil.getYMDHMSTime(businessRequest.getCreateTime()));
        json.put("updateTime", DateUtil.getYMDHMSTime(businessRequest.getUpdateTime()));
        Map<String, Object> location = new HashMap<>();

        if (businessRequest.getLongitude() != null && businessRequest.getLatitude() != null
                && businessRequest.getLatitude() <= 90 && businessRequest.getLatitude() > -90d
                && businessRequest.getLongitude() <= 180d && businessRequest.getLongitude() >= -180d) {
            location.put("lat", businessRequest.getLatitude());
            location.put("lon", businessRequest.getLongitude());

        }
        json.put("location", location);
        log.info("json:{}", JSON.toJSON(json));

        return json;
    }



    public static XContentBuilder createIndexObject(BusinessRequest businessRequest) throws IOException {
        XContentBuilder builder = jsonBuilder()
                .startObject()
                .field("id", businessRequest.getId())
                .field("userId", businessRequest.getUserId())
                .field("businessName", businessRequest.getBusinessName())
                .field("businessPrice", businessRequest.getBusinessPrice())
                .field("privinceName", businessRequest.getProvinceName())
                .field("cityName", businessRequest.getCityName())
                .field("districtName", businessRequest.getDistrictName())
                .field("field1", businessRequest.getField1())
                .field("field2", businessRequest.getField2())
                .field("category", businessRequest.getCategory())
                .field("flag", businessRequest.getFlag())
                .field("createTime", DateUtil.getYMDHMSTime(businessRequest.getCreateTime()))
                .field("updateTime", DateUtil.getYMDHMSTime(businessRequest.getUpdateTime()))
                .latlon("location", businessRequest.getLatitude(), businessRequest.getLongitude())
                .endObject();
//                .startObject("_all").field("analyzer","ik").field("search_analyzer","ik").endObject();
        return builder;
    }


    public static String createIndexJson(BusinessRequest businessRequest) throws IOException {
        XContentBuilder builder = jsonBuilder()
                .startObject()
                .field("id", businessRequest.getId())
                .field("userId", businessRequest.getUserId())
                .field("businessName", businessRequest.getBusinessName())
                .field("businessPrice", businessRequest.getBusinessPrice())
                .field("privinceName", businessRequest.getProvinceName())
                .field("cityName", businessRequest.getCityName())
                .field("districtName", businessRequest.getDistrictName())
                .field("field1", businessRequest.getField1())
                .field("field2", businessRequest.getField2())
                .field("category", businessRequest.getCategory())
                .field("flag", businessRequest.getFlag())
                .field("createTime", DateUtil.getYMDHMSTime(businessRequest.getCreateTime()))
                .field("updateTime", DateUtil.getYMDHMSTime(businessRequest.getUpdateTime()))
                .latlon("location", businessRequest.getLatitude(), businessRequest.getLongitude())
                .endObject();
//                .startObject("_all").field("analyzer","ik").field("search_analyzer","ik").endObject();

        return builder.toString();
    }
}
