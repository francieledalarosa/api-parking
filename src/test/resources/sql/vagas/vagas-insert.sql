insert into USUARIOS (id, username, password, role) values
(100, 'mariaz@gmail.com','$2a$12$4xrYMfvBObxNanUz5uXNz.vKL/0wIYCn6GidPkB5QmG7ei3fmAoPa' ,'ROLE_ADMIN'),(200, 'joaoz@gmail.com', '$2a$12$4xrYMfvBObxNanUz5uXNz.vKL/0wIYCn6GidPkB5QmG7ei3fmAoPa', 'ROLE_CLIENTE'), (300, 'ze@gmail.com', '$2a$12$4xrYMfvBObxNanUz5uXNz.vKL/0wIYCn6GidPkB5QmG7ei3fmAoPa', 'ROLE_CLIENTE');

insert into VAGAS (id, codigo, status) values (01, 'A-01', 'LIVRE'),
(02, 'A-02',  'LIVRE'),(03, 'A-03', 'OCUPADA'),(04, 'A-04', 'LIVRE');