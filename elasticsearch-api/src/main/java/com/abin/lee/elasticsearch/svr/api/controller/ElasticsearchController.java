package com.abin.lee.elasticsearch.svr.api.controller;

import com.abin.lee.elasticsearch.svr.api.search.SearchBusiness;
import com.abin.lee.elasticsearch.svr.api.service.BusinessService;
import com.abin.lee.elasticsearch.svr.api.vo.request.BusinessRequest;
import com.abin.lee.elasticsearch.svr.api.vo.request.SearchListVo;
import com.abin.lee.elasticsearch.svr.api.vo.request.SearchVo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by abin on 2018/1/23 19:12.
 * elasticsearch-svr
 * com.abin.lee.elasticsearch.svr.api.controller
 */
@Slf4j
@RestController
@RequestMapping("elasticsearch")
public class ElasticsearchController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BusinessService businessService;


    @RequestMapping(value = "/business/{keyword}/{lat}/{lon}/", method = RequestMethod.GET)
    public List<Map<String, Object>> businessSearch(@PathVariable("keyword") String keyword, @PathVariable("lat") Double lat
            , @PathVariable("lon") Double lon, HttpServletRequest request) {
        SearchBusiness search = new SearchBusiness();
        String page = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");
        if (page != null) {

            search.setCurrentPage(Integer.valueOf(page));
        }
        if (pageSize != null) {
            search.setSize(Integer.valueOf(pageSize));
        }

        search.setDistance(20000);
        search.setName(keyword);
        search.setUnit("km");
        search.setLatitude(lat);
        search.setLongitude(lon);
        logger.info(search.toString());
        return businessService.search(search);
    }


    @RequestMapping(value = "/search", method = {RequestMethod.GET, RequestMethod.POST})
    public List<Map<String, Object>> search(@PathVariable("keyword") String keyword, @PathVariable("lat") Double lat
            , @PathVariable("lon") Double lon, HttpServletRequest request) {
        SearchBusiness search = new SearchBusiness();
        String page = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");
        if (page != null) {

            search.setCurrentPage(Integer.valueOf(page));
        }
        if (pageSize != null) {
            search.setSize(Integer.valueOf(pageSize));
        }

        search.setDistance(20000);
        search.setName(keyword);
        search.setUnit("km");
        search.setLatitude(lat);
        search.setLongitude(lon);
        logger.info(search.toString());
        return businessService.search(search);
    }

    @RequestMapping(value = "/indexMap", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Boolean indexMap(@ModelAttribute("businessRequest") BusinessRequest businessRequest) {
        boolean flag = Boolean.FALSE;
        try {
            flag = this.businessService.createMap(businessRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    @RequestMapping(value = "/indexObject", method =  {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Boolean indexObject(@ModelAttribute("businessRequest") BusinessRequest businessRequest) {
        boolean flag = Boolean.FALSE;
        try {
            flag = this.businessService.createObject(businessRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    @RequestMapping(value = "/indexJson", method =  {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Boolean indexJson(@ModelAttribute("businessRequest") BusinessRequest businessRequest) {
        boolean flag = Boolean.FALSE;
        try {
            flag = this.businessService.createJson(businessRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    @RequestMapping(value = "/searchByKey", method =  {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public List<Map<String, Object>> searchByKey(@ModelAttribute("searchVo") SearchVo searchVo) {
        List<Map<String, Object>> result = null;
        try {
            result = this.businessService.searchByKey(searchVo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/searchPageByKey", method =  {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public List<Map<String, Object>> searchPageByKey(String key, String value, Integer pageSize, Integer pageNum) {
        List<Map<String, Object>> result = null;
        try {
            SearchVo searchVo = new SearchVo();
            searchVo.setKey(key);
            searchVo.setValue(value);
            result = this.businessService.searchByKey(searchVo, pageSize, pageNum);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/searchScollByKey", method =  {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public List<Map<String, Object>> searchScollByKey(String key, String value, Integer pageSize, Integer pageNum) {
        List<Map<String, Object>> result = null;
        try {
            SearchVo searchVo = new SearchVo();
            searchVo.setKey(key);
            searchVo.setValue(value);
            result = this.businessService.searchScollByKey(searchVo, pageSize, pageNum);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/searchByKeyList", method =  {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public List<Map<String, Object>> searchByKeyList(@ModelAttribute("searchListVoe") SearchListVo searchListVoe) {
        List<Map<String, Object>> result = null;
        try {
            result = this.businessService.searchByKeyList(searchListVoe);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    @RequestMapping(value = "/searchAll", method =  {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public List<Map<String, Object>> searchAll() {
        List<Map<String, Object>> result = null;
        try {
            result = this.businessService.searchAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    @RequestMapping(value = "/nearby", method =  {RequestMethod.GET, RequestMethod.POST})
    public void nearby(double lat, double lon) {
        try {
            this.businessService.nearby(lat, lon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequestMapping(value = "/geoAnalyzer", method =  {RequestMethod.GET, RequestMethod.POST})
    public void geoAnalyzer(String geoName,double lat, double lon) {
        try {
            this.businessService.geoAnalyzer(geoName, lat, lon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





}
