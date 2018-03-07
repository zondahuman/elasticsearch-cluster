package com.abin.lee.elasticsearch.svr.task;

import com.abin.lee.elasticsearch.svr.thread.process.CreateIndexTask;
import com.abin.lee.elasticsearch.svr.thread.process.CreateObjectTask;
import com.abin.lee.elasticsearch.svr.thread.process.CreateTask;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by abin on 2018/1/25 14:50.
 * elasticsearch-svr
 * com.abin.lee.elasticsearch.svr.task
 */
public class ElasticsearchBatchCreateFutureTask {

    public static void main(String[] args) {
//        testCreateIndex();
        testCreateObject();
    }

    public static void testCreateIndex(){
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i <5 ; i++) {
            Callable<String> callable = new CreateIndexTask();
            ElasticsearchCreateFutureTask futureTask = new ElasticsearchCreateFutureTask(callable);
            executorService.submit(futureTask);
        }
    }

    public static void testCreateObject(){
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i <1000 ; i++) {
            Callable<String> callable = new CreateObjectTask();
            ElasticsearchCreateFutureTask futureTask = new ElasticsearchCreateFutureTask(callable);
            executorService.submit(futureTask);
        }
    }


}


class ElasticsearchCreateFutureTask extends FutureTask<String> {

    ElasticsearchCreateFutureTask(Callable<String> callable){
        super(callable);
    }
    @Override
    protected void done() {
        super.done();
        try {
            System.out.println("id="+get() + "，  执行完毕");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


