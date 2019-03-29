# Vulpes-velox

### Environment variables:  
- DB_TYPE=mysql
- DB_HOSTNAME
- DB_PORT=3306
- DB_NAME
- DB_USERNAME
- DB_PASSWORD
- DRIVER_CLASS_NAME=com.mysql.jdbc.Driver
- HIBERNATE_DIALECT=org.hibernate.dialect.MySQL5Dialect
- GOOGLE_CLIENT_ID
- GOOGLE_CLIENT_SECRET 
#### Docker
./start-docker-compose.sh  
to start dockerized app with db.  
Since docker-compose is not controling startup order, it was handled manualy throught bash script. 
Depricated:
You can run app with:
```sh
$ docker-compose up -d
```
Environment variables in _.env_ file  
