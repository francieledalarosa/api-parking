insert into USUARIOS (id, username, password, role) values
                                                        (100, 'mariaz@gmail.com','$2a$12$4xrYMfvBObxNanUz5uXNz.vKL/0wIYCn6GidPkB5QmG7ei3fmAoPa' ,'ROLE_ADMIN'),
                                                        (200, 'joaoz@gmail.com', '$2a$12$4xrYMfvBObxNanUz5uXNz.vKL/0wIYCn6GidPkB5QmG7ei3fmAoPa', 'ROLE_CLIENTE'),
                                                        (300, 'ze@gmail.com', '$2a$12$4xrYMfvBObxNanUz5uXNz.vKL/0wIYCn6GidPkB5QmG7ei3fmAoPa', 'ROLE_CLIENTE'),
                                                        (400, 'zoy@gmail.com', '$2a$12$4xrYMfvBObxNanUz5uXNz.vKL/0wIYCn6GidPkB5QmG7ei3fmAoPa', 'ROLE_CLIENTE');

insert into CLIENTES (id, nome, cpf, id_usuario) values (10, 'joao ze', '38616418016', 200), (20, 'ze zi', '70456417028', 300);