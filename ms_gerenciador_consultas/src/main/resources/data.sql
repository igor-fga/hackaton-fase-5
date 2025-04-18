CREATE TABLE IF NOT EXISTS medico (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    crm VARCHAR(255) NOT NULL,
    especialidade VARCHAR(255) NOT NULL
);


INSERT INTO medico (nome, crm, especialidade)
SELECT * FROM (
                  SELECT 'Dr. João Silva' AS nome, '123456' AS crm, 'Cardiologia' AS especialidade UNION ALL
                  SELECT 'Dra. Maria Oliveira', '654321', 'Pediatria' UNION ALL
                  SELECT 'Dr. Carlos Pereira', '111111', 'Ortopedia' UNION ALL
                  SELECT 'Dra. Ana Costa', '222222', 'Dermatologia' UNION ALL
                  SELECT 'Dr. Pedro Santos', '333333', 'Neurologia' UNION ALL
                  SELECT 'Dra. Julia Almeida', '444444', 'Ginecologia' UNION ALL
                  SELECT 'Dr. Marcos Lima', '555555', 'Psiquiatria' UNION ALL
                  SELECT 'Dra. Fernanda Souza', '666666', 'Oftalmologia' UNION ALL
                  SELECT 'Dr. Ricardo Rocha', '777777', 'Endocrinologia' UNION ALL
                  SELECT 'Dra. Patricia Mendes', '888888', 'Gastroenterologia' UNION ALL
                  SELECT 'Dr. Roberto Nunes', '999999', 'Urologia' UNION ALL
                  SELECT 'Dra. Claudia Martins', '101010', 'Pediatria' UNION ALL
                  SELECT 'Dr. Eduardo Silva', '1111111', 'Cardiologia' UNION ALL
                  SELECT 'Dra. Vanessa Ribeiro', '121212', 'Dermatologia' UNION ALL
                  SELECT 'Dr. Felipe Araujo', '131313', 'Ortopedia' UNION ALL
                  SELECT 'Dra. Renata Lima', '141414', 'Ginecologia' UNION ALL
                  SELECT 'Dr. Gustavo Ferreira', '151515', 'Neurologia' UNION ALL
                  SELECT 'Dra. Mariana Barbosa', '161616', 'Psiquiatria' UNION ALL
                  SELECT 'Dr. Alexandre Costa', '171717', 'Oftalmologia' UNION ALL
                  SELECT 'Dra. Beatriz Gomes', '181818', 'Endocrinologia'
              ) AS temp_table
WHERE NOT EXISTS (SELECT 1 FROM medico);

DROP TABLE IF EXISTS horario_trabalho;

CREATE TABLE horario_trabalho (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    medico_id BIGINT NOT NULL,
    dia_semana VARCHAR(255) NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fim TIME NOT NULL,
    FOREIGN KEY (medico_id) REFERENCES medico(id),
    CONSTRAINT uk_medico_id_dia_semana UNIQUE (medico_id, dia_semana)
);

INSERT INTO horario_trabalho (medico_id, dia_semana, hora_inicio, hora_fim)
SELECT id, 'SEGUNDA-FEIRA', '08:00:00', '12:00:00' FROM medico WHERE crm = '123456'; -- Dr. João Silva
INSERT INTO horario_trabalho (medico_id, dia_semana, hora_inicio, hora_fim)
SELECT id, 'SEXTA-FEIRA', '08:00:00', '12:00:00' FROM medico WHERE crm = '123456'; -- Dr. João Silva
INSERT INTO horario_trabalho (medico_id, dia_semana, hora_inicio, hora_fim)
SELECT id, 'TERÇA-FEIRA', '14:00:00', '18:00:00' FROM medico WHERE crm = '654321'; -- Dra. Maria Oliveira
INSERT INTO horario_trabalho (medico_id, dia_semana, hora_inicio, hora_fim)
SELECT id, 'QUARTA-FEIRA', '09:00:00', '17:00:00' FROM medico WHERE crm = '111111'; -- Dr. Carlos Pereira
INSERT INTO horario_trabalho (medico_id, dia_semana, hora_inicio, hora_fim)
SELECT id, 'QUINTA-FEIRA', '08:00:00', '12:00:00' FROM medico WHERE crm = '222222'; -- Dra. Ana Costa
INSERT INTO horario_trabalho (medico_id, dia_semana, hora_inicio, hora_fim)
SELECT id, 'SEXTA-FEIRA', '13:00:00', '17:00:00' FROM medico WHERE crm = '333333'; -- Dr. Pedro Santos
INSERT INTO horario_trabalho (medico_id, dia_semana, hora_inicio, hora_fim)
SELECT id, 'SEGUNDA-FEIRA', '09:00:00', '13:00:00' FROM medico WHERE crm = '444444'; -- Dra. Julia Almeida
INSERT INTO horario_trabalho (medico_id, dia_semana, hora_inicio, hora_fim)
SELECT id, 'TERÇA-FEIRA', '10:00:00', '14:00:00' FROM medico WHERE crm = '555555'; -- Dr. Marcos Lima
INSERT INTO horario_trabalho (medico_id, dia_semana, hora_inicio, hora_fim)
SELECT id, 'QUARTA-FEIRA', '08:00:00', '12:00:00' FROM medico WHERE crm = '666666'; -- Dra. Fernanda Souza
INSERT INTO horario_trabalho (medico_id, dia_semana, hora_inicio, hora_fim)
SELECT id, 'QUINTA-FEIRA', '14:00:00', '18:00:00' FROM medico WHERE crm = '777777'; -- Dr. Ricardo Rocha
INSERT INTO horario_trabalho (medico_id, dia_semana, hora_inicio, hora_fim)
SELECT id, 'SEXTA-FEIRA', '08:00:00', '12:00:00' FROM medico WHERE crm = '888888'; -- Dra. Patricia Mendes


CREATE TABLE IF NOT EXISTS consulta (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    paciente_id BIGINT NOT NULL,
    tipo_consulta VARCHAR(255) NOT NULL,
    data_hora DATETIME NOT NULL,
    especialidade VARCHAR(255) NOT NULL,
    medico_id BIGINT NOT NULL,
    motivo_consulta VARCHAR(255) NOT NULL,
    anamnese TEXT NULL,
    diagnostico TEXT NULL,
    observacoes TEXT NULL,
    FOREIGN KEY (medico_id) REFERENCES medico(id)
);