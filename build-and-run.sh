#!/bin/bash

# Passo 1: Gerar o JAR
echo "Gerando o JAR..."
mvn clean package -DskipTests || { echo "Falha ao gerar o JAR"; exit 1; }

# Passo 2: Construir a imagem Docker
echo "Construindo a imagem Docker..."
docker-compose build || { echo "Falha ao construir a imagem"; exit 1; }

# Passo 3: Subir os serviços
echo "Subindo os containers com Docker Compose..."
docker-compose up -d || { echo "Falha ao subir os containers"; exit 1; }

echo "Todos os serviços estão rodando!"
