# Projeto - API cálculo preço tarifado

## Descrição do Projeto

Este projeto é uma API desenvolvida com Spring Boot que calcula e salva o preço tarifado de produtos de seguros com base em sua categoria e preço base. 

A API está estruturada para processar requisições, calcular o preço tarifado e retornar as informações do produto.

## Tecnologias utilizadas

- **Java 17**
- **Maven**
- **Spring Boot**
- **Spring Data JPA**
- **MySQL**
- **H2**
- **Docker**


## Arquitetura do projeto
Este projeto implementa uma API de microserviço em Java utilizando o framework Spring Boot e segue o padrão de arquitetura em camadas (Layered Architecture). 

A escolha dessa arquitetura foi baseada na necessidade de garantir uma separação clara de responsabilidades, facilitando a manutenção, evolução e testabilidade da aplicação. 

A arquitetura em camadas promove uma organização modular do código, onde cada camada tem uma função específica e se comunica apenas com as camadas diretamente relacionadas, tornando o sistema mais robusto e flexível.

## Estrutura do Projeto

- **Config**: Contém configurações adicionais, como `TaxConfiguration`.
- **Controller**: Contém a classe `ProductController` que define o endpoint para calcular e salvar o preço tarifado de um produto.
- **DTO**: Contém classes de Data Transfer Object (DTO) para comunicação entre camadas.
- **Exception**: Contém classes para tratamento de exceções personalizadas.
- **Mapper**: Contém a classe `ProductMapper` para mapear entre entidades e DTOs.
- **Model**: Contém a classe `ProductDomain` que representa a entidade do produto no banco de dados.
- **Repository**: Contém a interface `ProductRepository` que estende `JpaRepository` para operações CRUD.
- **Service**: Contém a classe `ProductService` que implementa a lógica de cálculo e persistência do produto.
- **Utils **: Contém a classe `ConstantesAPI` que contém Utilitários e constantes gerais.

## Detalhes sobre a Solução

- Garantia de dados consistentes e válidos com validações e padronizações.
- Uso de logs para rastreamento e depuração, garante tranparência e auditoria.
- Configuração de taxas separada do código permite fácil manutenção e atualização.

Algumas decisões são projetadas para criar um método robusto, flexível e fácil de manter, assegurando que os produtos sejam calculados e salvos corretamente com base nas taxas de tributação fornecidas.

Ex: 
```java
String category = dto.getCategory().toUpperCase();
BigDecimal[] taxes = taxConfiguration.getRates().get(category);
```

O método começa convertendo a categoria fornecida para maiúsculas para garantir consistência e evitar problemas de comparação sensível a maiúsculas/minúsculas. Em seguida, ele busca as taxas correspondentes na configuração.


### Requisitos necessários para executar a aplicação local.

- **Docker**: [Instale o Docker](https://docs.docker.com/get-docker/).
- **Docker Compose**: [Instale o Docker Compose](https://docs.docker.com/compose/install/).


## Instruções para execução da aplicação

1. Clone o repositório:
    ```bash
    git clone https://github.com/viniciusmonteiroarjonas/api-calcula-preco-tarifado
    ```

2. Navegue até o diretório do projeto:
    ```bash
    cd api-calcula-preco-tarifado
    ```

3. Construa a aplicação utilizando o docker e inicie os containers:

```bash
docker-compose up --build -d
```

4. Interagir com a Aplicação

Você pode interagir com a API usando ferramentas como `curl`[Instale o CURL](https://curl.se/download.html) executando o comando abaixo via terminal:
```curl
curl --location 'http://localhost:8080/calculo/products/v1/preco/tarifado' \
--header 'Content-Type: application/json' \
--data '{
    "nome": "Seguro de Vida Individual",
    "categoria": "VIDA",
    "preco_base": 100.00
}'
```

Ou via collection do Postman, obtendo a collection no diretorio: `src/main/resources/collection`

A aplicação estará acessível em `http://localhost:8080`.
