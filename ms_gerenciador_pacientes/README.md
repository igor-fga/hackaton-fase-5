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

```

## 📊 2. Acessar o Spring Boot Actuator

Endpoints para monitoramento e gestão da aplicação:

### URL Base:
http://localhost:8082/actuator/health
http://localhost:8082/actuator/info
http://localhost:8082/actuator/metrics

## ✉️ 3. Para testar envio de e-mail usando o Gmail, seguir os passos abaixo:

#### Gmail: 
```bash
Acesse sua conta do Google.
Selecione Segurança no item “Como fazer login no Google” ou selecione Senhas de app ou acesse diretamente o link https://myaccount.google.com/apppasswords
Crie uma senha específica para um app, digitando o nome para ele. 
Ao salvar será gerado o token para incluir em sua variável de ambiente, application.properties ou .env. 
```

## 📌 4. Endpoint para gerar Excel ao Admin via Navegador Local (extensão .xlsx)
```bash
http://localhost:8082/api/admin/exportar-pacientes
```
