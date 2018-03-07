package com.abin.lee.elasticsearch.svr.api.service.impl;

import com.abin.lee.elasticsearch.svr.api.constants.SearchConstants;
import com.abin.lee.elasticsearch.svr.api.search.BusinessSearchUtil;
import com.abin.lee.elasticsearch.svr.api.search.ElasticTransportClient;
import com.abin.lee.elasticsearch.svr.api.search.SearchBusiness;
import com.abin.lee.elasticsearch.svr.api.service.BusinessService;
import com.abin.lee.elasticsearch.svr.api.vo.request.BusinessRequest;
import com.abin.lee.elasticsearch.svr.api.vo.request.SearchListVo;
import com.abin.lee.elasticsearch.svr.api.vo.request.SearchVo;
import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.geo.GeoDistance;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHitField;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by abin on 2018/1/24 15:14.
 * elasticsearch-svr
 * com.abin.lee.elasticsearch.svr.api.service
 * http://blog.csdn.net/cweeyii/article/details/71076008
 * https://www.cnblogs.com/wenbronk/p/6432990.html
 * https://www.cnblogs.com/leixingzhi7/p/6903938.html
 * https://www.elastic.co/guide/cn/elasticsearch/guide/current/_deleting_an_index.html
 * http://blog.csdn.net/napoay/article/details/73100110?locationNum=9&fps=1
 * http://blog.csdn.net/top_code/article/details/50767138
 * 排序：
 * http://study121007.iteye.com/blog/2296556
 * http://blog.csdn.net/changong28/article/details/38445805#comments
 * Geo
 * http://blog.csdn.net/luosai19910103/article/details/53729783
 * http://blog.csdn.net/loveisnull/article/details/45914115
 * https://www.elastic.co/guide/en/elasticsearch/reference/5.5/query-dsl-geo-distance-query.html
 */
@Service
public class BusinessServiceImpl implements BusinessService {

    @Autowired
    private ElasticTransportClient elasticTransportClient;


    @Override
    public void createIndex() {

    }

    @Override
    public void createMapping() {

    }


    @Override
    public Boolean createMap(BusinessRequest businessRequest) {
        TransportClient transportClient = elasticTransportClient.getClient();

        IndexResponse response = transportClient.prepareIndex(SearchConstants.SEARCH_INDEX, SearchConstants.SEARCH_TYPE, businessRequest.getId() + "")
                .setSource(BusinessSearchUtil.generateIndexMap(businessRequest))
                .get();
        RestStatus status = response.status();
        System.out.println(JSON.toJSON(response));
        return status.getStatus() == RestStatus.CREATED.getStatus();
    }


    @Override
    public Boolean createObject(BusinessRequest businessRequest) throws IOException {
        TransportClient transportClient = elasticTransportClient.getClient();

        IndexResponse response = transportClient.prepareIndex(SearchConstants.SEARCH_INDEX, SearchConstants.SEARCH_TYPE, businessRequest.getId() + "")
                .setSource(BusinessSearchUtil.createIndexObject(businessRequest))
                .get();
        RestStatus status = response.status();
        System.out.println(JSON.toJSON(response));
        return status.getStatus() == RestStatus.CREATED.getStatus();
    }

    @Override
    public Boolean createJson(BusinessRequest businessRequest) throws IOException {
        TransportClient transportClient = elasticTransportClient.getClient();

        IndexResponse response = transportClient.prepareIndex(SearchConstants.SEARCH_INDEX, SearchConstants.SEARCH_TYPE, businessRequest.getId() + "")
                .setSource(BusinessSearchUtil.createIndexJson(businessRequest))
                .get();
        RestStatus status = response.status();
        System.out.println(JSON.toJSON(response));
        return status.getStatus() == RestStatus.CREATED.getStatus();
    }

    @Override
    public List<Map<String, Object>> search(SearchBusiness search) {
        List<Map<String, Object>> result = new ArrayList<>();
        TransportClient client = elasticTransportClient.getClient();

        Map<String, Object> scriptMap = new HashMap<>();
        scriptMap.put("time", new Date().getTime());

        QueryBuilder queryBuilder = QueryBuilders
                .boolQuery()
                .must(QueryBuilders.matchQuery("name", search.getName()))
                .filter(QueryBuilders.geoDistanceQuery("location").distance(search.getDistance() + search.getUnit()).point(search.getLatitude(), search.getLongitude()));
        SearchResponse searchResponse = client
                .prepareSearch(SearchConstants.SEARCH_INDEX)
                .addSort(SortBuilders.geoDistanceSort("location", new GeoPoint(search.getLatitude(), search.getLongitude())).sortMode(SortMode.MIN).unit(DistanceUnit.KILOMETERS).order(SortOrder.DESC))
                .addSort(SortBuilders.scriptSort(new Script(ScriptType.INLINE, "painless", "(params.time>doc['startTime'].value && params.time < doc['endTime'].value)?doc['fx'].value:doc['tsfx'].value", scriptMap), ScriptSortBuilder.ScriptSortType.NUMBER))
                .setQuery(queryBuilder)
                .execute()
                .actionGet();
        SearchHits searchHits = searchResponse.getHits();
        SearchHit[] hits = searchHits.getHits();
        for (SearchHit searchHit : hits) {
            Object[] sortValues = searchHit.getSortValues();
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
            sourceAsMap.put("distance", sortValues[0]);
            sourceAsMap.put("currentV", sortValues[1]);
            result.add(sourceAsMap);
        }

        return result;
    }




    @Override
    public List<Map<String, Object>> searchByKey(SearchVo searchVo) {
        List<Map<String, Object>> result = null;
//        QueryBuilder queryBuilder = QueryBuilders.spanTermQuery(searchVo.getKey(), searchVo.getValue());
//        result = searchFunction(queryBuilder);
        BoolQueryBuilder queryBuilder = QueryBuilders
                .boolQuery()
                .must(QueryBuilders.matchQuery(searchVo.getKey(), searchVo.getValue()));//消费标签
        result = searchFunctions(queryBuilder);
        return result;
    }

    @Override
    public List<Map<String, Object>> searchByKey(SearchVo searchVo, Integer pageSize, Integer pageNum) {
        List<Map<String, Object>> result = null;
//        QueryBuilder queryBuilder = QueryBuilders.spanTermQuery(searchVo.getKey(), searchVo.getValue());
//        result = searchFunction(queryBuilder);
        BoolQueryBuilder queryBuilder = QueryBuilders
                .boolQuery()
                .must(QueryBuilders.matchQuery(searchVo.getKey(), searchVo.getValue()));//消费标签
        result = searchFunctions(queryBuilder, pageSize, pageNum);
        return result;
    }

    @Override
    public List<Map<String, Object>> searchScollByKey(SearchVo searchVo, Integer pageSize, Integer pageNum) {
        List<Map<String, Object>> result = null;
        BoolQueryBuilder queryBuilder = QueryBuilders
                .boolQuery()
                .must(QueryBuilders.matchQuery(searchVo.getKey(), searchVo.getValue()));//消费标签
        result = searchFunction(queryBuilder, pageSize, pageNum);
        return result;
    }

    @Override
    public List<Map<String, Object>> searchByKeyList(SearchListVo searchListVo) {
        List<Map<String, Object>> result = null;
        QueryBuilder queryBuilder = QueryBuilders.termsQuery(searchListVo.getKey(), searchListVo.getObjectList());
        result = searchFunctions(queryBuilder);
        return result;
    }

    @Override
    public List<Map<String, Object>> searchAll() {
        List<Map<String, Object>> result = null;
        QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
        result = searchFunctions(queryBuilder);
        return result;
    }

    private List<Map<String, Object>> searchFunction(QueryBuilder queryBuilder) {
        List<Map<String, Object>> result = new ArrayList<>();
        SearchResponse response = elasticTransportClient.getClient().prepareSearch(SearchConstants.SEARCH_INDEX)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setScroll(new TimeValue(60000000))
                .setQuery(queryBuilder)
                .setSize(100).execute().actionGet();

        response = elasticTransportClient.getClient().prepareSearchScroll(response.getScrollId())
                .setScroll(new TimeValue(6000000)).execute().actionGet();
        for (SearchHit hit : response.getHits()) {
                result.add(hit.getSourceAsMap());
        }
        return result;
    }

    private List<Map<String, Object>> searchFunction(QueryBuilder queryBuilder, Integer pageSize, Integer pageNum) {
        List<Map<String, Object>> result = new ArrayList<>();
        SearchResponse response = elasticTransportClient.getClient().prepareSearch(SearchConstants.SEARCH_INDEX)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setScroll(new TimeValue(60000000))
                .setQuery(queryBuilder)
                .setSize(100).execute().actionGet();

        long totalCount = response.getHits().getTotalHits();
        int page=(int)totalCount/(pageNum*pageSize);//计算总页数,每次搜索数量为分片数*设置的size大小
        System.out.println(totalCount);
        for (int i = 0; i <= page; i++) {
            //再次发送请求,并使用上次搜索结果的ScrollId
            response = elasticTransportClient.getClient().prepareSearchScroll(response.getScrollId())
                    .setScroll(new TimeValue(6000000)).execute().actionGet();
            for (SearchHit hit : response.getHits()) {
                result.add(hit.getSourceAsMap());
            }
        }
        return result;
    }



    private List<Map<String, Object>> searchFunctions(QueryBuilder queryBuilder) {
        List<Map<String, Object>> result = new ArrayList<>();
        // 加入查询中
        SearchResponse response = elasticTransportClient.getClient().prepareSearch(SearchConstants.SEARCH_INDEX)
                .setQuery(queryBuilder)
                .execute().actionGet();

        // 遍历结果, 获取高亮片段
        SearchHits searchHits = response.getHits();
        SearchHit[] hits = searchHits.getHits();
        for(SearchHit hit:searchHits){
            System.out.println("getSourceAsString="+hit.getSourceAsString());

            System.out.println("遍历高亮集合，打印高亮片段:");
            Map<String, SearchHitField> fiels = hit.getFields();
            System.out.println("fiels="+fiels);
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            System.out.println("sourceAsMap="+sourceAsMap);
            result.add(sourceAsMap);
        }
        return result;
    }


    private List<Map<String, Object>> searchFunctions(QueryBuilder queryBuilder, Integer pageSize, Integer pageNum) {
        List<Map<String, Object>> result = new ArrayList<>();
        // 加入查询中
        SearchResponse response = elasticTransportClient.getClient().prepareSearch(SearchConstants.SEARCH_INDEX)
                .setQuery(queryBuilder)
                .setFrom(pageNum)
                .setSize(pageSize)
                .execute().actionGet();
        // 遍历结果
        SearchHits searchHits = response.getHits();
        for(SearchHit hit:searchHits){
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            result.add(sourceAsMap);
        }
        return result;
    }



    // 获取附近的城市
    public void nearby(double lat, double lon) {
        SearchRequestBuilder srb = this.elasticTransportClient.getClient().prepareSearch(SearchConstants.SEARCH_INDEX).setTypes(SearchConstants.SEARCH_TYPE);
        srb.setFrom(0).setSize(1000);//1000人
        // lon, lat位于谦的坐标，查询距离于谦1米到1000米
//        FilterBuilder builder = geoDistanceRangeFilter("location").point(lon, lat).from("1m").to("100m").optimizeBbox("memory").geoDistance(GeoDistance.PLANE);
        GeoDistanceQueryBuilder location1 = QueryBuilders.geoDistanceQuery("location").point(lat, lon).distance(100, DistanceUnit.METERS);
        srb.setPostFilter(location1);
        // 获取距离多少公里 这个才是获取点与点之间的距离的
        GeoDistanceSortBuilder sort = SortBuilders.geoDistanceSort("location", lat, lon);
        sort.unit(DistanceUnit.METERS);
        sort.order(SortOrder.ASC);
        sort.point(lat, lon);
        srb.addSort(sort);
        System.out.println("lql: "+srb.toString());
        SearchResponse searchResponse = srb.execute().actionGet();

        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHists = hits.getHits();
        // 搜索耗时
        Float usetime = searchResponse.getTookInMillis() / 1000f;
        System.out.println("于谦附近的人(" + hits.getTotalHits() + "个)，耗时(" + usetime + "秒)：");
        for (SearchHit hit : searchHists) {
            String name = (String) hit.getSource().get("businessName");
            Map<String, Double> location = (Map<String, Double>) hit.getSource().get("location");
            // 获取距离值，并保留两位小数点
            BigDecimal geoDis = new BigDecimal((Double) hit.getSortValues()[0]);
            Map<String, Object> hitMap = hit.getSource();
            // 在创建MAPPING的时候，属性名的不可为geoDistance。
            hitMap.put("geoDistance", geoDis.setScale(0, BigDecimal.ROUND_HALF_DOWN));
            System.out.println(name + "的坐标：" + location + "他距离于谦" + hit.getSource().get("geoDistance") + DistanceUnit.METERS.toString());
        }
    }




    public void geoAnalyzer(String geoName,double lat,double lon){
        SearchRequestBuilder srb = this.elasticTransportClient.getClient().prepareSearch(SearchConstants.SEARCH_INDEX)
                .setTypes(SearchConstants.SEARCH_TYPE);
        //这个是查询出附近东西。
        GeoDistanceQueryBuilder fb = QueryBuilders
                .geoDistanceQuery(geoName)
                .point(lat, lon)
                .distance(50, DistanceUnit.KILOMETERS)// KILOMETERS为空里的意思。2公里附近的数据
                .optimizeBbox("memory") // Can be also "indexed" or "none"
                .geoDistance(GeoDistance.ARC);

        srb.setPostFilter(fb);


        //获取距离多少公里 这个才是获取点与点之间的距离的
        GeoDistanceSortBuilder sort  = new GeoDistanceSortBuilder(geoName, lat, lon);
        sort.unit(DistanceUnit.KILOMETERS);
        sort.order(SortOrder.ASC);
        sort.point(lat, lon);
        sort.geoDistance(GeoDistance.ARC);

        srb.addSort(sort);
        SearchResponse searchResponse = srb.execute().actionGet();

        for (SearchHit hit : searchResponse.getHits().getHits()) {
            System.out.println("hit.getSource()="+hit.getSource());
            //获取距离值，并保留两位小数点
            BigDecimal geoDis=new BigDecimal((double)hit.getSortValues()[0]);
            Map<String,Object> hitMap=hit.getSource();
            //在创建MAPPING的时候，属性名的不可为geoDistance。
            hitMap.put("geoDistance", geoDis.setScale(2, BigDecimal.ROUND_HALF_DOWN));

            System.out.println(hit.getSource());

        }

    }




}
