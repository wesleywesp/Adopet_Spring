CREATE TABLE pets (
    id BIGSERIAL NOT NULL,
    nome varchar(100) not null,
    nascimento TIMESTAMP NOT NULL,
    descricao varchar(500) not null,
    tipo VARCHAR(255) NOT NULL,
    foto varchar(255),
    adotado BOOLEAN NOT NULL DEFAULT false,
    ong_id BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (ong_id) REFERENCES ongs(id) ON DELETE CASCADE
);
