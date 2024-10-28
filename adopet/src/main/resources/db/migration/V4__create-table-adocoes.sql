create table IF NOT EXISTS adocoes(
    id bigserial not null,
    id_pet bigserial not null,
    id_tutor bigserial not null,
    data_adocao timestamp not null,

    primary key (id),
    foreign key (id_pet) references pets(id),
    foreign key (id_tutor) references usuarios(id)
);