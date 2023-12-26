/* 여기부터 계정 생성 */
create database order_system default character set UTF8;

CREATE USER order_system@'%' IDENTIFIED BY 'order_system' ;

grant all privileges on order_system.* to 'order_system'@'%';

FLUSH PRIVILEGES;
/* 여기까지 */

