# Docker with Kafka from Confluent

## Full Document
https://docs.confluent.io/current/quickstart/ce-docker-quickstart.html?utm_medium=sem&utm_source=google&utm_campaign=ch.sem_br.nonbrand_tp.prs_tgt.kafka_mt.xct_rgn.emea_lng.eng_dv.all&utm_term=apache%20kafka%20docker&creative=367725579745&device=c&placement=&gclid=EAIaIQobChMI_IWZubK-5QIVxeWaCh2yGw9_EAAYASAAEgIsdvD_BwE


## Create and Start Docker with Kafka
```bash
git clone https://github.com/confluentinc/examples
cd examples
git checkout 5.3.1-post
```

```bash
cd ~/git/confluentinc/examples/
```
```bash
cd cp-all-in-one/

cd ~/git/confluentinc/examples/cp-all-in-one/
```

```bash
docker-compose up -d --build
```

```bash
docker-compose ps
```

## Kafka Web Interface
Navigate to the Control Center web interface at http://localhost:9021/.

## Stop Docker with Kafka
View a list of all Docker container IDs.

```bash
docker container ls -aq
```

Run the following command to stop the Docker containers for Confluent:
```bash
docker container stop $(docker container ls -a -q -f "label=io.confluent.docker")
```

Run the following commands to stop the containers and prune the Docker system. Running these commands deletes containers, networks, volumes, and images; freeing up disk space:
```bash
docker container stop $(docker container ls -a -q -f "label=io.confluent.docker") && docker system prune -a -f --volumes
```



## Docker Kafka Cli
You can also run these commands using the KSQL CLI from your Docker container with this command: docker-compose exec ksql-cli ksql http://ksql-server:8088.

```bash
docker-compose exec ksql-cli ksql http://ksql-server:8088
```


## Create a Stream from a Kafka Topic
Create a Stream with Selected Columns

### Page Views Stream
```sql
CREATE STREAM PAGEVIEWS
  (viewtime BIGINT,
   userid VARCHAR,
   pageid VARCHAR)
  WITH (KAFKA_TOPIC='pageviews',
        VALUE_FORMAT='DELIMITED');
```

```sql
CREATE STREAM PAGEVIEWS
  (viewtime BIGINT,
   userid VARCHAR,
   pageid VARCHAR)
  WITH (KAFKA_TOPIC='pageviews',
        VALUE_FORMAT='AVRO');
```

### Users Table
```sql
CREATE TABLE USERS
  (registertime BIGINT,
   userid VARCHAR,
   gender VARCHAR,
   regionid VARCHAR)
  WITH (KAFKA_TOPIC = 'users',
        VALUE_FORMAT='JSON',
        KEY = 'userid');
```

```sql
CREATE TABLE USERS
  (registertime BIGINT,
   userid VARCHAR,
   gender VARCHAR,
   regionid VARCHAR)
  WITH (KAFKA_TOPIC = 'users',
        VALUE_FORMAT='AVRO',
        KEY = 'userid');
```

Create a persistent query that filters for female users. The results from this query are written to the Kafka PAGEVIEWS_FEMALE topic. This query enriches the pageviews STREAM by doing a LEFT JOIN with the users TABLE on the user ID, where a condition (gender = 'FEMALE') is met.
```sql
CREATE STREAM pageviews_female AS SELECT users.userid AS userid, pageid, regionid, gender FROM pageviews LEFT JOIN users ON pageviews.userid = users.userid WHERE gender = 'FEMALE';
```