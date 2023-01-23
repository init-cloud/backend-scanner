<div align="center">    
 
# Init Cloud IaC Scanner API

</div>
 
## Description   
Init Cloud IaC Scanner is Scanner & Visualizer for AWS, NCP.
* This API is in development.
* In development, we will support only `Terraform`.
* This API is except FE Dashboard. Only API.


## How to install & run
1. Install docker & docker-compose on your linux environment.  
    [install docker](https://docs.docker.com/engine/install/ubuntu/)  
    [install docker-compose](https://docs.docker.com/compose/install/linux/)
2. Cloning Init Cloud Scanner API
```bash
git clone https://github.com/init-cloud/backend-scanner.git
cd ./backend-scanner
```  
3. Set your Environment Variables. 
```bash
touch .env
```
```bash
# .env
TZ= #YOUR TIMEZONE

# Dashboard
BOARD_PORT=5555 # YOUR BOARD PORT
REACT_APP_BASE_URL=http://initcloud_scanner:9090/api/v1

# Scanner
SCANNER_PORT=9090 # YOUR SCANNER PORT
JWT_SECRET= # YOUR RANDOM VALUE LONGER THAN 32

# Parser
PARSER_PORT=9001 # YOUR PARSER PORT

# DB
MARIADB_DATABASE=initcloud
MARIADB_USER=__YOUR_DATABASE_USER__
MARIADB_PASSWORD=__YOUR_DATABASE_PASSWORD__
MARIADB_ROOT=__YOUR_DATABASE_ROOT__
MARIADB_ROOT_PASSWORD=__YOUR_DATABASE_ROOT_PASSWORD__
DB_PORT=9002 #__YOUR_DATABASE_PORT__ 

```   
4. Run with Docker-compose
```bash
# pwd : ./backend-scanner
docker compose up

# or
docker compose up -d
```
