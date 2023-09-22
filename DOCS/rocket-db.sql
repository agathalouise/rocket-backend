CREATE TABLE rocket.cidade (
    id BIGINT NOT NULL AUTO_INCREMENT,
    cidade VARCHAR(45) NOT NULL,
    uf VARCHAR(2) NOT NULL,
	PRIMARY KEY (id)
) CHARACTER SET utf8 COLLATE utf8_unicode_ci;

CREATE TABLE rocket.funcionario (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(60) NOT NULL,
    cpf VARCHAR(11) NOT NULL,
    email VARCHAR(60) NOT NULL UNIQUE,
    senha VARCHAR(60) NOT NULL,
    cargo VARCHAR(25) NOT NULL DEFAULT 'Funcionario',
	PRIMARY KEY (id)
) CHARACTER SET utf8 COLLATE utf8_unicode_ci;

CREATE TABLE rocket.user (
    id_user BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    senha VARCHAR(60) NOT NULL,
    cpf VARCHAR(11) NOT NULL,
    email VARCHAR(60) NOT NULL UNIQUE,
    data_nascimento DATE NOT NULL,
    nome_mae VARCHAR(255) NOT NULL,
    cep VARCHAR(9) NOT NULL,
    logradouro VARCHAR(100) NOT NULL,
    complemento VARCHAR(100),
    bairro VARCHAR(100) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    uf VARCHAR(2) NOT NULL,
    foto_perfil VARCHAR(255) NOT NULL DEFAULT 'Envio pendente',
    identificacao_frente VARCHAR(255) NOT NULL DEFAULT 'Envio pendente',
    identificacao_verso VARCHAR(255) NOT NULL DEFAULT 'Envio pendente' ,
    comprovante_endereco VARCHAR(255) NOT NULL DEFAULT 'Envio pendente',
    PRIMARY KEY (id_user)
) CHARACTER SET utf8 COLLATE utf8_unicode_ci;

CREATE TABLE rocket.situacao (
    id BIGINT NOT NULL AUTO_INCREMENT,
    id_user BIGINT NOT NULL,
    situacao VARCHAR(255) NOT NULL,
    hora_solicitacao DATE NOT NULL,
    qnt_solicitacoes INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_user) REFERENCES user (id_user)
) CHARACTER SET utf8 COLLATE utf8_unicode_ci;

INSERT INTO rocket.cidade (cidade, uf) VALUES
    ('Abadia de Goias', 'GO'),
    ('Aparecida de Goiania', 'GO'),
    ('Aragoiania', 'GO'),
    ('Bela Vista de Goias', 'GO'),
    ('Bonfinopolis', 'GO'),
    ('Brazabrantes', 'GO'),
    ('Caldazinha', 'GO'),
    ('Caturai', 'GO'),
    ('Goiania', 'GO'),
    ('Goianapolis', 'GO'),
    ('Goianira', 'GO'),
    ('Guapo', 'GO'),
    ('Hidrolandia', 'GO'),
    ('Inhumas', 'GO'),
    ('Neropolis', 'GO'),
    ('Nova Veneza', 'GO'),
    ('Santo Antonio de Goias', 'GO'),
    ('Senador Canedo', 'GO'),
    ('Terezopolis de Goias', 'GO'),
    ('Trindade', 'GO');
