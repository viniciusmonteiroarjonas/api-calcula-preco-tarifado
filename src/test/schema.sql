CREATE TABLE product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255),
    categoria VARCHAR(255),
    preco_base DECIMAL(19, 2),
    preco_tarifado DECIMAL(19, 2)
);
