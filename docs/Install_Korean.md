
<div align="center">  
 <img src="https://github.com/init-cloud/backend-scanner/tree/main/docs/logo.png" width="100">

# Init Cloud IaC Scanner API (Beta)
</div>

## Description
이닛 클라우드에서 제공하는 정적 스캔, 시각화 및 보고서 제공 오픈소스입니다.
* 해당 솔루션은 API만 제공됩니다. 웹 대시보드를 원하시면 [InitCloud-Scanner](https://github.com/init-cloud/initcloud-scanner)로 이동하세요.

## Which vendors we support?
* CSP : AWS, NCP가 공식적으로 지원됩니다.
* IaC : 현재 Terraform이 지원됩니다.

## Road Map
* CSP : `OpenStack`을 추후 지원할 예정입니다.
* IaC : `Ansible`을 추후 지원할 예정입니다.

## How to install & run
1. 사용 환경에 도커와 도커 컴포즈를 설치하세요.
   [install docker](https://docs.docker.com/engine/install/ubuntu/)  
   [install docker-compose](https://docs.docker.com/compose/install/linux/)
2. 필요한 솔루션을 클론하세요.
```bash
git clone https://github.com/init-cloud/backend-scanner.git
cd ./backend-scanner
```  
3. 기본적인 환경변수를 설정하세요.
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
4. 자, 준비가 모두 끝났습니다.
```bash
# pwd : ./backend-scanner
docker compose up

# or
docker compose up -d
```
