## Como Usar

git clone https://github.com/murilonerdx/biblioteca-logica.git


cd biblioteca-logica

docker compose up --build -d

(Acesse: http://localhost:8080/swagger-ui/index.html)

OU

docker network create app-network


docker run --name mongo --network app-network -e MONGO_INITDB_ROOT_USERNAME=root -e MONGO_INITDB_ROOT_PASSWORD=12345 -e MONGO_INITDB_DATABASE=library -p 27017:27017 -d mongo:latest
docker run --name biblioteca-logica --network app-network -p 8080:8080 -d biblioteca-logica


#Sobrescrever Mongo
docker run --name biblioteca-logica --network app-network -p 8080:8080 -e SPRING_DATA_MONGODB_URI=mongodb://root:12345@mongo:27017/library -d biblioteca-logica

docker pull murilonerdx/biblioteca-logica:latest (Ainda não subi porque to com preguiça)

(Acesse: http://localhost:8080/swagger-ui/index.html)

---

© [Murilo Pereira](https://github.com/murilonerdx) - [2024]