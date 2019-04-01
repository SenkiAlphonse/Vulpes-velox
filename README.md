# Vulpes-velox

### Set these environment variables for VeloxApplication:
>DB_TYPE=mysql
>DB_HOSTNAME=localhost
DB_PORT=3306
DB_NAME=storage
DB_USERNAME=username
DB_PASSWORD=password
DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver
HIBERNATE_DIALECT=org.hibernate.dialect.MySQL5Dialect
GOOGLE_CLIENT_ID=583881474030-15c3u6bjad65scfinp29etvp74te5gl5.apps.googleusercontent.com
GOOGLE_CLIENT_SECRET=bLIhdXZ2INK1cG9ieO58W9ku

### Technologies, traits of project
- Oauth2, users table has to contain current google email to have access.
- Responsive css for mobile.
- Validation and alerts for /storage/add
Empty fields
Products with same name
Other than 8 digit product numbers
0 or less quantities
Assigning to products that don't exist
Aren't allowed
-
-




#### Docker
./start-docker-compose.sh  
to start dockerized app with db.  
Since docker-compose is not controlling startup order, it was handled manually throughout bash script.
Deprecated:
You can run app with:
```sh
$ docker-compose up -d
```
Environment variables in _.env_ file  
