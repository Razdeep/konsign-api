# konsign-api

Springboot backend for konsign


### Setting up local development environment

1. Login to the default mysql user
```bash
sudo mysql
```
2. Create a new database called `konsign`
```mysql
CREATE DATABASE konsign;
```
3. Create a new user named `rajdeep` with password `rajdeep` after logging into the root mysql user
```mysql
CREATE USER 'rajdeep'@'localhost' IDENTIFIED BY 'rajdeep';
```

4. Grant all permissions to the new created user
```mysql
grant all privileges on konsign.* to 'rajdeep'@'localhost'; 
```