# DEPLOY REDIS ON DOCKER LOCALLY
https://github.com/docker-library/redis/blob/0b2910f292fa6ac32318cb2acc84355b11aa8a7a/5.0/Dockerfile

### DEPLOYMENT SHORTCUTS

#### Initialize and start docker with redis server version 5.0
Container name will be redis-cart (--name redis-cart).
This command will try to find the docker image mentioned in the end of it in local repository
if it is not there it will pull a new one from the internet.
START DOCKER WITH PORT FORWARDING FOR 6379 (-p 6379:6379) (Redis 5.0)
``` bash
docker run -p 6379:6379 -d --name redis-cart redis:5.0
```

This command can be run just once per a container name (redis-cart in this case).

Then to connect to redis client (redis-cli) you can use a command listed below. The command has the following parameters:
```bash
docker run -it --network some-network --rm redis:5.0 redis-cli -h hostname
```
--network some-network , replace some-network with the network name
to see the list of networks available use command
```bash
docker network ls
```
-h hostname , usually is localhost


```bash
docker run -it --network host --rm redis:5.0 redis-cli -h localhost
```

NOTE: this will create another redis container with a random name
ALSO: if you start MONITOR command in this docker redis-cli session, it may happen that you can not stop it anyhow except closing the terminal tab or window.
  Ctrl+C, Ctrl+Z, Ctrl+Q, ESC keys don't seem to be working.

Then we can use start and stop commands for this container name
```bash
docker stop redis-cart
docker start redis-cart
```

