### Jdk:
`OpenJDK Runtime Environment Zulu17.54+21-CA (build 17.0.13+11-LTS)`
<br>
`Jdk: 17.0.13 Lts`


# Docker commands

### Baixar imagem do Mysql
`docker pull mysql:8.3.0`

<br>

#### Criar e iniciar um container
`docker run -d -p 3306:3306 --name mysql-container -e MYSQL_ROOT_PASSWORD=root -e MYSQL_PASSWORD-root mysql:8.3.0`

### [Opcional] Caso o container já exista e esteja parado, você pode consultá-lo com o seguinte comando: `docker ps -a`. 
### [Opcional] Para iniciar um container que está parado, utilize o comando:`docker start CONTAINER ID`
### [Opcional] Se preferir parar e remover um container para executar outro, use os comandos a seguir: `docker stop mysql-container` e `docker rm mysql-container`

### Para ver apenas os container que estão em execução
`docker ps`

### Acessar o container
`docker exec -it mysql-container bash`

### Após, podemos chamar o mysql seguido de -u root -p e passar a senha que é root 
`mysql -u root -p`

### Agora podemos dar um create database forum para criar o banco de dados.
`create database forum;`

### Feito isso, criamos o database. Pra conferirmos se está tudo certo, passamos o comando use forum.
`use forum`


### Gere o .jar com o comando(ele ficara no path: /target/forum-0.0.1-SNAPSHOT.jar)
`mvn clean package`

### Constroi a imagem/ faça o build dela, gera o container
`docker build -t forum -f Dockerfile .`

### Roda o container aplicação
`docker run -p 3080:8780 forum`

### E podemos consultar
`select * from topico;`

# MVN commands

### Baixar dependencia pelo maven que preenchem o pom
`mvn package spring-boot:repackage`

### Cria o jar da aplicação, sempre que mudar o código precisa gerar um novo jar.
`mvn package`

### Pegar as informações novas após ter modificado o código
`mvn clean package`

### Subir a aplicação usando o comando mvn, sem definir um perfil específico.
`mvn spring-boot:run`

### Rodar em Dev
`mvn spring-boot:run -Dspring-boot.run.profiles=dev`

### Rodar em Prod
`mvn spring-boot:run -Dspring-boot.run.profiles=prod`


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


### Parar algum container
`docker stop ${nome-do-contêiner}`

### Inicia-lo
`docker start ${nome-do-contêiner}`



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
