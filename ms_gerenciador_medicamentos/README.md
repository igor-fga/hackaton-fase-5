# Prontuário Medicamentos

Microserviço responsável pelo gerenciamento de medicamentos e prescrições médicas.

## 🛠 Tecnologias Utilizadas

- Java 17
- Spring Boot 3.4.4
- Spring Web
- Spring Data JPA
- Spring Validation
- Lombok
- MySQL
- Mockito (para testes unitários)
- Maven

## 📁 Estrutura do Projeto
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


## ⚙️ Configuração do Ambiente

### Pré-requisitos

- Java 17
- MySQL
- Maven

### Configuração do banco de dados

No arquivo `application-dev.properties`, configure:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/prontuario?useSSL=false&serverTimezone=America/Sao_Paulo
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.profiles.active=dev

```
▶️ Como Executar o Projeto
 
Clone o repositorio:
```plaintext
git clone https://github.com/igor-fga/hackaton-fase-5.git
```
Navegue até o diretorio do projeto:
```plaintext
cd ms_gerenciador_medicamentos
```

Compile o projeto com Maven:
```plaintext
mvn clean install -U
```

Inicie a aplicação localmente:
```plaintext
mvn spring-boot:run
```


## Endpoints Disponíveis
### Medicamentos
POST /api/medicamentos — Cadastrar um novo medicamento

GET /api/medicamentos/{id} — Buscar medicamento por ID

GET /api/medicamentos — Listar todos os medicamentos

GET /api/medicamentos/busca?nome={nome} — Buscar medicamentos por nome

PUT /api/medicamentos/{id} — Atualizar um medicamento

DELETE /api/medicamentos/{id} — Deletar um medicamento

### Prescrições
POST /api/prescricoes — Cadastrar nova prescrição

GET /api/prescricoes — Listar todas as prescrições

GET /api/prescricoes/paciente/{id} — Listar prescrições de um paciente

GET /api/prescricoes/paciente/{id}/ativas — Listar prescrições ativas de um paciente

🧪 Como executar testes
Para testes de unidade:
```plaintext
mvn test
```


