mkdir initcloud
cd ./initcloud

git clone https://github.com/init-cloud/frontend-initcloud.git
git clone https://github.com/init-cloud/backend-scanner.git
git clone https://github.com/init-cloud/backend-parser.git

mv ./scanner-proto/docker-compse.yml ../
mv ./scanner-proto/.env ../

docker-compose up -d