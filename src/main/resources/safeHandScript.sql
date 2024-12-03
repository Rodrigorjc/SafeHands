create table usuario(
                        id serial primary key,
                        username varchar(50) not null,
                        password varchar(500) not null,
                        email varchar(100) not null,
                        rol int not null
);

create table cliente(
                        id serial primary key,
                        dni char(9) not null,
                        foto_perfil varchar(500) not null,
                        id_usuario int not null,
                        constraint fk_cliente_usuario foreign key(id_usuario) references usuario(id)
);

create table ong(
                    id serial primary key,
                    num_voluntarios int not null,
                    descripcion varchar(500) not null,
                    sede varchar(100) not null,
                    ubicacion varchar(500) not null,
                    img varchar(500) not null,
                    id_usuario int not null,
                    constraint fk_ong_usuario foreign key(id_usuario) references usuario(id)
);


create table proveedores(
                            id serial primary key,
                            nombre varchar(100) not null,
                            num_voluntarios int not null,
                            sede varchar(100) not null,
                            ubicacion varchar(500) not null,
                            cif char(9) not null,
                            validado boolean default false,
                            img varchar(500) not null,
                            id_usuario int not null,
                            constraint fk_proveedores_usuario foreign key(id_usuario) references usuario(id)
);


create table producto(
                         id serial primary key,
                         nombre varchar(100) not null,
                         url varchar(500) not null,
                         precio float not null,
                         descripcion varchar(500) not null,
                         id_proveedores int not null,
                         constraint fk_producto_proveedores foreign key (id_proveedores) references proveedores(id)

);


create table pedido(
                       id serial primary key,
                       fecha timestamp not null,
                       id_cliente int not null,
                       constraint fk_pedido_cliente foreign key(id_cliente) references cliente(id)
);

create table linea_pedido(
                             id serial primary key,
                             cantidad int not null,
                             precio_unitario float not null,
                             id_producto int not null,
                             id_pedido int not null,
                             constraint fk_linea_pedido_producto foreign key(id_producto) references producto(id),
                             constraint fk_linea_pedido_pedido foreign key (id_pedido) references pedido(id)
);


create table acontecimiento(
                               id serial primary key,
                               nombre varchar(50) not null,
                               descripcion varchar(500) not null,
                               ubicacion varchar(500) not null,
                               constraint fk_acontecimiento_ong foreign key(id_ong) references ong(id)
);

create table pagos(
                      id serial primary key,
                      cuantia float not null,
                      estado boolean not null,
                      id_pedido int not null,
                      constraint fk_pagos_pedido foreign key(id_pedido) references pedido(id)
);



create table proveedores_acontecimiento(
                                           id serial primary key,
                                           id_proveedores int not null,
                                           id_acontecimiento int not null,
                                            id_producto int not null,
                                           constraint fk_proveedores_aconteciminetos_proveedores foreign key (id_proveedores) references proveedores(id),
                                           constraint fk_proveedores_aconteciminetos_acontecimiento foreign key (id_acontecimiento) references acontecimiento(id),
                                           constraint fk_proveedores_aconteciminetos_producto foreign key(id_producto) references producto(id)
);


create table token_acceso (
                              id serial primary key,
                              token varchar(500) not null,
                              fecha_expiracion timestamp not null,
                              id_usuario int not null,
                              constraint fk_token_acceso_usuario foreign key(id_usuario) references usuario(id)
);

alter table proveedores add column validado boolean not null default false;

CREATE TABLE ong_acontecimiento (
                                    id serial primary key,
                                    id_ong INT NOT NULL,
                                    id_acontecimiento INT NOT NULL,
                                    constraint fk_ong_acontecimiento_ong FOREIGN KEY (id_ong) REFERENCES ong(id),
                                    constraint fk_ong_acontecimiento_acontecimiento FOREIGN KEY (id_acontecimiento) REFERENCES acontecimiento(id)
);


-- alter table acontecimiento add column img varchar(500);

-- alter table producto add column id_proveedor int ;

-- alter table producto add constraint fk_producto_proveedores foreign key(id_proveedores) references proveedores(id);

-- alter table proveedores_acontecimiento add constraint fk_producto_proveedores_acontecimineto foreign key(id_producto) references producto(id);

-- alter table ong add column img varchar(500);