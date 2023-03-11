
<div align="center">  
 <img src="https://raw.githubusercontent.com/init-cloud/backend-scanner/main/docs/logo.png" width="100">

# Init Cloud IaC Scanner API (Beta)
</div>
 
## Description   
Init Cloud IaC Scanner support static code scan, visualization and report.
* This API is except Dashboard. Only API.

## Which vendors we Support?
* CSP : AWS, NCP are supported.
* IaC : Terraform is supported.

## Road Map
* CSP : `OpenStack` will be supported soon.
* IaC : `Ansible` will be supported.

## How to install & run
1. Install docker & docker-compose on your environment.  
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

# IF YOU USE FE DASHBOARD.
#BOARD_PORT=5555 # YOUR BOARD PORT
#LOCAL_BASE_URL=localhost:9090/api/v1
#APP_BASE_URL=http://initcloud_scanner:8080/api/v1

# Scanner
SCANNER_PORT=9090 # YOUR SCANNER PORT
JWT_SECRET= # YOUR RANDOM VALUE LONGER THAN 32

#IF YOU NEED TO MANAGE GITHUB REPOSITORY, WRITE BELOWS
#GITHUB_CLIENT_ID=  
#GITHUB_CLIENT_SECRET= 

#IF YOU NEED TO MANAGE GITHUB ORGANIZATION, WRITE BELOWS
#GITHUB_APP_CLIENT_ID=
#GITHUB_APP_CLIENT_SECRET=

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
