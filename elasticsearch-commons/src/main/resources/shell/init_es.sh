#!/bin/sh

es_dir="./elasticsearch-5.5.2"
if [ -d "$es_dir" ]; then
    rm -rf $es_dir
fi
wget https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-5.5.2.tar.gz
tar -zxvf elasticsearch-5.5.2.tar.gz
cd elasticsearch-5.5.2
data_dir="./data"
if [ ! -d "$data_dir" ]; then
    mkdir $data_dir
fi
logs_dir="./logs"
if [ ! -d "$logs_dir" ]; then
    mkdir $logs_dir
if

cd config
echo '' > elasticsearch.yml

current_ip=`/sbin/ifconfig -a|grep inet|grep -v 127.0.0.1|grep -v inet6|awk '{print $2}'|tr -d "addr:"`
echo -e 'current_ip=', $current_ip

elastic_conf="# 集群的名字
cluster.name: elasticsearch
# 节点名字
node.name: node-4
# 索引分片个数，默认为5片
index.number_of_shards: 5
# 索引副本个数，默认为1个副本
index.number_of_replicas: 1
# 数据存储目录（多个路径用逗号:分隔）
path.data: ./data
# 日志目录
path.logs: ./logs
# 修改一下ES的监听地址，这样别的机器才可以访问
network.host: "$current_ip"
# 设置节点间交互的tcp端口（集群）,默认是9300
transport.tcp.port: 9300
# 监听端口（默认的就好）
http.port: 9200

# 增加新的参数，这样head插件才可以访问es
http.cors.enabled: true
http.cors.allow-origin: "*"
bootstrap.memory_lock: false
bootstrap.system_call_filter: false
"
echo -e "${elastic_conf}" >  elasticsearch.yml

PIDS=`ps -ef | grep elasticsearch-5.5.2 |awk '{print $2}'`
if [ -z "$PIDS" ]; then
    echo "ERROR: The $SERVER_NAME does not started!"
else
    kill -9 $PIDS
fi

cd ../bin
echo 'elasticsearch-5.5.2-----starting...............'
./elasticsearch &
echo 'elasticsearch-5.5.2-----started...............'

cd ../../
cur_dir="pwd"
eval $cur_dir

################head---process#################

head_dir="elasticsearch-head"
if [ -d "$head_dir" ]; then
    rm -rf $head_dir
fi

git clone git://github.com/mobz/elasticsearch-head.git
cd elasticsearch-head
npm install

PIDS=`ps -ef | grep elasticsearch-head |awk '{print $2}'`
if [ -z "$PIDS" ]; then
    echo "ERROR: The $SERVER_NAME does not started!"
else
    kill -9 $PIDS
fi
echo 'elasticsearch-head-----starting...............'

grunt server &

echo 'elasticsearch-head-----started...............'

