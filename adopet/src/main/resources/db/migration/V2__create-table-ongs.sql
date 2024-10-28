CREATE TABLE ongs (
    id BIGSERIAL NOT NULL,
    nome varchar(100) NOT NULL,
    email varchar(100) NOT NULL UNIQUE,
    senha varchar(500) NOT NULL,
    telefone varchar(20) NOT NULL UNIQUE,
    pais varchar(100) NOT NULL,
    cidade varchar(100) NOT NULL,
    codigo_postal varchar(8) NOT NULL,
    complemento varchar(100),
    rua varchar(100) NOT NULL,
    ativo BOOLEAN NOT NULL,

    PRIMARY KEY (id)
);
