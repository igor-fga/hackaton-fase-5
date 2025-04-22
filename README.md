# hackaton-fase-5
Projeto para Hackaton FIAP visando a construção de uma solução de Prontuário Eletrônico para o SUS, com foco em escalabilidade e segurança.  

## 🛠️ Tecnologias utilizadas:
- **Java 17**
- **Spring Boot 3.4.4**
  - Spring Web
  - Spring Data JPA
  - Spring Validation
- **Banco de Dados MySQL**
- **Flyway**
- **Keycloak**
- **Lombok**
- **Mockito** (para testes)
- **AssertJ** (para assertions em testes)
- **Maven** (para gerenciamento de dependências)

---

## 📌 Pré-requisitos
- Java 17 ou superior
- Maven
- Banco de dados MySql
- Arquivo application.properties ou .yaml preenchido corretamente com senha do Banco de Dados

---

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
        └── com.prontuario.servicos/
            └── application/
                └── usecase/              # Testes dos casos de uso
```

---

## 🧩 Entidades de Domínio
**Pacientes**:
- id, nome, cpf, dataNascimento, contato, endereco, numeroProntuario

**Consultas**:
- id, idPaciente, dataHora, medico (nome/CRM), motivoConsulta, anamnese, diagnostico, observacoes

**Medicamentos**:
- Medicamento: id, nome, principioAtivo, fabricante, dosagem
- Prescricao: id, idConsulta, idPaciente, listaMedicamentos, posologia, dataInicio, dataTermino, observacoes

---

## 🔒 Segurança
- Autenticação via Keycloak com JWT
- RBAC baseado em perfis de usuário (médico, enfermeiro, admin)

---  

## 📋 Configurações do Projeto
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

---

## 🧪 Como Executar
 
### Clone o projeto
```bash
git clone https://github.com/igor-fga/hackaton-fase-5
```

### Navegue até o diretorio do projeto:
```plaintext
cd ms_gerenciador_{escolha o serviço}
```
### Compile o projeto
```plaintext
mvn clean install
```
### Execute com Maven
```bash
mvn spring-boot:run
```

### Execute os testes
```bash
mvn test
```
---

## 📦 URL das imagens dos serviços no Docker HUB
```bash
pacientes-ms - https://hub.docker.com/repository/docker/michaeltorto/pacientes-ms/general
consultas-ms - https://hub.docker.com/repository/docker/michaeltorto/medicamentos-ms/general
medicamentos-ms - https://hub.docker.com/repository/docker/michaeltorto/consultas-ms/general
```
### Requisitos necessários
```bash
Docker e docker-compose instalados.
```
```bash
Utilizar os arquivos abaixo: 
https://github.com/igor-fga/hackaton-fase-5/blob/main/docker-compose.yml
https://github.com/igor-fga/hackaton-fase-5/hospital-realm.json at main · igor-fga/hackaton-fase-5
https://github.com/igor-fga/hackaton-fase-5/init.sql at main · igor-fga/hackaton-fase-5

```
### Dentro do diretório hackaton-fase-5-docker terá um arquivo docker-compose.yml
```bash
A partir desse diretório, rodar um comando no terminal
docker-compose up -d
```
### Testes com o Postman
```bash
Para a realização dos testes via API, utilizar o Postman.
hackaton-fase-5/Hackaton-fase-5.postman_security_collection,json.txt at main · igor-fga/hackaton-fase-5
hackaton-fase-5/Hackaton-fase-5.postman_collection.json at main · igor-fga/hackaton-fase-5
```

--- 

## 📌 Para mais informações sobre os serviços, acessar o Readme.md de cada projeto:
- ms_gerenciador_consultas = https://github.com/igor-fga/hackaton-fase-5/blob/main/ms_gerenciador_consultas/README.md  

- ms_gerenciador_medicamentos = https://github.com/igor-fga/hackaton-fase-5/blob/main/ms_gerenciador_medicamentos/README.md

- ms_gerenciador_pacientes = https://github.com/igor-fga/hackaton-fase-5/blob/main/ms_gerenciador_pacientes/README.md

--- 

## 🎬 Vídeo MVP:
https://github.com/igor-fga/hackaton-fase-5/blob/main/VIDEO-2025-04-21-21-01-09.mp4
