# ms-calcula-preco-tarifado

Este projeto é uma aplicação Java 17 com Spring Boot que pode ser executada em um container Docker. 
A aplicação é configurada para rodar na porta 8080.

## Pré-requisitos

Antes de começar, certifique-se de ter as seguintes ferramentas instaladas em sua máquina:

- [Docker](https://www.docker.com/)
- [Git](https://git-scm.com/)
- Java

## Como Executar

### Clone o repositório

Clone o repositório para sua máquina local usando o seguinte comando:

```bash
git clone https://github.com/viniciusmonteiroarjonas/api-calcula-preco-tarifado
cd api-calcula-preco-tarifado
```

Siga os passos abaixo para construir e executar a aplicação em um container Docker.

### Construir a imagem Docker e executar a aplicação

```bash
docker build -t ms-calcula-preco-tarifado:latest .
docker run --name ms-calcula-preco-tarifado -p 8080:8080 ms-calcula-preco-tarifado:latest
```
