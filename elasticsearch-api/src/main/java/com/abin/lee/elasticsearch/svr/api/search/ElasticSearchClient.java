package com.abin.lee.elasticsearch.svr.api.search;

import com.google.common.collect.Maps;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: tinkpad
 * Date: 15-12-27
 * Time: 下午6:24
 * To change this template use File | Settings | File Templates.
 */
//@Component
public class ElasticSearchClient {
    // 创建私有对象
    TransportClient client;

    {
        try {
            // 配置你的es,现在这里只配置了集群的名,默认是elasticsearch,跟服务器的相同
            Map<String, String> settingMap = Maps.newHashMap();
//        settingMap.put("node.client", "false");
//        settingMap.put("node.data", "true");
//        settingMap.put("node.local", "true");
            settingMap.put("cluster.name", "business");
            // 设置client.transport.sniff为true来使客户端去嗅探整个集群的状态，把集群中其它机器的ip地址加到客户端中，
            settingMap.put("client.transport.sniff", "true");
            settingMap.put("discovery.type", "zen");//发现集群方式
            settingMap.put("discovery.zen.minimum_master_nodes", "3");//最少有2个master存在
            settingMap.put("discovery.zen.ping_timeout", "200ms");//集群ping时间，太小可能会因为网络通信而导致不能发现集群
            settingMap.put("discovery.initial_state_timeout", "500ms");
            settingMap.put("gateway.type", "local");//(fs, none, local)
            settingMap.put("index.number_of_shards", "1");
            settingMap.put("action.auto_create_index", "false");//配置是否自动创建索引
            settingMap.put("cluster.routing.schedule", "50ms");//发现新节点时间
            Settings.Builder settings = Settings.builder().put(settingMap);

            client = new PreBuiltTransportClient(settings.build())
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("172.30.11.75"), 9300))
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("172.30.11.240"), 9300))
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("172.30.11.77"), 9300));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 取得实例
    public synchronized TransportClient getTransportClient() {
        return client;
    }
    public synchronized void close() {
        client.close();
    }

    public XContentBuilder selectAnalyzer() throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder().startObject()
                .startObject("page")
                .startObject("properties")
                .startObject("id")
                .field("type", "integer")
                .field("indexAnalyzer", "ik")
                .field("searchAnalyzer", "ik")
                .endObject()
                .startObject("name")
                .field("type", "string")
                .field("indexAnalyzer", "ik")
                .field("searchAnalyzer", "ik")
                .endObject()
                .startObject("function")
                .field("type", "string")
                .field("indexAnalyzer", "ik")
                .field("searchAnalyzer", "ik")
                .endObject()
                .endObject()
                .endObject()
                .endObject();
        return builder;
    }

    public static void main(String[] args) {
        System.out.println("client="+new ElasticSearchClient().client);
    }



}
