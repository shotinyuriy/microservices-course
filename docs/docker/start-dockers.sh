docker start mysql-taxes && docker start mysql-catalog && docker start redis-cart

docker start mysql-taxes && docker start mysql-catalog && docker start redis-cart && docker start activemq

docker stop mysql-taxes && docker stop mysql-catalog && docker stop redis-cart

docker stop mysql-taxes && docker stop mysql-catalog && docker stop redis-cart && docker stop activemq

cd git/grid-dynamics/microservices-course/discovery-server
cd git/grid-dynamics/microservices-course/taxes-calculation
cd git/grid-dynamics/microservices-course/product-catalog
cd git/grid-dynamics/microservices-course/shopping-cart

gradle build bootRun