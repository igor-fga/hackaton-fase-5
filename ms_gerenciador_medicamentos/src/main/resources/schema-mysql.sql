CREATE DATABASE IF NOT EXISTS prontuario;
USE prontuario;

CREATE TABLE IF NOT EXISTS medicamentos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    principio_ativo VARCHAR(100) NOT NULL,
    fabricante VARCHAR(100) NOT NULL,
    dosagem VARCHAR(50) NOT NULL
);
