DROP TABLE IF EXISTS medico;

CREATE TABLE medico (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    crm VARCHAR(255) NOT NULL,
    especialidade VARCHAR(255) NOT NULL
);

INSERT INTO medico (nome, crm, especialidade) VALUES ('Dr. João Silva', '123456', 'Cardiologia');
INSERT INTO medico (nome, crm, especialidade) VALUES ('Dra. Maria Oliveira', '654321', 'Pediatria');
INSERT INTO medico (nome, crm, especialidade) VALUES ('Dr. Carlos Pereira', '111111', 'Ortopedia');
INSERT INTO medico (nome, crm, especialidade) VALUES ('Dra. Ana Costa', '222222', 'Dermatologia');
INSERT INTO medico (nome, crm, especialidade) VALUES ('Dr. Pedro Santos', '333333', 'Neurologia');
INSERT INTO medico (nome, crm, especialidade) VALUES ('Dra. Julia Almeida', '444444', 'Ginecologia');
INSERT INTO medico (nome, crm, especialidade) VALUES ('Dr. Marcos Lima', '555555', 'Psiquiatria');
INSERT INTO medico (nome, crm, especialidade) VALUES ('Dra. Fernanda Souza', '666666', 'Oftalmologia');
INSERT INTO medico (nome, crm, especialidade) VALUES ('Dr. Ricardo Rocha', '777777', 'Endocrinologia');
INSERT INTO medico (nome, crm, especialidade) VALUES ('Dra. Patricia Mendes', '888888', 'Gastroenterologia');
INSERT INTO medico (nome, crm, especialidade) VALUES ('Dr. Roberto Nunes', '999999', 'Urologia');
INSERT INTO medico (nome, crm, especialidade) VALUES ('Dra. Claudia Martins', '101010', 'Pediatria');
INSERT INTO medico (nome, crm, especialidade) VALUES ('Dr. Eduardo Silva', '1111111', 'Cardiologia');
INSERT INTO medico (nome, crm, especialidade) VALUES ('Dra. Vanessa Ribeiro', '121212', 'Dermatologia');
INSERT INTO medico (nome, crm, especialidade) VALUES ('Dr. Felipe Araujo', '131313', 'Ortopedia');
INSERT INTO medico (nome, crm, especialidade) VALUES ('Dra. Renata Lima', '141414', 'Ginecologia');
INSERT INTO medico (nome, crm, especialidade) VALUES ('Dr. Gustavo Ferreira', '151515', 'Neurologia');
INSERT INTO medico (nome, crm, especialidade) VALUES ('Dra. Mariana Barbosa', '161616', 'Psiquiatria');
INSERT INTO medico (nome, crm, especialidade) VALUES ('Dr. Alexandre Costa', '171717', 'Oftalmologia');
INSERT INTO medico (nome, crm, especialidade) VALUES ('Dra. Beatriz Gomes', '181818', 'Endocrinologia');
