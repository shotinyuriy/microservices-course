# MySQL Database on Docker

### Install Docker
https://docs.docker.com/docker-for-mac/install/


### Deploy MySQL image (does not include configuration for external connections)
https://dev.mysql.com/doc/refman/8.0/en/docker-mysql-getting-started.html
### Configure MySQL image for external connections
https://dzone.com/articles/docker-for-mac-mysql-setup

### DEPLOYMENT SHORTCUTS

#### Start docker with mysql server version 8.0
Container name will be mysql1 (--name mysql1).
This command will try to find the docker image mentioned in the end of it in local repository
if it is not there it will pull a new one from the internet 
``` bash
START DOCKER WITH PORT FORWARDING FOR 3306 (MySQL 8.0)
docker run -p 3306:3306 -d --name mysql1 -e MYSQL_ROOT_PASSWORD=Passw0rd1 mysql/mysql-server:8.0
```

#### Check the docker container status
``` bash
docker ps
```
Command result will display all docker containers running. It takes about 30 second for a container to start. 
For 30 seconds you will see STATUS = (health: starting). Then it will change to (healthy)
```
CONTAINER ID        IMAGE                COMMAND                  CREATED             
a3fb00c34877        mysql/mysql-server   "/entrypoint.sh my..."   2 minutes ago       
STATUS                   PORTS                               NAMES
Up 2 minutes (healthy)   0.0.0.0:3306->3306/tcp, 33060/tcp   mysql
```

#### Connect to the mysql db with docker
Once mysql container gets status healthy we can connect to MySQL using the password we provided on start up.
``` bash
docker exec -it mysql1 mysql -uroot -p

Enter password: Passw0rd1
```

#### Create a new user with all priveleges
It is not recommended to use the 'root' user for external applications. 
Therefore we will create a new user for Hibernate. For simplicity it will also have all priveleges.
We will also use 'mysql_native_password' to avoid connectivity issues with password hashing.
Create hibernate user for all hosts.
``` sql
CREATE USER 'hibernate'@'%' IDENTIFIED WITH mysql_native_password BY 'Passw0rd1';
GRANT ALL ON *.* TO 'hibernate'@'%' WITH GRANT OPTION;
```

After this you should be able to connect to the database using 
- Oracle SQL Developer (https://www.oracle.com/technetwork/developer-tools/sql-developer/downloads/sqldev-downloads-184-5458710.html)
- MySQL Workbench (https://dev.mysql.com/downloads/workbench/)

#### Connection troubleshooting
If you see the following error.

**Status : Failure -Test failed: Access denied for user 'hibernate'@'172.17.0.1' (using password: YES)**

Then copy the IP address from this error and create a user for this IP address.
``` sql
CREATE USER 'hibernate'@'172.17.0.1' IDENTIFIED WITH mysql_native_password BY 'Passw0rd1';
GRANT ALL ON *.* TO 'hibernate'@'172.17.0.1' WITH GRANT OPTION;

```

