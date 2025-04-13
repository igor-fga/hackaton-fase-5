CREATE TABLE pacientes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome_completo VARCHAR(255),
    data_nascimento DATE,
    genero VARCHAR(255),
    cpf VARCHAR(255),
    numero_prontuario VARCHAR(7) UNIQUE NOT NULL,
    email VARCHAR(255),
    endereco VARCHAR(255),
    contato VARCHAR(255)
);