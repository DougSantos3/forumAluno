### Jdk:
`OpenJDK Runtime Environment Zulu21.38+21-CA (build 21.0.5+11-LTS)`


### Baixar imagem do Mysql
`docker pull mysql:8.3.0`


### Criar e iniciar um container
`docker run -d -p 3306:3306 --name mysql-container -e MYSQL_ROOT_PASSWORD=root -e MYSQL_PASSWORD-root mysql:8.3.0`

`docker run -p 8081:8081 -e DB_HOST=localhost -e DB_PORT=3306 -e DB_USERNAME=root -e DB_PASSWORD=root forum-app`

### [Opcional] Caso o container já exista e esteja parado, você pode consultá-lo com o seguinte comando: `docker ps -a`. 
### [Opcional] Para iniciar um container que está parado, utilize o comando: `docker start CONTAINER ID`
### [Opcional] Se preferir parar e remover um container para executar outro, use os comandos a seguir: `docker stop CONTAINER ID` e `docker rm CONTAINER ID`

### Para ver apenas os container que estão em execução
`docker ps`

### Acessar o container
`docker exec -it mysql-container bash`

### Após, podemos chamar o mysql seguido de -u root -p e passar a senha que é root 
`mysql -u root -p`

### Agora podemos dar um create database forum para criar o banco de dados.
`create database forum;`

### Feito isso, criamos o database. Para conferirmos se está tudo certo, passamos o comando use forum.
`use forum`

### Gere o .jar com o comando(ele ficara no path: /target/forum-0.0.1-SNAPSHOT.jar)
## sem rodar os testes unitarios/integração container/integração controller
`mvn clean package -DskipTests`

### Rodando os testes juntos
`mvn clean package`

### Constroi a imagem/ faça o build dela, gera o container
`docker build -t forum -f Dockerfile .`

### Após atualizar ou adicionar uma nova dependência. Para garantir que o driver esteja disponível no classpath da sua aplicação
## Execução com os testes
`mvn clean install`

## Sem os testes
`mvn clean install -DskipTests`

### Crie uma Rede Docker Personalizada: Isso permite que os contêineres se comuniquem pelo nome.
`docker network create minha-rede`

### Listando as redes com docker network ls: Se você estiver usando uma rede customizada para o MySQL e o contêiner da aplicação, ambos estarão na mesma rede e você poderá acessar o banco pelo nome do contêiner (sem o IP).
`docker network ls`

### Confirme se o MySQL está em minha-rede: Se o contêiner do MySQL já está rodando, adicione-o à rede minha-rede com o comando:
`docker network connect minha-rede <mysql_container_id>`

### Ou, se ele ainda não estiver em execução, inicie o MySQL diretamente na rede minha-rede:
`docker run --name mysql-container --network minha-rede2 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=forum -p 3306:3306 -d mysql:8`

### Inicie a aplicação Spring Boot na mesma rede: Use mysql-container como valor de DB_HOST. Essa configuração permite que a aplicação se conecte ao MySQL, pois ambos estão na rede minha-rede.
`docker run -p 8081:8081 --network minha-rede2 \
  -e DB_HOST=mysql-container \
  -e DB_PORT=3306 \
  -e DB_USERNAME=root \
  -e DB_PASSWORD=root \
  forum`

### Agora que subiu a migration você tem as tabelas no banco

### Caso queira subir a aplicação pelo maven direto
`mvn spring-boot:run -Dspring-boot.run.profiles=dev`


## Outros comandos Maven

### Baixar dependência pelo maven que preenchem o pom
`mvn package spring-boot:repackage`

### Cria o jar da aplicação, sempre que mudar o código precisa gerar um novo jar.
`mvn package`

### Pegar as informações novas após ter modificado o código
`mvn clean package`

### Se você quizer consultar se a porta já está sendo usada
`sudo lsof -i :porta`

### Subir a aplicação usando o comando mvn, sem definir um perfil específico.
`mvn spring-boot:run`

### Rodar em Dev
`mvn spring-boot:run -Dspring-boot.run.profiles=dev`

### Rodar em Prod
`mvn spring-boot:run -Dspring-boot.run.profiles=prod`

### Docker compose
`docker-compose down` ## Para parar e remover os containers existentes. 
`docker-compose down -v` ## para remover também os volumes.
`docker-compose up -d`


### Heroku commands

#### Para fazer o deploy da aplicação, precisamos realizar uma série de passos, como criar um app, construir a imagem do contêiner, subir para o contêiner registry e criar a release da imagem.

### conectar com o Heroku
`heroku login`

### Criar um aplicativo 
`heroku create`

### Apontar para o aplicativo criado
`heroku git:remote -a $NomeDoAPlicativoCriadoAcima`

### Logar no registry do Heroku
`heroku container:login`

### Criamos a imagem no registry para fazer o deploy no nosso aplicativo, faz a release da imagem do app.
`heroku container:push web`




# Redis

### Baixar a imagme
`docker pull redis:bullseye`

#### Criar o container
`docker run -d -p 6379:6379 --name=redis redis:7.0.2-bullseye`

#### Acessar o Cantinar Redis
`docker exec -it redis bash`

### Executar o Redis
`redis-cli  monitor`


# Kubernetes

### Mostrar pods
`kubectl get pods`


### Aplicar todos os arquivos contidos da pasta MySQL. Sendo que o -f do comando significa failed.
`kubectl apply -f .\mysql\`

### Aplicar todos os arquivos redis
`kubectl apply -f .\redis\`


### Aplicar todos os arquivos app
`kubectl apply -f .\app\`

### Consulta do log
`kubectl logs  car-service-app-66fdb66944c-89bnc`

### Aplicar ingress
`kubectl apply -f .\ingress\`

### Mostra execucao do ingress
`kubectl get ingress`

### Mostra tudo que está rodando, pods, services, deployments e replica set
`kubectl get all`
