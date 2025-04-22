CREATE DATABASE pacientes;
CREATE DATABASE medicamentos;
CREATE DATABASE consultas;

CREATE USER 'app_user'@'%' IDENTIFIED BY 'app123';
GRANT ALL PRIVILEGES ON pacientes.* TO 'app_user'@'%';
GRANT ALL PRIVILEGES ON medicamentos.* TO 'app_user'@'%';
GRANT ALL PRIVILEGES ON consultas.* TO 'app_user'@'%';
FLUSH PRIVILEGES;
