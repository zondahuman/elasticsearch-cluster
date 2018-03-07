package com.abin.lee.elasticsearch.svr.api.search;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by abin on 2018/1/24 15:28.
 * elasticsearch-svr
 * com.abin.lee.elasticsearch.svr.api.search
 */
@Setter
@Getter
public class SearchBusiness implements Serializable {

    private String name; //检索关键字
    private double latitude; //纬度
    private double longitude; //经度

    private int distance; //距离
    private String unit;//单位 m,km

    private int from;
    private int size= 30;

    private int currentPage;


}
