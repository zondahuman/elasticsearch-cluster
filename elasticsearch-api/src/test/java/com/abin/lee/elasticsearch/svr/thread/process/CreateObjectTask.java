package com.abin.lee.elasticsearch.svr.thread.process;

import com.abin.lee.elasticsearch.svr.ElasticSearchTool;

import java.util.concurrent.Callable;

/**
 * Created by abin on 2018/1/25 15:11.
 * elasticsearch-svr
 * com.abin.lee.elasticsearch.svr.thread.process
 */
public class CreateObjectTask implements Callable<String> {

    @Override
    public String call() throws Exception {
        String id =ElasticSearchTool.testCreateObject();
        return id;
    }


}
