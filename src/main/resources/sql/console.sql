/* 여기부터 계정 생성 */
create database order_system default character set UTF8;

CREATE USER order_system@'%' IDENTIFIED BY 'order_system' ;

grant all privileges on order_system.* to 'order_system'@'%';

FLUSH PRIVILEGES;
/* 여기까지 */


/*
docker pull mongo
docker run --name mongodb -v ~/data:/data/db -d -p 27017:27017 mongo
docker exec -it mongodb /bin/bash
mongosh

use commerce;
db.createUser({user:"commerce", pwd:"commerce", roles:["readWrite"]});
db.createCollection("PRODUCT_VIEW"); // Collection생성
 */