# hackaton-fase-5
Projeto para Hackaton FIAP

##🧪 Como Executar

# Clone o projeto
git clone https://github.com/igor-fga/hackaton-fase-5
cd ms-{nome do microserviço}


# Execute com Maven
./mvnw spring-boot:run

# Ou com Docker Compose (se configurado)
docker-compose up --build

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

