# DEPLOY ACTIVE MQ ON DOCKER

## Hardware requirements
### CPU
No stats available to say the number of core in function of messages
### Memory
512MB is too little memory, I think  to use ActiveMQ on test environment
1GB is the standard memory size
You can set the memory that you need :
```bash
# --rm - flag is responsible 
docker run --name='activemq' -it --rm \
    -e 'ACTIVEMQ_CONFIG_MINMEMORY=512' \
    -e 'ACTIVEMQ_CONFIG_MAXMEMORY=2048' \
    -p 8161:8161 \
    -p 61616:61616 \
    -p 61613:61613 \
    -P webcenter/activemq:latest
```

```bash
docker run --name='activemq' -d --rm \
    -e 'ACTIVEMQ_CONFIG_MINMEMORY=512' \
    -e 'ACTIVEMQ_CONFIG_MAXMEMORY=2048' \
    -p 8161:8161 \
    -p 61616:61616 \
    -p 61613:61613 \
    -P webcenter/activemq:latest
```

### Persistent docker image
```bash
docker run --name='activemq' -d \
    -e 'ACTIVEMQ_CONFIG_MINMEMORY=512' \
    -e 'ACTIVEMQ_CONFIG_MAXMEMORY=2048' \
    -p 8161:8161 \
    -p 61616:61616 \
    -p 61613:61613 \
    -P webcenter/activemq:latest
```

### Storage
The necessary hard drive space depends if you use persistent message or not and the type of appender. 
Normally, no need to allocate space for ActiveMQ because the most data is contained directly in the memory. 
I think it depends on how you use ActiveMQ ;)

## Quick Start
You can launch the image using the docker command line :

### For test purpose :
```bash
docker run --name='activemq' -it --rm -P webcenter/activemq:latest
```
The account admin is "admin" and password is "admin". All settings is the default ActiveMQ's settings.

### For production purpose :
```bash
docker run --name='activemq' -d \
-e 'ACTIVEMQ_CONFIG_NAME=amqp-srv1' \
-e 'ACTIVEMQ_CONFIG_DEFAULTACCOUNT=false' \
-e 'ACTIVEMQ_ADMIN_LOGIN=admin' -e 'ACTIVEMQ_ADMIN_PASSWORD=your_password' \
-e 'ACTIVEMQ_USERS_myproducer=producerpassword' -e 'ACTIVEMQ_GROUPS_writes=myproducer' \
-e 'ACTIVEMQ_USERS_myconsumer=consumerpassword' -e 'ACTIVEMQ_GROUPS_reads=myconsumer' \
-e 'ACTIVEMQ_JMX_user1_role=readwrite' -e 'ACTIVEMQ_JMX_user1_password=jmx_password' \
-e 'ACTIVEMQ_JMX_user2_role=read' -e 'ACTIVEMQ_JMX_user2_password=jmx2_password'
-e 'ACTIVEMQ_CONFIG_TOPICS_topic1=mytopic1' -e 'ACTIVEMQ_CONFIG_TOPICS_topic2=mytopic2'  \
-e 'ACTIVEMQ_CONFIG_QUEUES_queue1=myqueue1' -e 'ACTIVEMQ_CONFIG_QUEUES_queue2=myqueue2'  \
-e 'ACTIVEMQ_CONFIG_MINMEMORY=1024' -e  'ACTIVEMQ_CONFIG_MAXMEMORY=4096' \
-e 'ACTIVEMQ_CONFIG_SCHEDULERENABLED=true' \
-v /data/activemq:/data \
-v /var/log/activemq:/var/log/activemq \
-p 8161:8161 \
-p 61616:61616 \
-p 61613:61613 \
webcenter/activemq:5.14.3
```

### Web Interface
http://localhost:8161