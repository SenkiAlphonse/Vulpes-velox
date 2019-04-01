# Vulpes-velox java SpringBoot storage management application
2 days project

### Set these environment variables for VeloxApplication:
> - DB_TYPE=mysql
> - DB_HOSTNAME=localhost
> - DB_PORT=3306
> - DB_NAME=storage
> - DB_USERNAME=username
> - DB_PASSWORD=password
> - DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver
> - HIBERNATE_DIALECT=org.hibernate.dialect.MySQL5Dialect
> - GOOGLE_CLIENT_ID=583881474030-15c3u6bjad65scfinp29etvp74te5gl5.apps.googleusercontent.com
> - GOOGLE_CLIENT_SECRET=bLIhdXZ2INK1cG9ieO58W9ku

Environment variables in _.env_ file

### Technologies and traits of project
- Java SpringBoot
- Oauth2, have to add google email to users table to authorize or to make admin. Excluded /order endpoints.
- Responsive css for mobile.
- Audit using hibernate Envers
- Validation and saved entity alerts for /storage/add and /order
  - Empty fields
  - Products with same name
  - Other than 8 digit product numbers
  - 0 or less quantities
  - Assigning to products that don't exist etc aren't allowed, handled on backend and frontend as well.
- Dropdown filter
- Mysql
- Flyway
- Heroku, postgresql
- AWS
- Html fragments
- ItemController tests with 8/10 pitest mutation and 19/19 line coverage
- Product abstract parent entity to substitute multiple queries
- Pageable users table
- Custom exceptions
- @Valid annotation implemented for ApiController @RequestBody dto



#### Docker
./start-docker-compose.sh  
to start dockerized app with db.  
Since docker-compose is not controlling startup order, it was handled manually throughout bash script.
Deprecated:
You can run app with:
```sh
$ docker-compose up -d
```
