CREATE DATABASE IF NOT EXISTS banco_db
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE USER IF NOT EXISTS 'banco_user'@'%' IDENTIFIED BY 'banco_pass';
GRANT ALL PRIVILEGES ON banco_db.* TO 'banco_user'@'%';
FLUSH PRIVILEGES;

USE banco_db;

CREATE TABLE IF NOT EXISTS clientes (
    id              BIGINT          NOT NULL AUTO_INCREMENT,
    nombre          VARCHAR(100)    NOT NULL,
    genero          VARCHAR(20)     NOT NULL,
    edad            INT             NOT NULL,
    identificacion  VARCHAR(20)     NOT NULL,
    direccion       VARCHAR(200)    NOT NULL,
    telefono        VARCHAR(20)     NOT NULL,

    cliente_id      VARCHAR(50)     NOT NULL,
    contrasena      VARCHAR(255)    NOT NULL,
    estado          TINYINT(1)      NOT NULL DEFAULT 1,

    CONSTRAINT pk_clientes         PRIMARY KEY (id),
    CONSTRAINT uk_cliente_id       UNIQUE KEY  (cliente_id),
    CONSTRAINT uk_identificacion   UNIQUE KEY  (identificacion),
    CONSTRAINT chk_cliente_edad    CHECK (edad BETWEEN 1 AND 120)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS cuentas (
    id                BIGINT          NOT NULL AUTO_INCREMENT,
    numero_cuenta     VARCHAR(30)     NOT NULL,
    tipo_cuenta       VARCHAR(30)     NOT NULL,
    saldo_inicial     DECIMAL(15,2)   NOT NULL DEFAULT 0.00,
    saldo_disponible  DECIMAL(15,2)   NOT NULL DEFAULT 0.00,
    estado            TINYINT(1)      NOT NULL DEFAULT 1,
    cliente_id        BIGINT          NOT NULL,

    CONSTRAINT pk_cuentas          PRIMARY KEY (id),
    CONSTRAINT uk_numero_cuenta    UNIQUE KEY  (numero_cuenta),
    CONSTRAINT fk_cuenta_cliente   FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE,
    CONSTRAINT chk_saldo_inicial   CHECK (saldo_inicial >= 0)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS movimientos (
    id              BIGINT          NOT NULL AUTO_INCREMENT,
    fecha           DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    tipo_movimiento VARCHAR(20)     NOT NULL,
    valor           DECIMAL(15,2)   NOT NULL,
    saldo           DECIMAL(15,2)   NOT NULL,
    cuenta_id       BIGINT          NOT NULL,

    CONSTRAINT pk_movimientos         PRIMARY KEY (id),
    CONSTRAINT uk_movimiento          UNIQUE KEY  (cuenta_id, fecha, valor),
    CONSTRAINT fk_movimiento_cuenta   FOREIGN KEY (cuenta_id) REFERENCES cuentas(id) ON DELETE CASCADE,
    CONSTRAINT chk_tipo_movimiento    CHECK (tipo_movimiento IN ('DEBITO','CREDITO'))
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Datos de prueba
INSERT INTO clientes (nombre, genero, edad, identificacion, direccion, telefono, cliente_id, contrasena, estado)
VALUES
    ('Jose Lema',   'Masculino',   35, '838484311', 'Otavalo sn y principal',   '098254785', '100',   '1234', 1),
    ('Marianela Montalvo',   'Femenino',  42, '838484312', 'Amazonas y NNUU','097548965', '101', '5678', 2),
    ('Juan Osorio',  'Masculino',   29, '838484313', '13 junio y Equinoccial', '098874587', '102',  '1245', 1);

INSERT INTO cuentas (numero_cuenta, tipo_cuenta, saldo_inicial, saldo_disponible, estado, cliente_id)
VALUES
    ('478758', 'Ahorro', 2000.00,  2000.00, 1, 1),
    ('225487', 'Corriente', 100.00, 100.00, 1, 2),
    ('495878', 'Corriente', 0.00, 200.00, 1, 3),
    ('496825', 'Ahorros',  540.00, 1000.00, 1, 2);

INSERT INTO movimientos (fecha, tipo_movimiento, valor, saldo, cuenta_id)
VALUES
    ('2026-05-01 09:00:00', 'DEBITO',  -575.00, 2000.00, 1),
    ('2026-05-01 10:30:00', 'CREDITO',  600.00, 100.00, 2),
    ('2026-05-02 08:45:00', 'CREDITO',  150.00, 0.00, 2),
    ('2026-05-03 14:00:00', 'CREDITO',  -540.00, 540.00, 3);