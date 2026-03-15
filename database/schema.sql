CREATE DATABASE club_deportivo;
USE club_deportivo;

CREATE TABLE TipoCuota (
    id_tipo_cuota INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    precio DECIMAL(8,2) NOT NULL,
    max_invitaciones_mes INT NOT NULL
);

CREATE TABLE Socio (
    id_socio INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellidos VARCHAR(150) NOT NULL,
    email VARCHAR(150),
    telefono VARCHAR(20),
    fecha_alta DATE NOT NULL,
    activo BOOLEAN DEFAULT TRUE,
    id_tipo_cuota INT NOT NULL,
    FOREIGN KEY (id_tipo_cuota) REFERENCES TipoCuota(id_tipo_cuota)
);

CREATE TABLE Pago (
    id_pago INT AUTO_INCREMENT PRIMARY KEY,
    id_socio INT NOT NULL,
    mes INT NOT NULL,
    anio INT NOT NULL,
    fecha_pago DATE,
    importe DECIMAL(8,2) NOT NULL,
    FOREIGN KEY (id_socio) REFERENCES Socio(id_socio)
);

CREATE TABLE Invitacion (
    id_invitacion INT AUTO_INCREMENT PRIMARY KEY,
    id_socio INT NOT NULL,
    mes INT NOT NULL,
    anio INT NOT NULL,
    cantidad_usadas INT DEFAULT 0,
    FOREIGN KEY (id_socio) REFERENCES Socio(id_socio)
);

CREATE TABLE UsuarioSistema (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    rol VARCHAR(20) NOT NULL
);

INSERT INTO TipoCuota (nombre, precio, max_invitaciones_mes)
VALUES
('Basica', 30.00, 2),
('Gold', 50.00, 5),
('Premium', 80.00, 10);

SELECT * FROM TipoCuota;

CREATE USER 'admin-frailema'@'localhost' IDENTIFIED BY 'frailema2026*';

GRANT ALL PRIVILEGES ON club_deportivo.* TO 'admin-frailema'@'localhost';

FLUSH PRIVILEGES;

INSERT INTO Socio (nombre, apellidos, email, telefono, fecha_alta, activo, id_tipo_cuota)
VALUES ('Juan', 'Pérez', 'juan@email.com', '600123123', '2024-01-01', true, 1);