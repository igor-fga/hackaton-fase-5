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
│   │       │       ├── dto/              # Objetos de resposta para erros (ErroResponse)
│   │       │       └── exception/        # Manipuladores de exceções (ApiExceptionHandler)
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

## Configuração do Ambiente

1. Pré-requisitos:
   - Java 17
   - MySQL
   - Maven
    
2. Configuração do Banco de Dados:
    - Certifique-se de que o MySQL esteja em execução.
    - Crie um banco de dados chamado prontuario.

```plaintext
spring.datasource.url=jdbc:mysql://localhost:3306/prontuario?serverTimezone=America/Sao_Paulo&useSSL=false
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA
```
3. Executando o Projeto:
   - Compile o projeto

```plaintext
mvn clean install
```

4. Acessando a API:
   - A API estará disponível em: [localhost](http://localhost:8080/api/consultas)

## Endpoints Disponíveis

### Consultas

  - POST /api/consultas (Agendar uma nova consulta)

  - GET /api/consultas/{id} (Buscar consulta por ID)

  - GET /api/consultas/paciente/{pacienteId} (Buscar consultas de um paciente)

  - GET /api/consultas/periodo?inicio={inicio}&fim={fim} (Buscar consultas em um período)

  - PUT /api/consultas/{id} (Atualizar uma consulta existente)

  - DELETE /api/consultas/{id} (Cancelar uma consulta)

  - GET /api/consultas/medicos (Consultar médicos disponíveis por especialidade)

### Disponibilidade

  - POST /api/consultas/disponibilidade (Verificar horários disponíveis para um médico)

  - GET /api/consultas/{medicoId}/dias-disponiveis?inicio={inicio}&fim={fim} (Verificar dias disponíveis para um médico em um período)

## Testes

Para executar os testes, utilize o comando:

```plaintext
mvn test
```

