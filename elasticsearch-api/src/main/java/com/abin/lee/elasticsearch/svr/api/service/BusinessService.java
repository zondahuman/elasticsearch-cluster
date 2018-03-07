package com.abin.lee.elasticsearch.svr.api.service;

import com.abin.lee.elasticsearch.svr.api.search.SearchBusiness;
import com.abin.lee.elasticsearch.svr.api.vo.request.BusinessRequest;
import com.abin.lee.elasticsearch.svr.api.vo.request.SearchListVo;
import com.abin.lee.elasticsearch.svr.api.vo.request.SearchVo;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by abin on 2018/1/24 15:14.
 * elasticsearch-svr
 * com.abin.lee.elasticsearch.svr.api.service
 */
public interface BusinessService {


    void createIndex();

    void createMapping();

    List<Map<String, Object>> search(SearchBusiness search);

    Boolean createMap(BusinessRequest businessRequest);

    Boolean createObject(BusinessRequest businessRequest) throws IOException;

    Boolean createJson(BusinessRequest businessRequest) throws IOException;

    List<Map<String, Object>> searchByKey(SearchVo searchVo);
    List<Map<String, Object>> searchByKeyList(SearchListVo searchListVo);
    List<Map<String, Object>> searchAll();

    List<Map<String, Object>> searchByKey(SearchVo searchVo, Integer pageSize, Integer pageNum);
    List<Map<String, Object>> searchScollByKey(SearchVo searchVo, Integer pageSize, Integer pageNum);


    void nearby(double lat, double lon);

    void geoAnalyzer(String geoName, double lat,double lon);

}
