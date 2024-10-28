create table usuarios(

    id BIGSERIAL NOT NULL,
    nome varchar(100) not null,
    username varchar(255) not null unique,
    senha varchar(500) not null,
    email varchar(100) not null unique,
    telefone varchar(20) not null unique,
    pais varchar(100) not null,
    cidade varchar(100) not null,
    codigo_postal varchar(8) not null,
    complemento varchar(100),
    rua varchar(100),
    ativo BOOLEAN NOT NULL,

    primary key(id)

);