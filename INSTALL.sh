mkdir initcloud
cd ./initcloud

git clone https://github.com/Floodnut/scanner-proto.git
git clone https://github.com/Floodnut/terraform-parser.git

mv ./scanner-proto/docker-compse.yml ../
mv ./scanner-proto/.env ../

docker-compose up -d