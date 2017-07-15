#! /bin/bash

## configure development env:
LOCAL_DIR=`pwd` && echo "############ PAY ATTENTION HERE: ${LOCAL_DIR} ###############################" && \

## Install all we need first:

## Java JDK 8
wget -c --header "Cookie: oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/8u131-b11/d54c1d3a095b4ff2b6607d096fa80163/jdk-8u131-linux-x64.tar.gz && \
tar -zxvf jdk-8u131-linux-x64.tar.gz && cd jdk1.8.0_131/ && cd .. && \
export PATH="${PATH}:${LOCAL_DIR}/jdk1.8.0_131/bin" && export JAVA_HOME="${LOCAL_DIR}/jdk1.8.0_131" &&  which java && echo "####### Habemos Java #######" && \

## Maven
wget -c https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.3.9/apache-maven-3.3.9-bin.tar.gz && \
tar -zxvf apache-maven-3.3.9-bin.tar.gz && cd apache-maven-3.3.9/ && cd .. && \
export PATH="${PATH}:${LOCAL_DIR}/apache-maven-3.3.9/bin" &&  which mvn && echo "####### Habemos Maven #######" && \

############################
## Let's get start now :D ##
############################

## 1) first clone my repo:
git clone https://github.com/softctrl/twitter-stats.git && cd twitter-stats/projects && export PRJ=`pwd` && \

## 2) Download fly-rest and sc-utils-java libs (mypreciouslib... :D ) I use this libs on this project:
mkdir libs && cd libs && git clone https://github.com/softctrl/sc-utils-java.git && cd sc-utils-java/sc-utils-java && mvn install && cd ../.. && \
git clone https://github.com/softctrl/fly-rest.git && cd fly-rest/fly-rest && mvn install && \
echo "####### Habemos FLY-REST and SC-UTILS-JAVA #######" && cd ../../.. && \

## 3) Build twitter-model project:
cd twitter-model/ && mvn install && cd .. && \

## 4) Build data-dump proect:
cd data-dump/ && mvn install && cd .. && \

## 5) Build sqlite-client proect:
cd sqlite-client/ && mvn install && cd .. && \

## 6) Build stats-service project:
cd stats-service && mvn install && mvn package && cd .. && \

## 7) Download SQLite database:
cd ${PRJ} && mkdir db && cd db && \
wget -c https://github.com/aliancahospitalar/backend-software-engineer-test/raw/master/twitter-movie-ratings.zip && unzip twitter-movie-ratings.zip && \

SQLITE_DB=`pwd`/twitter-movie-ratings.db && cd .. && \

### 8) Elasticsearch:
wget -c https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-5.5.0.tar.gz && tar -zxvf elasticsearch-5.5.0.tar.gz && elasticsearch-5.5.0/bin/elasticsearch -d && \
echo "##### STARGING ELASTICSEARCH #####" && \

echo "Wait 1min please........" && sleep 60 && \

# 9) Making the initial dump and send it to elastisearch:
mkdir dump && cd dump && \
java  -cp ../data-dump/target/data-dump-0.0.1-SNAPSHOT-jar-with-dependencies.jar com.aliancahospitalar.data.dump.Main $SQLITE_DB dump.elk && echo "##### BULK Insert #####" && \
curl -s -XPOST http://localhost:9200/twitter/_bulk --data-binary @dump.elk && echo "##### Data imported ok ######" && cd .. && \

# 10) Start stats-service project:
#nohup java -jar stats-service/target/stats-service-0.0.1-SNAPSHOT.jar > stats-service/log.log 
java -jar stats-service/target/stats-service-0.0.1-SNAPSHOT.jar && \

# 11) Start the update Service (under development)


echo "" && \
echo "###################################################" && \
echo "###################################################" && \
echo "###################################################" && \
echo "###################################################" && \
echo "###################################################"
