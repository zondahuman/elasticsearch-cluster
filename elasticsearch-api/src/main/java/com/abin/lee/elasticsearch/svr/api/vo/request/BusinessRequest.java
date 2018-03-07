package com.abin.lee.elasticsearch.svr.api.vo.request;

import com.abin.lee.elasticsearch.svr.api.model.BusinessInfo;
import com.abin.lee.elasticsearch.svr.common.util.DateUtil;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by abin on 2018/1/24 16:29.
 * elasticsearch-svr
 * com.abin.lee.elasticsearch.svr.api.vo.request
 */
@Setter
@Getter
public class BusinessRequest {
    private Integer id;

    private String userId;

    private Double longitude;

    private Double latitude;

    private String businessName;

    private BigDecimal businessPrice;

    private String provinceName;

    private String cityName;

    private String districtName;

    private String category;

    private String field1;

    private String field2;

    private String flag;

    private String createTime;

    private String updateTime;



    public static BusinessInfo transfer(BusinessRequest businessRequest){
        BusinessInfo businessInfo = new BusinessInfo();
        businessInfo.setId(businessRequest.getId());
        businessInfo.setBusinessName(businessInfo.getBusinessName());
        businessInfo.setBusinessPrice(businessInfo.getBusinessPrice());
        businessInfo.setCategory(businessInfo.getCategory());
        businessInfo.setCityName(businessInfo.getCityName());
        businessInfo.setDistrictName(businessInfo.getDistrictName());
        businessInfo.setField1(businessInfo.getField1());
        businessInfo.setField2(businessInfo.getField2());
        businessInfo.setFlag(businessInfo.getFlag());
        businessInfo.setLatitude(businessInfo.getLatitude());
        businessInfo.setLongitude(businessInfo.getLongitude());
        businessInfo.setProvinceName(businessInfo.getProvinceName());
        businessInfo.setUserId(businessInfo.getUserId());
        businessInfo.setVersion(businessInfo.getVersion());
//        businessInfo.setCreateTime(DateUtil.getYMDHMSTime(businessRequest.getC()));
//        businessInfo.set
        return businessInfo;
    }

}
