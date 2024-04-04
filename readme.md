# MVN commands

### Cria o jar da aplicação, sempre que mudar o código precisa gerar um novo jar.
`mvn package`

### Pegar as infomrações novas após ter modificado o código
`mvn clean package`

### Subir a aplicação usando o comando mvn, sem definir um perfil específico.
`mvn spring-boot:run`


### Rodar em Dev
`mvn spring-boot:run -Dspring-boot.run.profiles=dev`

### Rodar em Prod
`mvn spring-boot:run -Dspring-boot.run.profiles=prod`



# Docker commands

### Constroi a imagem/ faça o build dela
`docker build -t forum -f Dockerfile .`

### Roda a aplicação
`docker run -p 3080:8080 forum`



# Heroku commands

# Que para fazer o deploy da aplicação, precisamos realizar uma série de passos, como criar um app, construir a imagem
# do contêiner, subir para o contêiner registry e criar a release da imagem;

### conectar com o Heroku
`heroku login`

# Criar um aplicativo 
`heroku create`

### Apontar para o aplicativo criado
`heroku git:remote -a $NomeDoAPlicativoCriadoAcima`

### Logar no registry do Heroku
`heroku container:login`

### Ccriamos a imagem no registry para fazer o deploy no nosso aplicativo, faz a release da imagem do app.
`heroku container:push web`



### Rodar uma imagem, executa-la, entrar dentro do container e executar dentro

### pegando a imagem do Docker
`docker pull mysql:8.0.28`

### Feito isso, a ferramenta indica que fez o pulling da biblioteca da imagem do MySQL. Porém, nós já temos a 
### biblioteca na nossa máquina. No seu caso ao passar esse comando o download será feito, ao concluir estará pronto 
### para usarmos o próximo comando, que é criar o container a partir dessa imagem.
### O próximo passo então é passar o comando docker run seguido de -dde detected, -p para definir a porta que será 
### 3306:3306. Importante ressaltar que o -p pe para a porta. Quando colocamos 3306, definimos que a porta do container
### será 3306 e isso será necessário para conseguir nos conectar ao container. Além disso, o banco de dados também será
### 3306 que é padrão do MySQL.
### Dessa forma, quando colocarmos na string de conexão 3306, nos referimos ao banco de dados, que está após os dois
### pontos :, antes é o container.
### Na mesma linha passamos --name mysql-container. Em seguida, configuramos a variável de ambiente -e
### MYSQL_ROOT_PASSWORD=root seguido de -e mysql_password=root. Por fim, dizemos qual imagem queremos usar,
### mysql:8.0.28.
`docker run -d -p 3306:3306-name mysql-container -e MYSQL_ROOT_PASSWORD=root -e MYSQL_PASSWORD-root mysql:8.0.28`

### para poder usar os comandos do MySQL
`docker exec -it mysql-container bash`

### Após, podemos chamar o mysql seguido de -u root -p e passar a senha. Definimos nossa senha como root, você pode ter
### definido outra, então neste momento você passa a senha definida.
`mysql -u root -p`

### Agora podemos dar um create database forum para criar o banco de dados.
`create database forum`

### Feito isso, criamos o database. Pra conferirmos se está tudo certo, passamos o comando use forum.
`use forum`

### E podemos consultar
`select * from topico`



# Redis

### Baixr a imagme
`docker pull redis:bullseye`

#### Criar o container
`docker run -d -p 6379:6379 --name=redis redis:7.0.2-bullseye`

#### Acessar o Cantinar Redis
`docker exec -it redis bash`

### Exccutar o Redis
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
