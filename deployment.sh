#!/bin/bash

# Remove existing MySQL Container
# sudo docker container rm $(sudo docker container stop $(sudo docker ps -a -q --filter ancestor=mysql))

# Starting MySQL container
# sudo docker run --name mysql-learn --network learn-mysql -e MYSQL_ROOT_PASSWORD=database -e MYSQL_DATABASE=testdb -d mysql:latest


# Building Learning Service Application
# Remove existing Learning Service Image
sudo docker rmi -f learning

echo "*********** Building Learning Service **************"
mvn clean install -DskipTests=true
sleep 2

# Create Docker Image
echo "********** Creating Learning Service Docker Image **************"
sudo docker build -t learning .

sleep 2

# Show Docker Images
echo "********* Current Docker Images ******************"
sudo docker images

sleep 2

# Running Learning Service Docker Image
echo "******** Starting Learning Service Docker container ************"
sudo docker run --network=learn-mysql --name learning -p 8100:8100 -d learning
sleep 5

#####################################################

# Remove existing Payment Service
echo "************ Remove existing Payment Service ***********"
cd ..
cd payment-service/
sudo docker rmi -f payment

echo "*********** Building Payment Service **************"
mvn clean install -DskipTests=true
sleep 2

# Create Docker Image
echo "********** Creating Payment Service Docker Image **************"
sudo docker build -t payment .

sleep 2

# Show Docker Images
echo "********* Current Docker Images ******************"
sudo docker images

sleep 2

# Running Payment Service Docker Image
echo "******** Starting Payment Service Docker container ************"
sudo docker run --network=learn-mysql --name payment -p 8200:8200 -d payment
sleep 5
