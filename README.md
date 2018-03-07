march action

# ElasticSearch :

# Install :
wget https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-5.5.2.tar.gz
tar -zxvf elasticsearch-5.5.2.tar.gz
cd elasticsearch-5.5.2/bin
./


# Elasticsearch Plugins：
Elasticsearch 5.0 —— Head插件部署指南, Head目前支持5.0
git clone git://github.com/mobz/elasticsearch-head.git
cd elasticsearch-head
npm install
cd /home/root/install/elasticsearch-head/node_modules/grunt/bin/
nohup grunt server &

/home/root/install/elasticsearch-head/node_modules/grunt/bin/./grunt server

修改elasticsearch-head下Gruntfile.js，添加以下
hostname: '0.0.0.0',

# Elasticsearch WebConsole：
http://localhost:9100/
http://172.16.2.134:9100/
http://172.16.2.132:9100/
http://172.16.2.133:9100/
http://172.16.2.146:9100/

http://172.16.2.134:9200/_nodes
http://172.16.2.146:9200/

http://172.16.2.146:9200/_all?pretty


# Install npm
wget https://npm.taobao.org/mirrors/node/latest/node-v9.4.0-linux-x64.tar.gz
tar -zxvf node-v9.4.0-linux-x64.tar.gz

wget https://nodejs.org/dist/v6.1.0/node-v6.1.0-linux-x64.tar.xz
xz -d node-v6.1.0-linux-x64.tar.xz
tar -xvf node-v6.1.0-linux-x64.tar
设置环境变量：
export PATH=$PATH:/home/zhaohongyong/install/node-v9.4.0-linux-x64/bin



# phantomjs install :
wget https://bitbucket.org/ariya/phantomjs/downloads/phantomjs-2.1.1-linux-x86_64.tar.bz2
tar   -jxvf    xx.tar.bz2

yum -y install wget fontconfig
mv /usr/local/phantomjs-2.1.1-linux-x86_64/ /usr/local/phantomjs

ln -s /usr/local/phantomjs/bin/phantomjs /usr/bin/
[root@localhost ~]# phantomjs
phantomjs>


# Linux Conf





# 百度地图获取经纬度：
http://api.map.baidu.com/lbsapi/getpoint/index.html


# Create Index
首先创建一个索引：
curl -XPOST "http://172.16.2.146:9200/business"
现在只创建了一个索引，并没有设置mapping，查看一下索引mapping的内容：
curl -XGET "http://172.16.2.146:9200/business/_mapping?pretty"



# ulimit -a
sudo sh -c "ulimit -n 65535 && exec su $LOGNAME"



# kibana install :
wget https://artifacts.elastic.co/downloads/kibana/kibana-5.0.0-linux-x86_64.tar.gz

http://172.16.2.146:5601

User: elastic
Pwd: changeme


# Elasticsearch Plugins
IK要和ES版本对应:
https://github.com/medcl/elasticsearch-analysis-ik.git

https://github.com/medcl/elasticsearch-analysis-pinyin.git


# Elasticsearch Index Create

创建一个索引:
curl -XPUT "http://172.16.2.146:9200/index"
测试分词效果:
curl -XPOST "http://172.16.2.146:9200/business/_analyze?analyzer=ik_max_word&text=朝阳区"
