# Flood Monitor

## Descricao do Projeto

O Flood Monitor e uma API REST desenvolvida para a disciplina de Arquitetura Orientada a Servicos (SOA), na Global Solution da FIAP.

A solucao tem como objetivo apoiar o monitoramento de regioes urbanas com risco de enchente ou alagamento. A API permite cadastrar regioes monitoradas, consultar regioes, executar analises de risco com base em dados climaticos externos e consultar alertas gerados pelo sistema.

## Tecnologias Utilizadas

* Java 17;
* Spring Boot 3.3.5;
* Spring Web;
* Spring Data JPA;
* Spring Security;
* JWT com JJWT;
* BCrypt;
* H2 Database;
* Flyway;
* Swagger/OpenAPI com Springdoc;
* Maven;
* Bean Validation;
* Lombok;
* RestClient;
* API externa Open-Meteo.

## Arquitetura do Projeto

O projeto segue uma arquitetura em camadas, organizada por dominios da aplicacao.

Principais camadas e componentes:

* **Controller**: recebe as requisicoes HTTP e retorna as respostas da API.
* **Service**: concentra as regras de negocio e coordena os fluxos da aplicacao.
* **Repository**: realiza o acesso ao banco de dados com Spring Data JPA.
* **Model/Entity**: representa as entidades persistidas no banco de dados.
* **DTO**: define os dados de entrada e saida da API.
* **Security/JWT**: controla autenticacao, geracao de token e protecao dos endpoints.
* **Integracao externa**: consulta dados climaticos da API Open-Meteo.
* **Banco de dados**: utiliza H2 em memoria, com criacao das tabelas via Flyway.

Fluxo geral da aplicacao:

```text
Cliente -> Controller -> Service -> Repository -> Banco de Dados -> Service -> Controller -> Cliente
```

Fluxo da analise de risco:

```text
Cliente -> RiskAnalysisController -> RiskAnalysisService -> WeatherService -> OpenMeteoClient -> Open-Meteo
RiskAnalysisService -> Repository -> Banco de Dados -> Controller -> Cliente
```

## Instrucoes de Execucao

### 1. Clonar o repositorio

```bash
git clone https://github.com/Pedro-Josue/Flood-Monitor
cd Flood-monitor
```

### 2. Banco de dados

O projeto usa H2 em memoria. Nao e necessario instalar ou configurar um banco externo.

As tabelas e dados iniciais sao carregados automaticamente pelo Flyway a partir de:

```text
src/main/resources/db/migration
```

Configuracao principal do banco:

```text
JDBC URL: jdbc:h2:mem:flooddb
Usuario: sa
Senha: em branco
```

Console H2:

```text
http://localhost:8081/h2-console
```

### 3. Variaveis de ambiente

Nao ha variaveis de ambiente obrigatorias para executar o projeto localmente. As configuracoes principais estao em:

```text
src/main/resources/application.properties
```

### 4. Executar pela IDE

Execute a classe principal:

```text
src/main/java/com/fiap/floodmonitoring/FloodMonitoringApplication.java
```

### 5. Executar via Maven

Na raiz do projeto, execute:

```bash
mvn spring-boot:run
```

A API ficara disponivel em:

```text
http://localhost:8081
```

## Swagger/OpenAPI

A documentacao Swagger/OpenAPI esta disponivel em:

```text
http://localhost:8081/swagger-ui.html
```

O arquivo OpenAPI em JSON pode ser acessado em:

```text
http://localhost:8081/v3/api-docs
```

A configuracao do Swagger esta em `OpenApiConfig.java` e em `application.properties`.

## Endpoints da API

O projeto possui CRUD completo para regioes monitoradas em `/api/regions`.

| Metodo | Endpoint | Descricao |
| ------ | -------- | --------- |
| POST | `/api/auth/register` | Cadastra um usuario |
| POST | `/api/auth/login` | Autentica um usuario e retorna token JWT |
| GET | `/api/regions` | Lista todas as regioes monitoradas |
| GET | `/api/regions/{id}` | Busca uma regiao monitorada por ID |
| POST | `/api/regions` | Cadastra uma nova regiao monitorada |
| PUT | `/api/regions/{id}` | Atualiza uma regiao monitorada |
| DELETE | `/api/regions/{id}` | Remove uma regiao monitorada |
| POST | `/api/regions/{regionId}/risk-analysis` | Executa uma analise de risco para uma regiao |
| GET | `/api/regions/{regionId}/risk-analysis` | Lista o historico de analises de uma regiao |
| GET | `/api/alerts` | Lista os alertas gerados |
| GET | `/api/alerts/{id}` | Busca um alerta por ID |
| PUT | `/api/alerts/{id}/resolve` | Marca um alerta como resolvido |
| DELETE | `/api/alerts/{id}` | Remove um alerta |

## Autenticacao JWT

O projeto possui autenticacao JWT.

O login e realizado pelo endpoint:

```text
POST /api/auth/login
```

Exemplo de resposta:

```json
{
  "token": "TOKEN_GERADO_PELA_API",
  "type": "Bearer"
}
```

Para acessar endpoints protegidos, envie o token no header:

```text
Authorization: Bearer SEU_TOKEN_AQUI
```

Os endpoints de autenticacao sao publicos. Os endpoints de regioes, analises de risco e alertas exigem JWT.

## Exemplos de Requisicoes

### Cadastro de usuario

```http
POST /api/auth/register
```

```json
{
  "name": "Usuario Teste",
  "email": "usuario@fiap.com",
  "password": "123456",
  "role": "USER"
}
```

### Login

```http
POST /api/auth/login
```

```json
{
  "email": "admin@fiap.com",
  "password": "123456"
}
```

### Cadastro de regiao monitorada

```http
POST /api/regions
```

```json
{
  "name": "Marginal Pinheiros - Ponte Cidade Jardim",
  "city": "Sao Paulo",
  "state": "SP",
  "neighborhood": "Cidade Jardim",
  "latitude": -23.5862,
  "longitude": -46.6931,
  "riverName": "Rio Pinheiros",
  "riskLevel": "HIGH",
  "active": true,
  "description": "Regiao com historico de alagamento em chuvas intensas."
}
```

### Executar analise de risco

Este endpoint nao recebe corpo JSON. Ele utiliza a regiao cadastrada e consulta dados climaticos externos.

```http
POST /api/regions/1/risk-analysis
```

## Estrutura do Projeto

```text
src/
  main/
    java/
      com/fiap/floodmonitoring/
        auth/
          controller/
          dto/
          model/
          repository/
          security/
          service/
        region/
          controller/
          dto/
          model/
          repository/
          service/
        risk/
          controller/
          dto/
          model/
          repository/
          service/
        alert/
          controller/
          dto/
          model/
          repository/
          service/
        weather/
          client/
          dto/
          service/
        config/
        shared/
    resources/
      application.properties
      db/
        migration/
```

