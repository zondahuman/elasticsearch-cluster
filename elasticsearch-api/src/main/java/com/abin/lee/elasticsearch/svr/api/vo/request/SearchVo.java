package com.abin.lee.elasticsearch.svr.api.vo.request;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by abin on 2018/1/25 15:56.
 * elasticsearch-svr
 * com.abin.lee.elasticsearch.svr.api.vo.request
 */
@Getter
@Setter
public class SearchVo {
    private String key;
    private String value;
}
