# hackaton-fase-5
Projeto para Hackaton FIAP

## 🛠️ Tecnologias utilizadas:


## 📌 Pré-requisitos
- Java 17 ou superior
- Maven
- Banco de dados MySql
- Arquivo application.properties ou .yaml preenchido corretamente com senha do Banco de Dados

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

## 🧪 Como Executar
 
### Clone o projeto
```bash
git clone https://github.com/igor-fga/hackaton-fase-5
cd ms-{nome do microserviço}
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
./mvnw spring-boot:run
```
## 📌 Para mais informações sobre os serviços, acessar o Readme.md de cada projeto:
ms_gerenciador_consultas
ms_gerenciador_medicamentos
ms_gerenciador_pacientes
