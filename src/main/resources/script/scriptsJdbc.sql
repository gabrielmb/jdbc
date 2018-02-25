create table tb_endereco(
	id integer auto_increment,
    logradouro varchar(50) not null,
    numero integer not null,
    bairro varchar(50) not null,
    cidade varchar(50) not null,
    pais varchar(30) not null default 'BRASIL',
    primary key (id)
);

create table tb_contato (
	id integer auto_increment,
    id_endereco integer not null,
    nome varchar(20) not null,
    email varchar(30) not null,
    data_nascimento date default null,
    primary key (id),
    foreign key (id_endereco) references tb_endereco(id)
);
