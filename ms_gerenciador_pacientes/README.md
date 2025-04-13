# 🚀 Guia para o MS_Gerenciador_Pacientes

## ✅ 1. Rodar o Projeto

### Pré-requisitos
- Java 17 ou superior
- Maven 
- Banco de dados MySql
- Arquivo `application.properties` ou `.env` preenchido corretamente

### Executar com Maven
```bash
./mvnw spring-boot:run

## 📊 2. Acessar o Spring Boot Actuator

Endpoints para monitoramento e gestão da aplicação:

### URL Base:
http://localhost:8082/actuator/health
http://localhost:8082/actuator/info
http://localhost:8082/actuator/metrics
