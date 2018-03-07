package com.abin.lee.elasticsearch.svr.api.vo.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by abin on 2018/1/25 15:56.
 * elasticsearch-svr
 * com.abin.lee.elasticsearch.svr.api.vo.request
 */
@Getter
@Setter
public class SearchListVo {

    private String key;

    private List<Object> objectList;

}
