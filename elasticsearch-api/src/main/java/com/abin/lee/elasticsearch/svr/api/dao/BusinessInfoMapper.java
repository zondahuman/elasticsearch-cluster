package com.abin.lee.elasticsearch.svr.api.dao;

import com.abin.lee.elasticsearch.svr.api.model.BusinessInfo;
import com.abin.lee.elasticsearch.svr.api.model.BusinessInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BusinessInfoMapper {
    int countByExample(BusinessInfoExample example);

    int deleteByExample(BusinessInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BusinessInfo record);

    int insertSelective(BusinessInfo record);

    List<BusinessInfo> selectByExample(BusinessInfoExample example);

    BusinessInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BusinessInfo record, @Param("example") BusinessInfoExample example);

    int updateByExample(@Param("record") BusinessInfo record, @Param("example") BusinessInfoExample example);

    int updateByPrimaryKeySelective(BusinessInfo record);

    int updateByPrimaryKey(BusinessInfo record);
}