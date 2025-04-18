# Consultas Service

Microserviço para gerenciamento de consultas médicas.

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.4.4**
  - Spring Web
  - Spring Data JPA
  - Spring Validation
- **Banco de Dados MySQL**
- **Lombok**
- **Mockito** (para testes)
- **AssertJ** (para assertions em testes)
- **Maven** (para gerenciamento de dependências)

## Estrutura do Projeto

```plaintext
src/
├── main/
│   ├── java/
│   │   └── com.prontuario.consultas/
│   │       ├── adapters/
│   │       │   └── controllers/          # Controladores REST
│   │       ├── application/
│   │       │   ├── dto/                  # Objetos de transferência de dados (DTOs)
│   │       │   ├── mapper/               # Mapeadores de entidades para DTOs
│   │       │   └── usecase/              # Casos de uso (lógica de negócios)
│   │       ├── domain/
│   │       │   ├── entity/               # Entidades do domínio
│   │       │   └── repository/           # Interfaces de repositórios
│   │       └── infra/
│   │           └── config/               # Configurações do projeto
│   └── resources/
│       ├── [application.properties]      # Configurações do Spring Boot
│       └── [data.sql]                    # Script de inicialização do banco de dados
└── test/
    └── java/
        └── com.prontuario.consultas/
            └── application/
                └── usecase/              # Testes dos casos de uso
```

Configuração do Ambiente
Pré-requisitos:

Java 17
MySQL
Maven
Configuração do Banco de Dados:

Certifique-se de que o MySQL esteja em execução.
Crie um banco de dados chamado prontuario.
